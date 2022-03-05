package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.invoice.Invoice;

public interface InvoiceFinalizationStrategy {

    Invoice finalizeInvoice(Long id);

    Invoice finalizeInvoice(Invoice invoice);

}
