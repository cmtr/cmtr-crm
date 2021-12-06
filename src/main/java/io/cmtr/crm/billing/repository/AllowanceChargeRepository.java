package io.cmtr.crm.billing.repository;

import io.cmtr.crm.billing.model.invoice.AllowanceCharge;
import io.cmtr.crm.shared.generic.repository.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowanceChargeRepository extends GenericRepository<Long, AllowanceCharge> {
}
