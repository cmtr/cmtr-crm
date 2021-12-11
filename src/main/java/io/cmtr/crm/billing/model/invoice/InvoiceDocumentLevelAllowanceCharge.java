package io.cmtr.crm.billing.model.invoice;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;
import io.cmtr.crm.shared.billing.model.IAmount;
import io.cmtr.crm.shared.billing.model.IDocumentLevelAllowanceCharge;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.math.BigDecimal;


/**
 * Document Level Allowance Charge
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@DiscriminatorValue(InvoiceDocumentLevelAllowanceCharge.DISCRIMINATOR_VALUE)
public class InvoiceDocumentLevelAllowanceCharge extends AllowanceCharge implements IDocumentLevelAllowanceCharge {



    /**
     *
     */
    public static final String DISCRIMINATOR_VALUE = "DOCUMENT";



    /**
     *
     */
    private VatCategory vatCategory;



    /**
     *
     */
    private BigDecimal net;



    /**
     *
     */
    public InvoiceDocumentLevelAllowanceCharge() {
        super(DISCRIMINATOR_VALUE);
    }



    /**
     *
     * @return
     */
    public BigDecimal getNetAmount() {
        return net.setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE);
    }



    /**
     *
     * @return
     */
    @Override
    @Transient
    public BigDecimal getAmount() {
        return getNetAmount();
    }



    /**
     *
     * @return
     */
    @Override
    @Transient
    public String getCurrency() {
        return vatCategory.getCurrency();
    }

    ///**** SETTERS ****///



    /**
     *
     * @param invoice
     * @return
     */
    @Override
    public AllowanceCharge complete(Invoice invoice) {
        // TODO - Validate VAT CATEGORY NOT NULL
        return super.complete(invoice);
    }



    /**
     *
     * @param source
     * @return
     */
    @Override
    public InvoiceDocumentLevelAllowanceCharge update(AllowanceCharge source) {
        super.update(source);
        // if (source instanceof InvoiceDocumentLevelAllowanceCharge src)
        // Unable to get language support for Java 17  (14) in Intellij
        if (source instanceof InvoiceDocumentLevelAllowanceCharge) {
            InvoiceDocumentLevelAllowanceCharge documentLevelAllowanceCharge = (InvoiceDocumentLevelAllowanceCharge) source;
            this.setVatCategory(documentLevelAllowanceCharge.getVatCategory());
            this.setNet(documentLevelAllowanceCharge.getNet());
        }
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public InvoiceDocumentLevelAllowanceCharge createNewInstance() {
        InvoiceDocumentLevelAllowanceCharge ac = new InvoiceDocumentLevelAllowanceCharge();
        ac.setState(State.NEW);
        return ac.update(this);
    }


    ///*** STATIC RESOURCES ***///


    public static InvoiceDocumentLevelAllowanceCharge factory(
            boolean isCharge,
            Supplier supplier,
            BillingAccount billingAccount,
            VatCategory vatCategory,
            BigDecimal net
    ) {
        InvoiceDocumentLevelAllowanceCharge documentLevelAllowanceCharge = new InvoiceDocumentLevelAllowanceCharge()
                .setVatCategory(vatCategory)
                .setNet(net);
        documentLevelAllowanceCharge
                .setCharge(isCharge)
                .setSupplier(supplier)
                .setBillingAccount(billingAccount);
        return documentLevelAllowanceCharge;
    }


}
