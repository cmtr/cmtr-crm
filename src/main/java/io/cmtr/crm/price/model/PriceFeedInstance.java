package io.cmtr.crm.price.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PriceFeedInstance implements GenericEntity<Long, PriceFeedInstance> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @ManyToOne(
            optional = false
    )
    @JsonIgnore
    private PriceFeed priceFeed;

    @NotNull
    private ZonedDateTime validFrom;


    // private UnitPrice price;


    @JsonInclude
    public Long getPriceFeedId() {
        return priceFeed.getId();
    }

    @Override
    public PriceFeedInstance update(PriceFeedInstance source) {
        return null;
    }

    @Override
    public PriceFeedInstance createNewInstance() {
        return null;
    }
}
