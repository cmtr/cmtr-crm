package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.billcycle.BillRun;
import io.cmtr.crm.billing.model.invoice.AllowanceCharge;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;

import java.util.Set;

public interface IBillRunService {

    BillRun adhocInvoice(Supplier supplier, BillingAccount billingAccount, Set<AllowanceCharge> allowanceCharges);

}
