package io.cmtr.crm.shared.contact.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @implNote Modeled as Value Object
 * @author harald
 */
@Getter(AccessLevel.PRIVATE)
@Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@DiscriminatorValue(StreetAddress.DISCRIMINATOR_VALUE)
public class StreetAddress extends AbstractAddress {

    public static final String DISCRIMINATOR_VALUE = "STREET";

    private String street;

    private String houseNr;

    public StreetAddress() {
        super(DISCRIMINATOR_VALUE);
    }

    public String getAddress() {
        return String.join(" ", getStreet(), getHouseNr());
    }


    @Override
    public AbstractAddress update(AbstractAddress source) {
        if (!(source instanceof StreetAddress))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incompatible Address Type");
        super.update(source);
        StreetAddress src = (StreetAddress) source;
        return this
                .setStreet(src.getStreet())
                .setHouseNr(src.getHouseNr());
    }

    @Override
    public AbstractAddress createNewInstance() {
        return new StreetAddress()
                .update(this);
    }

    public static StreetAddress factory(
            String country,
            String zipCode,
            String city,
            String street,
            String houseNr
    ) {
        return (StreetAddress) new StreetAddress()
                .setStreet(street)
                .setHouseNr(houseNr)
                .setCountry(country)
                .setZipCode(zipCode)
                .setCity(city);
    }

}
