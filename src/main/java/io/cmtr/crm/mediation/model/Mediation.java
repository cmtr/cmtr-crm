package io.cmtr.crm.mediation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.cmtr.crm.order.model.Offer;
import io.cmtr.crm.order.model.OfferFeature;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Mediation
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mediations")
@Entity
public class Mediation implements GenericEntity<UUID, Mediation> {



    /**
     *
     */
    @Id
    private UUID id;



    /**
     *
     */
    @NotNull
    private State state;



    /**
     *
     */
    @NotEmpty
    private String unit;



    /**
     *
     */
    @JsonIgnore
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Usage> usages = new LinkedList<>();



    ///**** CONSTRUCTORS ****///



    ///**** GETTERS *****///



    ///**** SETTERS ****///



    /**
     *
     * @param source
     * @return
     */
    @Override
    public Mediation update(Mediation source) {
        return this.setUnit(source.unit);
    }



    /**
     *
     * @return
     */
    @Override
    public Mediation createNewInstance() {
        return new Mediation()
                .setId(UUID.randomUUID())
                .setState(State.NEW)
                .update(this);
    }


    ///**** STATIC RESOURCES ****///



    enum State {
        NEW,
        ACTIVE,
        COMPLETE
    }


    ///**** FACTORIES ****///

    /**
     *
     * @param offer
     * @param unit
     * @return
     */
    public static Mediation factory(
        Offer offer,
        String unit
    ) {
        return new Mediation()
                .setUnit(unit);
    }
}
