package io.cmtr.crm.shared.contact.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.cmtr.crm.shared.contact.config.ContactProperties;
import io.cmtr.crm.shared.contact.model.*;
import io.cmtr.crm.shared.generic.dto.EntityDtoValidator;
import io.cmtr.crm.shared.generic.dto.GenericDeserializer;
import lombok.Getter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.io.IOException;

@Component
public class ContactDeserializer extends GenericDeserializer<AbstractContact> {

    @Getter
    private final StdDeserializer<AbstractAddress> addressDeserializer;
    private final ContactProperties contactProperties;

    public ContactDeserializer(
            @Autowired EntityDtoValidator<AbstractContact> validator,
            @Autowired StdDeserializer<AbstractAddress> addressDeserializer,
            @Autowired ContactProperties contactProperties
    ) {
        super(validator);
        this.addressDeserializer = addressDeserializer;
        this.contactProperties = contactProperties;
    }
    
    @Override
    protected AbstractContact getEntity(JsonNode node) throws IOException {
        val type = getContactType(node);
        val address = deserializeSubtree(node.get("address").toString(), addressDeserializer);
        return getContact(node, type, address);
    }

    private String getContactType(JsonNode node) {
        val type = node.get("type").textValue().toUpperCase();
        if (!contactProperties.isType(type))
            throw new ValidationException(String.format("Unsupported contact type '%s'", type));
        return type;
    }

    private AbstractContact getContact(
            JsonNode node,
            String type,
            AbstractAddress address
    ) {
        switch (type) {
            case "PERSON":
                return deserializePersonContact(node, address);
            case "BUSINESS":
                return deserializeBusinessContact(node, address);
            default:
                throw new ValidationException( "Unsupported Contact Type");
        }
    }

    private AbstractContact deserializePersonContact(JsonNode node, AbstractAddress address) {
        val firstName = node.get("firstName").textValue();
        val lastName = node.get("lastName").textValue();
        val email = node.get("email").textValue();
        return PersonContact.factory(email, firstName, lastName, address);
    }

    private AbstractContact deserializeBusinessContact(JsonNode node, AbstractAddress address) {
        val orgNr = node.get("orgNr").textValue();
        val legalName = node.get("legalName").textValue();
        val countryCode = node.get("countryCode").textValue();
        val businessName = node.get("businessName").textValue();
        val email = node.get("email").textValue();
        return BusinessContact.factory(email, orgNr, legalName,countryCode, businessName, address);
    }


}
