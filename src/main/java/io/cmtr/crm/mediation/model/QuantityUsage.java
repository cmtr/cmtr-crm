package io.cmtr.crm.mediation.model;

import io.cmtr.crm.price.model.UnitPrice;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter(AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue(QuantityUsage.DISCRIMINATOR_VALUE)
public class QuantityUsage extends Usage {

    public static final String DISCRIMINATOR_VALUE = "QUANTITY";


    /**
     *
     */
    private BigDecimal quantity;


    ///**** CONSTRUCTOR ****///


    /**
     *
     */
    protected QuantityUsage() {
        super(DISCRIMINATOR_VALUE);
    }


    ///**** GETTERS ****///


    ///**** SETTERS ****///



    /**
     *
     * @param source
     * @return
     */
    @Override
    public QuantityUsage update(Usage source) {
        super.update(source);
        if (source instanceof QuantityUsage) {
            QuantityUsage src = (QuantityUsage) source;
            this.setQuantity(src.getQuantity());
        }
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public Usage createNewInstance() {
        return new QuantityUsage()
                .update(this);
    }



    ///**** FACTORIES ****///


}

