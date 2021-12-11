package io.cmtr.crm.shared.billing.model;

import io.cmtr.crm.shared.mediation.model.IQuantity;

import java.math.BigDecimal;

/**
 *
 * @author Harald Blikø
 */
public interface IUnitPrice extends IMonetary {



    /**
     *
     * @return
     */
    BigDecimal getGrossPrice();



    /**
     *
     * @return
     */
    BigDecimal getGrossBasePriceQuantity();



    /**
     *
     * @return
     */
    BigDecimal getUnitPriceDiscount();



    /**
     *
     * @return
     */
    String getUnit();



    /**
     *
     * @return
     */
    default BigDecimal getUnitNetPrice() {
        return getNetPrice()
                .divide(getGrossBasePriceQuantity());
    }



    /**
     *
     * @return
     */
    default BigDecimal getNetPrice() {
        return getGrossPrice()
                .multiply(getUnitPriceDiscount())
                .divide(new BigDecimal("100"));
    }



    /**
     *
     * @param quantity - quantity
     * @return net price for given quantity
     */
    default BigDecimal getNetAmount(BigDecimal quantity) {
        return getNetPrice()
                .divide(getGrossBasePriceQuantity())
                .multiply(quantity)
                .setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE);
    }



    /**
     *
     * @param quantity
     * @return
     */
    default BigDecimal getNetAmount(IQuantity quantity) {
        if (!getUnit().equals(quantity.getUnit()))
            throw new IllegalArgumentException("Unit in quantity and price must match");
        return getNetAmount(quantity.getQuantity());
    }

}
