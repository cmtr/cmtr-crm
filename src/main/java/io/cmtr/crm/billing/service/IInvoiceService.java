package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.invoice.Invoice;
import io.cmtr.crm.shared.generic.service.IGenericService;

/**
 * Invoice Service Interface
 *
 * @author Harald Blikø
 */
public interface IInvoiceService extends IGenericService<Long, Invoice> {

    Invoice get(Long id);

    Invoice save(Invoice invoice);
}
