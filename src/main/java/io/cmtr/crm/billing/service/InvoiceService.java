package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.invoice.Invoice;
import io.cmtr.crm.billing.repository.InvoiceRepository;
import io.cmtr.crm.shared.generic.service.GenericService;
import org.springframework.stereotype.Service;



@Service
public class InvoiceService extends GenericService<Long, Invoice> implements IInvoiceService {

    public InvoiceService(InvoiceRepository repository) {
        super(repository);
    }

    @Override
    public Invoice save(Invoice invoice) {
        return null;
    }
}
