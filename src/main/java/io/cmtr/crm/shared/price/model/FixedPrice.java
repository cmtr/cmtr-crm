package io.cmtr.crm.shared.price.model;

import io.cmtr.crm.shared.mediation.model.IQuantity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@DiscriminatorValue(FixedPrice.DISCRIMINATOR_VALUE)
public class FixedPrice extends UnitPrice {

    public final static String DISCRIMINATOR_VALUE = "FIXED_PRICE";

    protected FixedPrice() {
        super(DISCRIMINATOR_VALUE);
    }

    @Override
    public IMonetaryAmountOld getAmount(IQuantity quantity) {
        return new MonetaryAmountOld(getUnitPrice(), getUnit());
    }

    @Override
    public Price update(Price source) {
        return super.update(source);
    }

    @Override
    public Price createNewInstance() {
        return super.createNewInstance();
    }
}
