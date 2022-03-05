package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.billcycle.BillRun;

public interface InvoiceInstantiationStrategy {

    void instantiateInvoice(BillRun billRun);

}
