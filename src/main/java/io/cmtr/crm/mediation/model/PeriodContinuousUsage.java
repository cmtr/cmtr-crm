package io.cmtr.crm.mediation.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@DiscriminatorValue(PeriodContinuousUsage.DISCRIMINATOR_VALUE)
public class PeriodContinuousUsage extends ContinuousUsage {

    public static final String DISCRIMINATOR_VALUE = "SINGLE_DISCRETE";

    private ZonedDateTime start;
    private ZonedDateTime finish;

    protected PeriodContinuousUsage() {
        super(DISCRIMINATOR_VALUE);
    }

    @Override
    protected Usage setUnit(String unit) {
        ChronoUnit chronoUnit = ChronoUnit.valueOf(unit.toUpperCase());
        return super.setUnit(chronoUnit.toString());
    }

    private TemporalUnit getUnitAsTemporalUnit() {
        return ChronoUnit.valueOf(getUnit());
    }

    @Override
    public double getQuantity() {
        return Duration
                .between(start, finish)
                .get(getUnitAsTemporalUnit());
    }

    @Override
    public Usage update(Usage source) {
        return null;
    }

    @Override
    public Usage createNewInstance() {
        return null;
    }
}
