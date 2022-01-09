package io.cmtr.crm.price.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Duration Price
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue(DurationPrice.DISCRIMINATOR_VALUE)
public class DurationPrice extends UnitPrice {



    public static final String DISCRIMINATOR_VALUE = "DURATION_PRICE";



    /**
     *
     */
    @JsonIgnore
    private ChronoUnit chronoUnit;



    ///**** CONSTRUCTOR ****///



    /**
     *
     */
    protected DurationPrice() {
        super(DISCRIMINATOR_VALUE);
    }

    ///**** GETTER ****///

    @Override
    public String getUnit() {
        return chronoUnit.toString();
    }


    ///**** SETTERS ****///



    /**
     *
     * @param unit
     * @return
     */
    @Override
    public DurationPrice setUnit(String unit) {
        // TODO - Validate
        this.chronoUnit = ChronoUnit.valueOf(unit);
        super.setUnit(unit);
        return this;
    }

    /**
     *
     * @param source
     * @return
     */
    @Override
    public DurationPrice update(AbstractPrice source) {
        super.update(source);
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public DurationPrice createNewInstance() {
        return new DurationPrice()
                .update(this);
    }



    ///**** FACTORIES ****///


    ///**** HELPER METHODS ****///


}
