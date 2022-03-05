package io.cmtr.crm.mediation.repository;

import io.cmtr.crm.mediation.model.Usage;
import io.cmtr.crm.shared.generic.repository.GenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Usage Repository
 */
@Repository
public interface IUsageRepository extends GenericRepository<Long, Usage> {
}
