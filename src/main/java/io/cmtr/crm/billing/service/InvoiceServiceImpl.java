package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.invoice.Invoice;
import io.cmtr.crm.billing.repository.InvoiceRepository;
import io.cmtr.crm.shared.generic.service.GenericService;
import org.springframework.stereotype.Service;


/**
 * Invoice Service Implementation
 *
 * @author Harald Blikø
 */
@Service
public class InvoiceServiceImpl extends GenericService<Long, Invoice> implements InvoiceService {



    /**
     *
     * @param repository
     */
    public InvoiceServiceImpl(InvoiceRepository repository) {
        super(repository);
    }



    /**
     *
     * @param invoice
     * @return
     */
    @Override
    public Invoice save(Invoice invoice) {
        return null;
    }

}
