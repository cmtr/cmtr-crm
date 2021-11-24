package io.cmtr.crm.customer.repository;

import io.cmtr.crm.customer.model.Customer;
import io.cmtr.crm.shared.generic.repository.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends GenericRepository<UUID, Customer> {

}
