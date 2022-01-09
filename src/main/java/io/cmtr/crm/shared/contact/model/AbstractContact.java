package io.cmtr.crm.shared.contact.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                name = BusinessContact.DISCRIMINATOR_VALUE,
                value = BusinessContact.class
        ),
        @JsonSubTypes.Type(
                name = PersonContact.DISCRIMINATOR_VALUE,
                value = PersonContact.class
        )
})
@Entity
@Table(name = "contacts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "contact_type",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class AbstractContact implements GenericEntity<Long, AbstractContact> {



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
            name = "contact_type",
            insertable = false,
            updatable = false,
            nullable = false
    )
    private String type;



    /**
     *
     */
    @OneToOne(
            cascade = CascadeType.ALL,
            optional = false,
            orphanRemoval = true
    )
    private AbstractAddress address;



    /**
     *
     */
    private String email;


    ///**** CONSTRUCTORS ****///



    /**
     *
     * @param type
     */
    AbstractContact(String type) {
        this.type = type;
    }



    ///**** SETTERS ****///



    /**
     *
     * @param source
     * @return
     */
    @Override
    public AbstractContact update(AbstractContact source) {
        return this
                .setEmail(source.getEmail())
                .setAddress(source.getAddress());
    }



}
