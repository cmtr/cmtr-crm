package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.invoice.AllowanceCharge;
import io.cmtr.crm.billing.model.invoice.InvoiceLineItem;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Set;


/**
 *
 */
@Component
public class AllowanceChargeServiceImpl implements AllowanceChargeService {

    @Override
    public Set<InvoiceLineItem> getLineItems(Supplier supplier, BillingAccount billingAccount, boolean isAllocated, ZonedDateTime periodEnd) {
        return null;
    }
}
