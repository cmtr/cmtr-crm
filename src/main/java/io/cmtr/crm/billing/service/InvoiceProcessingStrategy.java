package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.invoice.Invoice;

/**
 * Invoice Processing Strategy Interface
 *
 *
 * @author Harald Blikø
 */
public interface InvoiceProcessingStrategy {

    Invoice processInvoice(Long id);

    Invoice processInvoice(Invoice invoice);

}
