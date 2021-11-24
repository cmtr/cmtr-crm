package io.cmtr.crm.shared.contact.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter(AccessLevel.PRIVATE)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(PostboxAddress.DISCRIMINATOR_VALUE)
public class PostboxAddress extends AbstractAddress {

    public static final String DISCRIMINATOR_VALUE = "POSTBOX";

    public PostboxAddress() {
        super(DISCRIMINATOR_VALUE);
    }

    @Override
    public AbstractAddress setHouseNr(String houseNr) {
        return this;
    }

    @Override
    public AbstractAddress setStreet(String street) {
        return this;
    }

    @Override
    public String getAddress() {
        return String.join(" ", "PO", getPostbox());
    }

    @Override
    public AbstractAddress update(AbstractAddress source) {
        if (!(source instanceof PostboxAddress))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incompatible Address Type");
        super.update(source);
        return this
                .setPostbox(source.getPostbox());
    }

    @Override
    public AbstractAddress createNewInstance() {
        return new PostboxAddress()
                .update(this);
    }

    public static PostboxAddress factory(
            String country,
            String zipCode,
            String city,
            String postbox
    ) {
        return (PostboxAddress) new PostboxAddress()
                .setPostbox(postbox)
                .setCountry(country)
                .setZipCode(zipCode)
                .setCity(city);
    }
}
