package io.cmtr.crm.billing.repository;

import io.cmtr.crm.billing.model.invoice.Invoice;
import io.cmtr.crm.shared.generic.repository.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends GenericRepository<Long, Invoice> {

}
