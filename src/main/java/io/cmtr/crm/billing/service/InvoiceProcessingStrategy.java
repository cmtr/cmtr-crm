package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.invoice.Invoice;

public interface InvoiceProcessingStrategy {

    Invoice processInvoice(Long id);

    Invoice processInvoice(Invoice invoice);

}
