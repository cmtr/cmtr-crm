package io.cmtr.crm.mediation.model;

import io.cmtr.crm.shared.generic.model.GenericEntity;
import io.cmtr.crm.shared.mediation.model.IUsage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Usage
 *
 * @author Harald Blikø
 *
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@Table(name = "usages")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "usage_type")
public abstract class Usage implements IUsage, GenericEntity<Long, Usage> {



    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;



    /**
     *
     */
    @Column(
        name = "usage_type",
        nullable = false,
        updatable = false,
        insertable = false
    )
    private String type;



    /**
     *
     */
    @NotNull
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    private Mediation mediation;



    /**
     * Time and date of usage
     */
    private ZonedDateTime at;


    ///**** CONSTRUCTOR ****///



    /**
     *
     * @param type
     */
    protected Usage(String type) {
        this.type = type;
    }



    ///**** GETTERS ****///



    /**
     *
     * @return
     */
    public abstract BigDecimal getQuantity();



    /**
     *
     * @return
     */
    @Override
    public String getUnit() {
        return this.mediation.getUnit();
    }


    ///**** SETTERS ****///



    /**
     *
     * @param source
     * @return
     */
    @Override
    public Usage update(Usage source) {
        return this;
    }



    ///**** STATIC RESOURCES ****///



    enum State {
        NEW,
        ACTIVE,
        COMPLETE,
        ALLOCATED
    }
}
