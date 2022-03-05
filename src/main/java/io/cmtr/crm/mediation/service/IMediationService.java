package io.cmtr.crm.mediation.service;

import io.cmtr.crm.mediation.model.Mediation;
import io.cmtr.crm.order.model.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Mediation Service Interface
 *
 * @author Harald Blikø
 */
public interface IMediationService {

    Page<Mediation> getPage(Pageable pageable);

    Mediation get(UUID id);

    List<Mediation> create(Offer offer);

    List<Mediation> update(Offer offer);

    void delete(Offer offer);

}
