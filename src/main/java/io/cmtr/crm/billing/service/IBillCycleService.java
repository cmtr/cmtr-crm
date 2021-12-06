package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.billcycle.BillCycle;
import io.cmtr.crm.customer.model.BillingAccount;

public interface IBillCycleService {

    BillingAccount moveBillCycle(BillingAccount billingAccount, BillCycle billCycle);
}
