package io.cmtr.crm.shared.price.model;

import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Price implements GenericEntity<Long, Price> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private String type;

    @NotNull
    private State state;

    @NotNull
    private String currency;

    @NotNull
    private String unit;

    protected Price(String type) {
        this.type = type;
    }


    public enum State {
        NEW,
        ACTIVE,
        DEACTIVE
    }
}
