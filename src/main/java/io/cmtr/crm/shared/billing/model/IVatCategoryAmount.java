package io.cmtr.crm.shared.billing.model;

import java.math.BigDecimal;

/**
 *
 * Vat Category Amount
 *
 * @author Harald Blikø
 */
public interface IVatCategoryAmount extends IAmount, IVatCategory {

    BigDecimal getNetAmuont();

    default BigDecimal getVatAmount() {
        return getAmount();
    }

}
