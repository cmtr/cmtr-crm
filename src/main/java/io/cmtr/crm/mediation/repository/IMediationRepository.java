package io.cmtr.crm.mediation.repository;

import io.cmtr.crm.mediation.model.Mediation;
import io.cmtr.crm.shared.generic.repository.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 *
 * @author Harald Blikø
 */
@Repository
public interface IMediationRepository extends GenericRepository<UUID, Mediation> {
}
