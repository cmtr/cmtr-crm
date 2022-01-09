package io.cmtr.crm.price.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.cmtr.crm.customer.model.Supplier;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 *
 * @author Harald Blikø
 */
@Setter
@Getter
@Accessors(chain = true)
@Table(name = "price")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UnitPrice.class, name = UnitPrice.DISCRIMINATOR_VALUE),
        @JsonSubTypes.Type(value = TieredUnitPrice.class, name = TieredUnitPrice.DISCRIMINATOR_VALUE)
})
public abstract class AbstractPrice implements GenericEntity<Long, AbstractPrice> {



    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;



    /**
     *
     */
    @NotNull
    private State state;



    /**
     *
     */
    @Column(
            updatable = false,
            insertable = false,
            nullable = false
    )
    private String type;



    /**
     *
     */
    @NotEmpty
    private String unit;



    /**
     *
     */
    @NotEmpty
    private String currency;



    /**
     *
     */
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private Supplier supplier;


    /**
     *
     */
    private ZonedDateTime validTo;


    ///**** CONSTRUCTOR ****///



    /**
     *
     * @param type
     */
    protected AbstractPrice(String type) {
        this.type = type;
    }



    ///**** GETTERS ****///


    @JsonInclude
    public UUID getSupplierId() {
        return this.supplier.getId();
    }


    ///**** SETTERS ****///



    /**
     *
     * @param source
     * @return
     */
    @Override
    public AbstractPrice update(AbstractPrice source) {
        return this
                .setUnit(source.getUnit())
                .setCurrency(source.getCurrency());
    }



    ///**** STATIC RESOURCES ****///


    /**
     *
     */
    public enum State {
        NEW,
        ACTIVE,
        DEACTIVETED,
        CLOSED
    }


}
