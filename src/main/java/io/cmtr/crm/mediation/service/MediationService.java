package io.cmtr.crm.mediation.service;

import io.cmtr.crm.mediation.model.IOfferMediationStrategy;
import io.cmtr.crm.mediation.model.Mediation;
import io.cmtr.crm.mediation.repository.IMediationRepository;
import io.cmtr.crm.order.model.Offer;
import io.cmtr.crm.shared.generic.event.CrudEventType;
import io.cmtr.crm.shared.generic.service.GenericService;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

/**
 * Mediation Service Implementation
 *
 * @author Harald Blikø
 */
@Service
public class MediationService implements IMediationService{

    private final IMediationRepository mediationRepository;
    private final IOfferMediationStrategy offerMediationStrategy;

    public MediationService(
            @Autowired IMediationRepository mediationRepository,
            @Autowired IOfferMediationStrategy offerMediationStrategy
    ) {
        this.mediationRepository = mediationRepository;
        this.offerMediationStrategy = offerMediationStrategy;
    }

    /**
     *
     * @param pageable
     * @return
     */
    public Page<Mediation> getPage(Pageable pageable) {
        return mediationRepository.findAll(pageable);
    }



    /**
     *
     * @param id
     * @return
     */
    public Mediation get(UUID id) {
        return mediationRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Entity with id '%s'not found", id)));
    }



    /**
     *
     * @param offer
     * @return
     */
    public List<Mediation> create(Offer offer) {
        return mediationRepository
                .saveAll(offerMediationStrategy.create(offer));
    }



    /**
     *
     * @param offer
     * @return
     */
    public List<Mediation> update(Offer offer) {
        return mediationRepository
                .saveAll(offerMediationStrategy.update(offer));
    }


    /**
     *
     * @param offer
     */
    public void delete(Offer offer) {
        List<UUID> mediationIds = offerMediationStrategy.delete(offer);
        mediationRepository.findAllById(mediationIds);
        mediationRepository.deleteAllById(mediationIds);
    }



    /**
     *
     * @param current
     * @param prev
     * @param type
     */
    public void publish(Mediation current, Mediation prev, CrudEventType type) {

    }
}
