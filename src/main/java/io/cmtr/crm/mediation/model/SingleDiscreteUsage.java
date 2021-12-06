package io.cmtr.crm.mediation.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@EqualsAndHashCode
@Entity
@DiscriminatorValue(SingleDiscreteUsage.DISCRIMINATOR_VALUE)
public class SingleDiscreteUsage extends DiscreteUsage {

    public static final String DISCRIMINATOR_VALUE = "SINGLE_DISCRETE";

    protected SingleDiscreteUsage() {
        super(DISCRIMINATOR_VALUE);
    }

    @Override
    public double getQuantity() {
        return 1;
    }

    @Override
    public Usage update(Usage source) {
        return null;
    }

    @Override
    public Usage createNewInstance() {
        return new SingleDiscreteUsage()
                .setUnit(this.getUnit());
    }

}
