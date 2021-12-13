package io.cmtr.crm.billing.model.billcycle;

import io.cmtr.crm.customer.model.Supplier;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 *
 * Continuous Bill Cycle
 *
 * Spans a minimum period from creation or last bill run
 *
 * @author Harald Blikø
 *
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@DiscriminatorValue(ContinuousBillCycle.DISCRIMINATOR_VALUE)
public class ContinuousBillCycle extends BillCycle {

    public static final String DISCRIMINATOR_VALUE = "CONTINUOUS";


    /**
     *
     * Max 27 to account for the 28 days of February
     *
     * @param dayShift - days shift from start of month
     */
    @PositiveOrZero(message = "Days shift must be between 0 and 27 inclusive")
    @Max(value = 27, message = "Days shift must be between 0 and 27 inclusive")
    private int dayShift;

    /**
     *
     * No max value - to support
     *
     * @param monthStep - months duration of interval
     */
    @Positive(message = "MonthStep must be 1 or greater")
    private int monthStep;



    ///**** CONSTRUCTOR ****///



    /**
     *
     */
    protected ContinuousBillCycle() {
        super(DISCRIMINATOR_VALUE);
    }



    /**
     *
     * @param type
     */
    protected ContinuousBillCycle(@NotEmpty String type) {
        super(type);
    }



    ///**** SETTERS ****///



    /**
     *
     * @param source
     * @return
     */
    @Override
    public ContinuousBillCycle update(BillCycle source) {
        super.update(source);
        if (source instanceof ContinuousBillCycle) {
            ContinuousBillCycle src = (ContinuousBillCycle) source;
            this.setDayShift(src.getDayShift());
            this.setMonthStep(src.getMonthStep());
        }
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public ContinuousBillCycle createNewInstance() {
        ContinuousBillCycle continuousBillCycle = new ContinuousBillCycle();
        continuousBillCycle
                .setSupplier(this.getSupplier());
        return continuousBillCycle
                .update(this);
    }



    ///**** FACTORIES ****///



    /**
     *
     * @return
     */
    public static ContinuousBillCycle factory(
            Supplier supplier,
            String name,
            int monthStep,
            int dayShift
    ) {
        ContinuousBillCycle continuousBillCycle = new ContinuousBillCycle()
                .setDayShift(dayShift)
                .setMonthStep(monthStep);
        continuousBillCycle
                .setName(name)
                .setSupplier(supplier);
        return continuousBillCycle;
    }



    /**
     *
     * @param supplier
     * @param name
     * @return
     */
    public static ContinuousBillCycle factory(Supplier supplier,String name) {
        return factory(supplier, name, 0, 1);
    }

}
