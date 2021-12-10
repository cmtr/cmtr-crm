package io.cmtr.crm.shared.billing.model;

import java.math.BigDecimal;

/**
 *
 * Vat Category Amount
 *
 * @author Harald Blikø
 */
public interface IVatCategoryAmount extends IVatCategory, IAmount {

    BigDecimal getNetAmount();

    /**
     *
     * @return unrounded vat value
     */
    default BigDecimal getVat() {
        return getNetAmount()
                .multiply(getRate())
                .divide(new BigDecimal("100"));
    }

    /**
     *
     * Set to precision 2 and half up rounding mode
     *
     * @return vat amount
     */
    default BigDecimal getVatAmount() {
        return getVat()
                .setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE);
    }

}
