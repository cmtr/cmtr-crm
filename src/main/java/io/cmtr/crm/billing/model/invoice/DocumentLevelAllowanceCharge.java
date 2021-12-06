package io.cmtr.crm.billing.model.invoice;

import io.cmtr.crm.shared.billing.model.IAllowanceCharge;
import io.cmtr.crm.shared.billing.model.IDocumentLevelAllowanceCharge;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import java.math.BigDecimal;


/**
 * Document Level Allowance Charge
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
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
        // TODO
        return null;
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
    public DocumentLevelAllowanceCharge update(IAllowanceCharge source) {
        super.update(source);
        if (source instanceof DocumentLevelAllowanceCharge)
            this.setVatCategory(((DocumentLevelAllowanceCharge) source).getVatCategory());
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


}
