package io.cmtr.crm.billing.repository;

import io.cmtr.crm.billing.model.billcycle.BillRun;
import io.cmtr.crm.shared.generic.repository.GenericRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface BillRunRepository extends GenericRepository<Long, BillRun> {


}
