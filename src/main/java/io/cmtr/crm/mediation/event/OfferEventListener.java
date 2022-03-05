package io.cmtr.crm.mediation.event;

import io.cmtr.crm.mediation.service.IMediationService;
import io.cmtr.crm.order.model.Offer;
import io.cmtr.crm.shared.generic.event.GenericCrudEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Offer CRUD Event Listener
 *
 * @author Harald Blikø
 */
@Component
public class OfferEventListener {

    private IMediationService mediationService;

    public OfferEventListener(
            @Autowired IMediationService mediationService
    ) {
        this.mediationService = mediationService;
    }

    public void handleOfferCrudEvent(GenericCrudEvent<Offer> offerEvent) {
        // TODO
    }

}
