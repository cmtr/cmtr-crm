package io.cmtr.crm.price.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 *  Unit Price Instance Value Object
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@DiscriminatorValue(UnitPriceInstance.DISCRIMINATOR_VALUE)
class UnitPriceInstance extends PriceInstance {

    public static final String DISCRIMINATOR_VALUE = "UNIT_PRICE";


    /**
     *
     */
    @NotNull
    private BigDecimal unitPrice;



    ///**** CONSTRUCTOR ****//



    /**
     *
     */
    protected UnitPriceInstance() {
        super(DISCRIMINATOR_VALUE);
    }



    /**
     *
     * @param type
     */
    protected UnitPriceInstance(String type) {
        super(type);
    }



    ///**** SETTERS ****///



    /**
     *
     * @param source
     * @return
     */
    @Override
    public UnitPriceInstance update(PriceInstance source) {
        super.update(source);
        if (source instanceof UnitPriceInstance) {
            UnitPriceInstance src = (UnitPriceInstance) source;
            this.setUnitPrice(src.getUnitPrice());
        }
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public UnitPriceInstance createNewInstance() {
        return new UnitPriceInstance()
                .update(this);
    }



    ///**** FACTORIES ****///



    /**
     *
     * @param price
     * @param validFrom
     * @param unitPrice
     * @return
     */
    public static UnitPriceInstance factory(
            AbstractPrice price,
            ZonedDateTime validFrom,
            BigDecimal unitPrice
    ) {
        return  (UnitPriceInstance) new UnitPriceInstance()
                .setUnitPrice(unitPrice)
                .setValidFrom(validFrom)
                .setPrice(price);
    }


}
