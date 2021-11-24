package io.cmtr.crm.shared.contact.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@EqualsAndHashCode
@Entity
@Table(name = "addresses")
@Inheritance(
        strategy = InheritanceType.SINGLE_TABLE
)
@DiscriminatorColumn(
        name = "address_type",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class AbstractAddress implements GenericEntity<Long, AbstractAddress>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;

    @Column(
            name = "address_type",
            insertable = false,
            updatable = false,
            nullable = false
    )
    private String type;

    private String country;

    private String zipCode;

    private String city;

    private String street;

    private String houseNr;

    private String postbox;


    AbstractAddress(String addressType) {
        this.type = addressType;
    }

    public abstract String getAddress();

    @Override
    public AbstractAddress update(AbstractAddress source) {
        return this
                .setCountry(source.getCountry())
                .setZipCode(source.getZipCode())
                .setCity(source.getCity());
    }

}