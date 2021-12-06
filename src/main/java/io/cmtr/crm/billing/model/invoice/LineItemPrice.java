package io.cmtr.crm.billing.model.invoice;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * Immutable Embeddable LineItemPrice Value Object
 *
 * @author Harald Blikø
 */
@Getter
@EqualsAndHashCode
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LineItemPrice {

    public static final int PRECISION = 2;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    private BigDecimal grossPrice;

    private BigDecimal grossPriceBaseQuantity;

    /**
     * Will be divided by 100.0
     *
     * @param discount - as percent 0.0 to 100.0
     */
    private BigDecimal unitPriceDiscount;


    public BigDecimal getNetPrice() {
        return grossPrice
                .multiply(unitPriceDiscount)
                .divide(new BigDecimal("100"));
    }

    public BigDecimal getNetAmount(BigDecimal quantity) {
        return getNetPrice()
                .divide(grossPriceBaseQuantity)
                .multiply(quantity)
                .setScale(PRECISION, ROUNDING_MODE);
    }
}
