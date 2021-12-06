package io.cmtr.crm.billing.model.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.cmtr.crm.shared.billing.model.IInvoiceLineItem;
import io.cmtr.crm.shared.billing.model.IInvoiceLineItemAllowanceCharge;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
public class InvoiceLineItem implements GenericEntity<Long, InvoiceLineItem>, IInvoiceLineItem {



    /**
     *
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;



    /**
     *
     */
    private State state;



    /**
     *
     */
    private boolean charge;



    /**
     *
     */
    @ManyToOne(
            optional = false,
            targetEntity = Invoice.class,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private Invoice invoice;



    /**
     *
     */
    @Delegate
    @Getter(AccessLevel.PROTECTED)
    @JsonIgnore
    private LineItemPrice price;



    /**
     *
     */
    @ManyToOne(
            optional = false,
            targetEntity = VatCategory.class,
            fetch = FetchType.EAGER
    )
    private VatCategory vatCategory;



    /**
     *
     */
    @JsonIgnore
    @Getter(AccessLevel.PROTECTED)
    private List<LineItemAllowanceCharge> allowanceCharges;



    ///**** GETTERS ****∕∕∕



    /**
     *
     * @return
     */
    public List<IInvoiceLineItemAllowanceCharge> getAllowances() {
        return allowanceCharges
                .stream()
                .filter(Predicate.not(IInvoiceLineItemAllowanceCharge::isCharge))
                .collect(Collectors.toList());
    }



    /**
     *
     * @return
     */
    public List<IInvoiceLineItemAllowanceCharge> getCharges() {
        return allowanceCharges
                .stream()
                .filter(IInvoiceLineItemAllowanceCharge::isCharge)
                .collect(Collectors.toList());
    }



    /**
     *
     * @return
     */
    @JsonInclude
    public Long getInvoiceId() {
        return invoice.getId();
    }



    /**
     *
     * @return
     */
    public String getCurrency() {
        // TODO - Write implementation
        return "";
    }



    /**
     *
     * @return
     */
    @Override
    public BigDecimal getQuantity() {
        return null;
    }


    /**
     *
     * @return
     */
    @Override
    public String getUnit() {
        return null;
    }



    ///**** SETTERS ****///



    /**
     *
     * @return
     */
    public InvoiceLineItem complete() {
        return this;
    }



    /**
     *
     * @return
     */
    public InvoiceLineItem cancell() {
        // TODO
        return this;
    }



    /**
     *
     * @param source
     * @return
     */
    @Override
    public InvoiceLineItem update(InvoiceLineItem source) {
        return null;
    }



    /**
     *
     * @return
     */
    @Override
    public InvoiceLineItem createNewInstance() {
        return new InvoiceLineItem()
                .setState(State.NEW)
                .update(this);
    }



    ///**** FACTORIES ****///



    public static InvoiceLineItem factory() {
        return new InvoiceLineItem();
    }



    ///**** STATIC RESOURCES ****///



    public enum State {
        NEW,
        IN_PROGESS,
        COMPLETE,
        CANCELLED
    }



    ///**** HELPER METHODS ****∕∕∕



    private void setStateToInProgressIfNew() {
        if (this.state == State.NEW)
            this.state = State.IN_PROGESS;
    }



    private void inProgressOrThrow(String message) {
        if (this.state != State.IN_PROGESS)
            throw new IllegalStateException(message);
    }

}
