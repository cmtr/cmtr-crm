package io.cmtr.crm.mediation.model;

import io.cmtr.crm.order.model.Offer;


import java.util.List;
import java.util.UUID;

/**
 *
 */
public interface IOfferMediationStrategy {

    List<Mediation> create(Offer offer);

    List<Mediation> update(Offer offer);

    List<UUID> delete(Offer offer);

}
