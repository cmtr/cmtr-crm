package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.billcycle.BillRun;
import io.cmtr.crm.billing.model.invoice.AllowanceCharge;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;
import io.cmtr.crm.shared.generic.service.GenericService;
import io.cmtr.crm.shared.generic.service.IGenericService;

import java.util.Set;

/**
 * Bill Run Service Interface
 *
 * @author Harald Blikø
 */
public interface BillRunService extends IGenericService<Long, BillRun> {


}
