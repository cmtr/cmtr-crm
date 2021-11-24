package io.cmtr.crm.customer.repository;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.shared.generic.repository.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillingAccountRepository extends GenericRepository<UUID, BillingAccount> {

}
