package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.invoice.InvoiceLineItem;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;

import java.time.ZonedDateTime;
import java.util.Set;

public interface AllowanceChargeService {

    Set<InvoiceLineItem> getLineItems(
            Supplier supplier,
            BillingAccount billingAccount,
            boolean isAllocated,
            ZonedDateTime periodEnd
    );

}
