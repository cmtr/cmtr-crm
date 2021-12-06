package io.cmtr.crm.billing.repository;

import io.cmtr.crm.billing.model.billcycle.BillCycle;
import io.cmtr.crm.shared.generic.repository.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface BillCycleRepository extends GenericRepository<UUID, BillCycle> {
}
