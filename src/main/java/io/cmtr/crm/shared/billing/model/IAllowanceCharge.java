package io.cmtr.crm.shared.billing.model;

import java.math.BigDecimal;

/**
 *
 *
 * @author Harald Blikø
 */
public interface IAllowanceCharge extends IAmount, IMonetary {

    boolean isCharge();

    default BigDecimal getNetAmount() {
        return getAmount();
    }

    // Supplier

    // BillingAccount


}
