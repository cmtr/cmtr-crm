package io.cmtr.crm.mediation.model;

import io.cmtr.crm.order.model.Offer;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Mediation implements GenericEntity<Long, Mediation> {

    @Id
    @GeneratedValue
    private Long id;

    private Offer offer;

    @Override
    public Mediation update(Mediation source) {
        return null;
    }

    @Override
    public Mediation createNewInstance() {
        return null;
    }
}
