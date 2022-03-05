package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.invoice.AllowanceCharge;
import io.cmtr.crm.billing.model.invoice.InvoiceLineItem;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;

import io.cmtr.crm.shared.generic.service.IGenericService;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Harald Blikø
 */
public interface AllowanceChargeService extends IGenericService<Long, AllowanceCharge> {


    /**
     *
     * @param supplier
     * @param billingAccount
     * @param isAllocated
     * @param periodEnd
     * @return
     */
    Set<InvoiceLineItem> getLineItems(
            Supplier supplier,
            BillingAccount billingAccount,
            Optional<Boolean> isAllocated,
            ZonedDateTime periodEnd
    );

    default Set<InvoiceLineItem> getLineItems(
            Supplier supplier,
            BillingAccount billingAccount,
            Optional<Boolean> isAllocated
    ) {
        return getLineItems(supplier, billingAccount, isAllocated, ZonedDateTime.now());
    }

    default Set<InvoiceLineItem> getLineItems(
            Supplier supplier,
            BillingAccount billingAccount
    ) {
        return getLineItems(supplier, billingAccount, Optional.empty(), ZonedDateTime.now());
    }
}
