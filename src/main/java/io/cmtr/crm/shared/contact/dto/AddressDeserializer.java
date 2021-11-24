package io.cmtr.crm.shared.contact.dto;


import com.fasterxml.jackson.databind.JsonNode;
import io.cmtr.crm.shared.contact.config.AddressProperties;
import io.cmtr.crm.shared.contact.model.AbstractAddress;
import io.cmtr.crm.shared.contact.model.PostboxAddress;
import io.cmtr.crm.shared.contact.model.StreetAddress;
import io.cmtr.crm.shared.generic.dto.EntityDtoValidator;
import io.cmtr.crm.shared.generic.dto.GenericDeserializer;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.io.IOException;

@Component
public class AddressDeserializer extends GenericDeserializer<AbstractAddress> {

    private final AddressProperties addressProperties;

    public AddressDeserializer(
            @Autowired EntityDtoValidator<AbstractAddress> validator,
            @Autowired AddressProperties addressProperties
    ) {
        super(validator);
        this.addressProperties = addressProperties;
    }

    @Override
    protected AbstractAddress getEntity(JsonNode node) {
        val type = getAddressType(node);
        return getAddress(node, type);
    }

    private String getAddressType(JsonNode node) {
        val type = node.get("type").textValue().toUpperCase();
        if (!addressProperties.isType(type))
            throw new ValidationException(String.format("Unsupported address type '%s'", type));
        return type;
    }

    private AbstractAddress getAddress(JsonNode node, String type) {
        switch (type) {
            case "STREET":
                return deserializeStreetAddress(node);
            case "POSTBOX":
                return deserializePostboxAddress(node);
            default:
                throw new ValidationException(String.format("Unsupported address type '%s'", type));
        }
    }

    private AbstractAddress deserializeStreetAddress(JsonNode node) {
        val country = node.get("country").textValue();
        val zipCode = node.get("zipCode").textValue();
        val city = node.get("city").textValue();
        val street = node.get("street").textValue();
        val houseNr = node.get("houseNr").textValue();
        return StreetAddress.factory(country, zipCode, city, street, houseNr);
    }

    private AbstractAddress deserializePostboxAddress(JsonNode node) {
        val country = node.get("country").textValue();
        val zipCode = node.get("zipCode").textValue();
        val city = node.get("city").textValue();
        val postbox = node.get("postbox").textValue();
        return PostboxAddress.factory(country, zipCode, city, postbox);
    }

}
