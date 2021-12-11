package io.cmtr.crm.billing.model.invoice;

import io.cmtr.crm.shared.mediation.model.IQuantity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

/**
 * Immutable Embeddable Quantity Value Object
 *
 * @author Harald Blikø
 *
 */
@Getter
@EqualsAndHashCode
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Quantity implements IQuantity {



    /**
     * Unit of Measure
     */
    private final String unit;



    /**
     * Quantity
     */
    private final BigDecimal quantity;



    ///**** FACTORIES ****///



    public static Quantity factory(String unit, BigDecimal quantity) {
        return new Quantity(unit, quantity);
    }


    public static Quantity factory(IQuantity quantity) {
        return factory(quantity.getUnit(), quantity.getQuantity());
    }
}
