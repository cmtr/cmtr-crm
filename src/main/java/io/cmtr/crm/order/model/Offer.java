package io.cmtr.crm.order.model;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.product.model.Product;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
public class Offer implements GenericEntity<Long, Offer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private State state;

    private BillingAccount billingAccount;

    private Product product;

    private List<OfferFeature> features;

    @Override
    public Offer update(Offer source) {
        return null;
    }

    @Override
    public Offer createNewInstance() {
        return null;
    }

    public enum State {
        NEW,
        PENDING,
        ACTIVE,
        DEACTIVATED
    }
}
