package io.cmtr.crm.mediation.model;

import io.cmtr.crm.mediation.repository.IMediationRepository;
import io.cmtr.crm.order.model.Offer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Offer Mediation Strategy Implementation
 *
 *
 * This strategy will create one mediation feed for each applicable Offer Feature
 * according to the Offer Features Life Cycle.
 *
 * TODO - Other types:
 * - One mediation feed can be linked to multiple offer features of the same offer
 * - One mediation feed can be linked to multiple offer feature of different offers
 *
 * @author Harald Blikø
 */
@Component
public class OfferMediationStrategy implements IOfferMediationStrategy {


    public List<Mediation> create(Offer offer) {
        /// Check if the Offer is active
        /// Get Offer Features
        /// Filter Offer Features for Active and No Mediation Feed
        /// Create Mediation Feeds
        /// Collect to list

        return null;
    }

    @Override
    public List<Mediation> update(Offer offer) {
        return null;
    }

    @Override
    public List<UUID> delete(Offer offer) {
        return null;
    }
}
