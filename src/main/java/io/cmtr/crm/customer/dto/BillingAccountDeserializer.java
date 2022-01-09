package io.cmtr.crm.customer.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.cmtr.crm.customer.config.BillingProperties;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Customer;
import io.cmtr.crm.shared.contact.model.AbstractContact;
import io.cmtr.crm.shared.generic.dto.EntityDtoValidator;
import io.cmtr.crm.shared.generic.dto.GenericDeserializer;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class BillingAccountDeserializer extends GenericDeserializer<BillingAccount> {

    private final StdDeserializer<AbstractContact> contactDeserializer;
    private final BillingProperties billingProperties;

    public BillingAccountDeserializer(
            @Autowired EntityDtoValidator<BillingAccount> validator,
            @Autowired StdDeserializer<AbstractContact> contactDeserializer,
            @Autowired BillingProperties billingProperties
    ) {
        super(validator);
        this.contactDeserializer = contactDeserializer;
        this.billingProperties = billingProperties;
    }

    @Override
    protected BillingAccount getEntity(JsonNode node) throws IOException {
        val type = getBillingAccountType(node);
        val customer = getCustomer(node);
        val owner = deserializeSubtree(node.get("owner").toString(), contactDeserializer);
        val recipient = deserializeSubtree(node.get("recipient").toString(), contactDeserializer);
        val parameters = getBillingAccountParameters(node);
        return BillingAccount.factory(type, customer, owner, recipient, parameters);
    }

    private String getBillingAccountType(JsonNode node) {
        val type = node.get("type").toString().toUpperCase();
        return type;
    }

    private Map<String, String> getBillingAccountParameters(JsonNode node) {
        Map<String, String> parameters = new HashMap<>();
        node.fields().forEachRemaining((e) -> parameters.put(e.getKey(), e.getValue().asText()));
        return parameters;
    }

    private Customer getCustomer(JsonNode node) {
        UUID id = UUID.fromString(node.get("customerId").textValue());
        return Customer.factory(id);
    }

}
