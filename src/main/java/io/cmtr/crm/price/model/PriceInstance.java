package io.cmtr.crm.price.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;



/**
 * Price Instance
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Table(name = "price_instances")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class PriceInstance implements GenericEntity<Long, PriceInstance> {


    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;



    /**
     *
     */
    @Column(
            name = "price_instance_type",
            updatable = false,
            insertable = false,
            nullable = false
    )
    private String type;



    /**
     *
     */
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            optional = false
    )
    @JsonIgnore
    private AbstractPrice price;



    /**
     *
     */
    @NotNull
    private ZonedDateTime validFrom;




    ///**** CONSTRUCTOR ****///



    protected PriceInstance(String type) {
        this.type = type;
    }


    ///**** GETTERS ****///



    /**
     *
     * @return
     */
    @JsonInclude
    public Long getPriceId() {
        return price.getId();
    }


    ///**** SETTERS ****///



    /**
     *
     * @param validFrom
     * @return
     */
    public PriceInstance setValidFrom(ZonedDateTime validFrom) {
        // This makes sense, but also restricts many use cases where the price information
        // is not available at until after the fact. E.g. spot-prices from external sources
        // if (validFrom.isBefore(ZonedDateTime.now())) throw new IllegalArgumentException("Cannot set valid from to past date");
        this.validFrom = validFrom;
        return this;
    }



    /**
     *
     * @param source
     * @return
     */
    @Override
    public PriceInstance update(PriceInstance source) {
        if (this.getValidFrom().isBefore(ZonedDateTime.now()))
            throw new IllegalStateException("Cannot modify Price Instance with past Valid From date");
        this.setValidFrom(source.getValidFrom());
        this.setPrice(source.getPrice());
        return this;
    }


}
