package io.cmtr.crm.price.model;

import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
public class PriceFeed implements GenericEntity<Long, PriceFeed> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private State state;

    @Override
    public PriceFeed update(PriceFeed source) {
        return null;
    }

    @Override
    public PriceFeed createNewInstance() {
        return null;
    }

    public enum State {
        NEW,
        ACTIVE,
        DEACTIVETED,
        CLOSED
    }
}
