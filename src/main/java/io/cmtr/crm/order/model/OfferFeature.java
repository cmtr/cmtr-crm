package io.cmtr.crm.order.model;

import io.cmtr.crm.mediation.model.Mediation;
import io.cmtr.crm.price.model.AbstractPrice;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 */
public class OfferFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Offer offer;

    private String name;

    private AbstractPrice price;

    @ManyToOne(
            optional = true
    )
    private Mediation mediation;

    public enum State {
        NEW,
        ACTIVE,
        DEACTIVATED,
        DELETED
    }
}
