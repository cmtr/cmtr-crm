package io.cmtr.crm.shared.billing.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Amount
 *
 * A monetary amount is defined as having two decimal spaces of precision.
 *
 * The interface does not force the behaviour, but all implementation shall
 *
 * Based on Poppel Bis 3 Standard
 *
 * @author Harald Blikø
 */
public interface IAmount {

    // Default Precision
    int PRECISION = 2;

    // Default rounding mode
    RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * The interface does not enforce the 2 decimal precision.
     *
     * @return BigDecimal - positive with 2 decimal precision
     */
    BigDecimal getAmount();


}
