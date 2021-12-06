package io.cmtr.crm.billing.model.invoice;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;
import io.cmtr.crm.shared.billing.model.IAllowanceCharge;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * Allowance Charge
 *
 * Abstract Implementation
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "allowance_charge_type")
public abstract class AllowanceCharge implements IAllowanceCharge, GenericEntity<Long, AllowanceCharge> {



    /**
     * Allowance Charge Identifier as Long int64
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;



    /**
     * Allowance Charge Life Cycle State
     */
    private State state;



    /**
     *
     */
    private boolean charge;



    /**
     * Allowance Charge Type
     *
     * Currently supported are Document and Line Item Level
     *
     * Discriminator value for which join table should be used
     */
    @Column(
            name = "allowance_charge_type",
            updatable = false,
            nullable = false,
            insertable = false
    )
    private String type;



    /**
     * Allowance Charge Debt Holder / Issuer
     *
     * Party claiming the allowance or charge
     */
    @NotNull
    private Supplier supplier;



    /**
     * Allowance Charge Debtor / Recipient
     *
     * Party the allowance or charge is directed towards
     *
     */
    @NotNull
    private BillingAccount billingAccount;



    /**
     *
     * Allowance Charge Invoice reference
     *
     * Set once the allowance charge is allocated to one specific invoice
     *
     */
    @Setter(AccessLevel.PRIVATE)
    private Invoice invoice;



    /**
     *
     * Charge currency
     *
     */
    private String currency;



    /**
     * Constructor
     *
     * @param type - String - Discriminator value
     */
    protected AllowanceCharge(String type) {
        this.type = type;
    }



    ///**** SETTERS ****///



    /**
     *
     * @param invoice
     * @return
     */
    protected AllowanceCharge complete(Invoice invoice) {
        validateCompletion(invoice);
        this.setInvoice(invoice);
        this.state = State.COMPLETE;
        return this;
    }


    /**
     *
     * @return
     */
    public AllowanceCharge delete() {
        validateDeletion();
        this.state = State.DELETED;
        return this;
    }



    /**
     *
     * @param source
     * @return
     */
    @Override
    public AllowanceCharge update(AllowanceCharge source) {
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public AllowanceCharge createNewInstance() {
        return this.setState(State.NEW);
    }



    ///**** STATIC RESOURCES ****///



    public enum State {
        NEW,
        PREPARING,
        COMPLETE,
        DELETED
    }



    ///**** HELPER METHODS ****///



    /**
     *
     * @param message
     */
    private void preparingOrThrow(String message) {
        if (this.state != State.PREPARING)
            throw new IllegalStateException(message);
    }



    /**
     *
     */
    private void validateDeletion() {
        if (this.state == State.COMPLETE)
            throw new IllegalStateException("COMPLETED charge cannot be deleted");
    }



    /**
     *
     * @param invoice
     */
    private void validateCompletion(Invoice invoice) {
        if (this.state != State.PREPARING)
            throw new IllegalStateException("Charge must be PREPARING to complete");
        // TODO - Validate Billing Account and Supplier
    }
}
