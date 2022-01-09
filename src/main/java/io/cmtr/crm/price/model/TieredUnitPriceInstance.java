package io.cmtr.crm.price.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;


/**
 * Tiered Unit Price Instance
 *
 * @author Harald Bikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@DiscriminatorValue(TieredUnitPriceInstance.DISCRIMINATOR_VALUE)
public class TieredUnitPriceInstance extends  UnitPriceInstance {

    public static final String DISCRIMINATOR_VALUE = "TIERED_UNIT_PRICE";

    /**
     *
     */
    @NotNull
    private BigDecimal tier;



    ///**** CONSTRUCTORS ****///



    protected TieredUnitPriceInstance() {
        super(DISCRIMINATOR_VALUE);
    }



    ///**** GETTERS ****///



    ///**** SETTERS ****///



    @Override
    public TieredUnitPriceInstance update(PriceInstance source) {
        super.update(source);
        if (source instanceof TieredUnitPriceInstance) {
            TieredUnitPriceInstance src = (TieredUnitPriceInstance) source;
            this.setTier(src.getTier());
        }
        return this;
    }

    @Override
    public TieredUnitPriceInstance createNewInstance() {
        return new TieredUnitPriceInstance()
                .update(this);
    }


    ///**** FACTORIES ****///



    /**
     *
     * @param price
     * @param validFrom
     * @param unitPrice
     * @param tier
     * @return
     */
    public static TieredUnitPriceInstance factory(
            AbstractPrice price,
            ZonedDateTime validFrom,
            BigDecimal unitPrice,
            BigDecimal tier
    ) {
        return (TieredUnitPriceInstance) new TieredUnitPriceInstance()
            .setTier(tier)
            .setUnitPrice(unitPrice)
            .setValidFrom(validFrom)
            .setPrice(price);
    }
}
