package io.cmtr.crm.billing.model.invoice;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;
import io.cmtr.crm.shared.billing.model.IDocumentLevelAllowanceCharge;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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
@DiscriminatorValue(DocumentLevelAllowanceCharge.DISCRIMINATOR_VALUE)
public class DocumentLevelAllowanceCharge extends AllowanceCharge implements IDocumentLevelAllowanceCharge {



    /**
     *
     */
    public static final String DISCRIMINATOR_VALUE = "DOCUMENT";



    /**
     *
     */
    private VatCategory vatCategory;


    private BigDecimal netAmount;

    /**
     *
     */
    public DocumentLevelAllowanceCharge() {
        super(DISCRIMINATOR_VALUE);
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
    public DocumentLevelAllowanceCharge update(AllowanceCharge source) {
        super.update(source);
        // if (source instanceof DocumentLevelAllowanceCharge src)
        // Unable to get language support for Java 17  (14) in Intellij
        if (source instanceof DocumentLevelAllowanceCharge) {
            DocumentLevelAllowanceCharge documentLevelAllowanceCharge = (DocumentLevelAllowanceCharge) source;
            this.setVatCategory(documentLevelAllowanceCharge.getVatCategory());
            this.setCurrency(getVatCategory().getCurrency());
            this.setNetAmount(documentLevelAllowanceCharge.getNetAmount());
        }
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public DocumentLevelAllowanceCharge createNewInstance() {
        DocumentLevelAllowanceCharge ac = new DocumentLevelAllowanceCharge();
        ac.setState(State.NEW);
        return ac.update(this);
    }


    ///*** STATIC RESOURCES ***///


    public static DocumentLevelAllowanceCharge factory(
            boolean isCharge,
            Supplier supplier,
            BillingAccount billingAccount,
            VatCategory vatCategory,
            BigDecimal netAmount
    ) {
        DocumentLevelAllowanceCharge documentLevelAllowanceCharge = new DocumentLevelAllowanceCharge()
                .setVatCategory(vatCategory)
                .setNetAmount(netAmount);
        documentLevelAllowanceCharge
                .setCharge(isCharge)
                .setSupplier(supplier)
                .setBillingAccount(billingAccount)
                .setCurrency(vatCategory.getCurrency());
        return documentLevelAllowanceCharge;
    }


}
