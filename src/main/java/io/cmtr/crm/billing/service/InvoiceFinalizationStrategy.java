package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.invoice.Invoice;

/**
 * Invoice Finalization Strategy Interface
 *
 * @author Harald Blikø
 */
public interface InvoiceFinalizationStrategy {



    /**
     *
     * @param id
     * @return
     */
    Invoice finalizeInvoice(Long id);



    /**
     *
     * @param invoice
     * @return
     */
    Invoice finalizeInvoice(Invoice invoice);


}
