package io.cmtr.crm.billing.model.billcycle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 *
 *
 *
 * @author Harald Blikø
 *
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@DiscriminatorValue(ContinuousBillCycle.DISCRIMINATOR_VALUE)
public class ContinuousBillCycle extends BillCycle {

    public static final String DISCRIMINATOR_VALUE = "CONTINUOUS_MONTHLY";


    /**
     *
     * @param dayShift - days shift from start of month
     */
    @PositiveOrZero
    private int dayShift;

    /**
     *
     * @param monthStep - months duration of interval
     */
    @Positive
    private int monthStep;

    protected ContinuousBillCycle() {
        super(DISCRIMINATOR_VALUE);
    }

    @Override
    public BillCycle update(BillCycle source) {
        return null;
    }

    @Override
    public BillCycle createNewInstance() {
        return super.createNewInstance();
    }
}
