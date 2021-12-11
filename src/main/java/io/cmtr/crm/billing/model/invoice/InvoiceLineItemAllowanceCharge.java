package io.cmtr.crm.billing.model.invoice;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;
import io.cmtr.crm.shared.billing.model.IAmount;
import io.cmtr.crm.shared.billing.model.IInvoiceLineItemAllowanceCharge;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@DiscriminatorValue(InvoiceLineItemAllowanceCharge.DISCRIMINATOR_VALUE)
public class InvoiceLineItemAllowanceCharge extends AllowanceCharge implements IInvoiceLineItemAllowanceCharge {

    public static final String DISCRIMINATOR_VALUE = "LINE_ITEM_AC";



    /**
     *
     */
    private BigDecimal net;



    /**
     *
     */
    protected InvoiceLineItemAllowanceCharge() {
        super(DISCRIMINATOR_VALUE);
    }


    ///**** GETTERS ****////

    /**
     *
     * @return
     */
    @Transient
    public BigDecimal getNetAmount() {
        return net.setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE);
    }



    /**
     *
     * @return
     */
    @Override
    public BigDecimal getAmount() {
        return getNetAmount();
    }

///**** SETTERS ****///



    /**
     *
     * @param source
     * @return
     */
    @Override
    public AllowanceCharge update(AllowanceCharge source) {
        return super.update(source);
    }



    /**
     *
     * @return
     */
    @Override
    public AllowanceCharge createNewInstance() {
        InvoiceLineItemAllowanceCharge lineItemAllowanceCharge = new InvoiceLineItemAllowanceCharge();
        lineItemAllowanceCharge.setState(State.NEW);
        return lineItemAllowanceCharge;
    }


    public InvoiceLineItemAllowanceCharge factory(
            boolean charge, Supplier supplier, BillingAccount billingAccount, BigDecimal net
    ) {
        InvoiceLineItemAllowanceCharge lineItemAllowanceCharge = new InvoiceLineItemAllowanceCharge();
        lineItemAllowanceCharge
                .setCharge(charge)
                .setSupplier(supplier)
                .setBillingAccount(billingAccount);
        return lineItemAllowanceCharge;
    }

}
