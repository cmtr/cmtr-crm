package io.cmtr.crm.billing.model.billcycle;

import io.cmtr.crm.customer.model.Supplier;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


/**
 *
 * Incremental Bill Cycle
 *
 * Spans a minimum period from creation or last bill run
 *
 * Fixed Points in Year Increments, e.g. Q1, Q2, Q3, Q4, H1, H2, etc.
 *
 * @author Harald Blikø
 *
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@DiscriminatorValue(IncrementalBillCycle.DISCRIMINATOR_VALUE)
public class IncrementalBillCycle extends ContinuousBillCycle {

    public static final String DISCRIMINATOR_VALUE = "INCREMENTAL";


    /**
     *
     *
     * @param monthShift - month shift from start of year
     */
    @PositiveOrZero
    @Max(11)
    private int monthShift;



    ///**** CONSTRUCTOR ****///



    /**
     *
     */
    protected IncrementalBillCycle() {
        super(DISCRIMINATOR_VALUE);
    }



    ///**** SETTERS ****///



    /**
     *
     * @param source
     * @return
     */
    @Override
    public IncrementalBillCycle update(BillCycle source) {
        super.update(source);
        if (source instanceof IncrementalBillCycle) {
            IncrementalBillCycle src = (IncrementalBillCycle) source;
            this.setMonthShift(src.getMonthShift());
        }
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public IncrementalBillCycle createNewInstance() {
        IncrementalBillCycle incrementalBillCycle = new IncrementalBillCycle();
        incrementalBillCycle
                .setSupplier(this.getSupplier());
        return incrementalBillCycle
                .update(this);
    }



    ///**** FACTORIES ****///



    /**
     *
     * @param supplier
     * @param name
     * @param monthStep
     * @param dayShift
     * @param monthShift
     * @return
     */
    public static IncrementalBillCycle factory(
        Supplier supplier,
        String name,
        int monthStep,
        int dayShift,
        int monthShift
    ) {
        IncrementalBillCycle incrementalBillCycle = new IncrementalBillCycle()
                .setMonthShift(monthShift);
        incrementalBillCycle
                .setDayShift(dayShift)
                .setMonthStep(monthStep)
                .setName(name)
                .setSupplier(supplier);
        return incrementalBillCycle;
    }



    /**
     *
     * @param supplier
     * @param name
     * @return
     */
    public static IncrementalBillCycle factory(Supplier supplier,String name) {
        return factory(supplier, name, 0, 1, 0);
    }



}
