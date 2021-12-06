package io.cmtr.crm.billing.model.invoice;

import io.cmtr.crm.shared.billing.model.IInvoiceLineItemAllowanceCharge;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@DiscriminatorValue(LineItemAllowanceCharge.DISCRIMINATOR_VALUE)
public class LineItemAllowanceCharge extends AllowanceCharge implements IInvoiceLineItemAllowanceCharge {

    public static final String DISCRIMINATOR_VALUE = "LINE_ITEM_AC";

    protected LineItemAllowanceCharge() {
        super(DISCRIMINATOR_VALUE);
    }

    @Override
    public BigDecimal getAmount() {
        // TODO
        return null;
    }

    @Override
    public AllowanceCharge update(AllowanceCharge source) {
        return super.update(source);
    }

    @Override
    public AllowanceCharge createNewInstance() {
        LineItemAllowanceCharge lineItemAllowanceCharge = new LineItemAllowanceCharge();
        super.createNewInstance();
        return lineItemAllowanceCharge;
    }
}
