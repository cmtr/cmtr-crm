package io.cmtr.crm.shared.billing.model;

import java.math.BigDecimal;

/**
 * Vat Category
 *
 * @author Harald Blikø
 */
public interface IVatCategory {



    /**
     *
     * @return
     */
    String getCategory();



    /**
     *
     * @return
     */
    String getCurrency();



    /**
     *
     * @return
     */
    BigDecimal getRate();



    /**
     *
     * @param netAmount
     * @return
     */
    default BigDecimal getVatAmount(BigDecimal netAmount) {
        return netAmount
                .multiply(getRate())
                .divide(new BigDecimal("100"))
                .setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE);
    }
}
