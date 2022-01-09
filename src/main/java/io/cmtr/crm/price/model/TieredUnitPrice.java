package io.cmtr.crm.price.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Tiered Unit Price
 *
 *
 * @author Harald Blikø
 *
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue(TieredUnitPrice.DISCRIMINATOR_VALUE)
public class TieredUnitPrice extends UnitPrice {

    public static final String DISCRIMINATOR_VALUE = "TIERED_UNIT_PRICE";

    // TODO - Abstract out the duration unit, step and shift to own class

    /**
     * Hour, Week, Month, Year
     *
     * Use string for flexibility
     */
    private String durationUnit;


    /**
     * Amounts of units per step
     */
    private Long durationStep;


    /**
     * start of period
     */
    private Long durationShift;



    ///**** CONSTRUCTOR ****///



    protected TieredUnitPrice() {
        super(DISCRIMINATOR_VALUE);
    }

    ///**** GETTERS ****///




    ///**** SETTERS ****///



    /**
     *
     * @param validFrom
     * @param unitPrice
     * @return
     */
    @Override
    public TieredUnitPrice saveUnitPriceInstance(ZonedDateTime validFrom, BigDecimal unitPrice) {
        return saveUnitPriceInstance(validFrom, unitPrice, BigDecimal.ZERO);
    }



    /**
     *
     * @param validFrom
     * @param unitPrice
     * @return
     */
    @Override
    public TieredUnitPrice removeUnitPriceInstance(ZonedDateTime validFrom, BigDecimal unitPrice) {
        return removeUnitPriceInstance(validFrom, unitPrice, BigDecimal.ZERO);
    }



    /**
     *
     * @param validFrom
     * @param unitPrice
     * @param tier
     * @return
     */
    public TieredUnitPrice saveUnitPriceInstance(ZonedDateTime validFrom, BigDecimal unitPrice, BigDecimal tier) {
        if (!isUniquePrinceInstanceDateAndTier(validFrom, tier))
            throw new IllegalArgumentException("Unit Price Instance validFrom date and tier must be unique.");
        // TODO - add code to modify
        val instance = TieredUnitPriceInstance.factory(this, validFrom, unitPrice, tier);
        this.getUnitPriceInstances().add(instance);
        return this;
    }



    /**
     *
     * @param validFrom
     * @param unitPrice
     * @param tier
     * @return
     */
    public TieredUnitPrice removeUnitPriceInstance(ZonedDateTime validFrom, BigDecimal unitPrice, BigDecimal tier) {
        return this;
    }
    /**
     *
     * @param source
     * @return
     */
    @Override
    public TieredUnitPrice update(AbstractPrice source) {
        super.update(source);
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public TieredUnitPrice createNewInstance() {
        return null;
    }

    ///**** HELPER METHODS ****////



    /**
     *
     * @param validFrom
     * @return
     */
    private boolean isUniquePrinceInstanceDateAndTier(ZonedDateTime validFrom, BigDecimal tier) {
        return !this.getUnitPriceInstances()
                .stream()
                .anyMatch(e -> {
                    if (e instanceof TieredUnitPriceInstance) {
                        TieredUnitPriceInstance instance = (TieredUnitPriceInstance) e;
                        return instance.getValidFrom().equals(validFrom) && instance.getTier().equals(tier);
                    } else {
                        throw new RuntimeException("TieredUnitPrice with relation to non-TieredUnitPriceInstance. Relation clean-up required.");
                    }
                });
    }

    ///**** FACTORIES ****///



}
