package io.cmtr.crm.billing.model.invoice;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Recurring Invoice Line Items
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@DiscriminatorValue(RecurringInvoiceLineItem.DISCRIMINATOR_VALUE)
@Entity
public class RecurringInvoiceLineItem extends InvoiceLineItem {

    public static final String DISCRIMINATOR_VALUE = "RECURRING_LINE_ITEM";



    /**
     *
     */
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private RecurringInvoiceLineItem next;



    /**
     *
     */
    private ZonedDateTime start;



    /**
     *
     */
    private ZonedDateTime finish;




    ///**** CONSTRUCTOR ****∕∕∕



    public RecurringInvoiceLineItem() {
        super(DISCRIMINATOR_VALUE);
    }



    /**
     *
     * @param type
     */
    protected RecurringInvoiceLineItem(String type) {
        super(type);
    }



    ///**** GETTERS ****///




    ///**** SETTERS ****///



    /**
     *
     * @param invoice
     * @return
     */
    @Override
    protected RecurringInvoiceLineItem complete(Invoice invoice) {
        this.next = extend();
        return (RecurringInvoiceLineItem) super.complete(invoice);
    }



    /**
     *
     * @param source
     * @return
     */
    @Override
    public RecurringInvoiceLineItem update(AllowanceCharge source) {
        return (RecurringInvoiceLineItem) super.update(source);
    }



    /**
     *
     * @return
     */
    @Override
    public RecurringInvoiceLineItem createNewInstance() {
        return new RecurringInvoiceLineItem()
                .update(this);
    }



    ////**** FACTORIES ****////

    /**
     *
     * @param supplier
     * @param billingAccount
     * @param start
     * @return
     */
    public static RecurringInvoiceLineItem factory(Supplier supplier, BillingAccount billingAccount, ZonedDateTime start) {
        return (RecurringInvoiceLineItem) new RecurringInvoiceLineItem()
                .setStart(start)
                .setSupplier(supplier)
                .setBillingAccount(billingAccount);

    }


    ///**** HELPER METHODS ****///



    /**
     * Creates a new Recurring Charge with start date equals finish date of this.
     *
     * @return
     */
    private RecurringInvoiceLineItem extend() {
        return (RecurringInvoiceLineItem) new RecurringInvoiceLineItem()
                .setStart(this.finish)
                .setSupplier(this.getSupplier())
                .setBillingAccount(this.getBillingAccount())
                .setState(this.getState())
                .setCharge(this.isCharge());
    }
}
