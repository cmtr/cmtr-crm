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
@Accessors(chain = true)
@Entity
@DiscriminatorValue(InvoiceLineItem.DISCRIMINATOR_VALUE)
public class InvoiceLineItem extends AllowanceCharge implements IInvoiceLineItem {

    /**
     *
     */
    public static final String DISCRIMINATOR_VALUE = "LINE_ITEM";

    /**
     *
     */
    @Delegate
    @Getter(AccessLevel.PROTECTED)
    @JsonIgnore
    private LineItemPrice price;


    public InvoiceLineItem() {
        super(DISCRIMINATOR_VALUE);
    }

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
     * @param invoice
     * @return
     */
    @Override
    protected InvoiceLineItem complete(Invoice invoice) {
        super.complete(invoice);
        this.allowanceCharges.forEach(ac -> ac.complete(invoice));
        return this;
    }



    /**
     *
     * @param source
     * @return
     */
    @Override
    public InvoiceLineItem update(AllowanceCharge source) {
        return null;
    }



    /**
     *
     * @return
     */
    @Override
    public InvoiceLineItem createNewInstance() {
        return new InvoiceLineItem()
                .update(this);
    }



    ///**** FACTORIES ****///



    public static InvoiceLineItem factory() {
        return new InvoiceLineItem();
    }



    ///**** HELPER METHODS ****∕∕∕



}
