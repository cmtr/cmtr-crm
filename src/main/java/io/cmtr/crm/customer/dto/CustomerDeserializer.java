package io.cmtr.crm.customer.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.cmtr.crm.customer.config.CustomerProperties;
import io.cmtr.crm.customer.model.Customer;
import io.cmtr.crm.shared.contact.model.AbstractContact;
import io.cmtr.crm.shared.generic.dto.EntityDtoValidator;
import io.cmtr.crm.shared.generic.dto.GenericDeserializer;
import lombok.Getter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomerDeserializer extends GenericDeserializer<Customer> {

    @Getter
    private final StdDeserializer<AbstractContact> contactDeserializer;
    @Getter
    private final CustomerProperties customerProperties;

    public CustomerDeserializer(
            @Autowired EntityDtoValidator<Customer> validator,
            @Autowired StdDeserializer<AbstractContact> contactDeserializer,
            @Autowired CustomerProperties customerProperties
    ) {
        super(validator);
        this.contactDeserializer = contactDeserializer;
        this.customerProperties = customerProperties;
    }

    @Override
    protected Customer getEntity(JsonNode node) throws IOException {
        val type = getCustomerType(node);
        val email = node.get("email").toString();
        val parameters = getCustomerParameters(node.get("parameters"));
        val contact = deserializeSubtree(node.get("customer").toString(), contactDeserializer);
        return Customer.parse(type, parameters, contact, email);
    }

    private Map<String, String> getCustomerParameters(JsonNode node) {
        if (node == null) return Collections.emptyMap();

        Map<String, String> parameters = new HashMap<>();
        node
            .fields()
            .forEachRemaining((e) -> parameters.put(e.getKey(), e.getValue().asText()));
        return parameters;
    }

    private String getCustomerType(JsonNode node) {
        String type = node.get("type").textValue().toUpperCase();
        if (!customerProperties.isType(type))
            throw new UnsupportedOperationException(String.format("Illegal customer type '%s'", type));

        return type;
    }

}