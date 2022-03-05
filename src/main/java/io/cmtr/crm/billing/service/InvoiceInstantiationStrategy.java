package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.billcycle.BillRun;

/**
 * Invoice Instantiation Strategy Interface
 *
 * @author Harald Blikø
 */
public interface InvoiceInstantiationStrategy {

    void instantiateInvoice(BillRun billRun);

}
