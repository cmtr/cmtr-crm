package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.invoice.AllowanceCharge;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;

import java.util.Set;

public interface IAllowanceChargeService {

    Set<AllowanceCharge> getAllowanceChage(Supplier supplier, BillingAccount billingAccount, boolean isAllocated);

}
