package io.cmtr.crm.shared.price.model;

import io.cmtr.crm.shared.mediation.model.IQuantity;
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
@DiscriminatorValue(UnitPrice.DISCRIMINATOR_VALUE)
public class UnitPrice extends Price {

    public final static String DISCRIMINATOR_VALUE = "UNIT_PRICE";


    private BigDecimal unitPrice;


    protected UnitPrice(String type) {
        super(type);
    }

    protected UnitPrice() {
        this(DISCRIMINATOR_VALUE);
    }


    public IMonetaryAmountOld getAmount(IQuantity quantity) {
        if (!this.getUnit().equals(quantity.getUnit()))
            throw new IllegalArgumentException("LineItemPrice and quantity unit must match");
        BigDecimal amount = unitPrice.multiply(BigDecimal.valueOf(quantity.getQuantity()));
        return new MonetaryAmountOld(amount, getUnit());
    }

    @Override
    public Price update(Price source) {
        return null;
    }

    @Override
    public Price createNewInstance() {
        return null;
    }
}
