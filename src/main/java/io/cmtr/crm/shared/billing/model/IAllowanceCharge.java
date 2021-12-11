package io.cmtr.crm.shared.billing.model;

import java.math.BigDecimal;

/**
 *
 *
 * @author Harald Blikø
 */
public interface IAllowanceCharge extends IAmount {

    boolean isCharge();

    default BigDecimal getNetAmount() {
        return getAmount();
    }

    // Supplier

    // BillingAccount


}
