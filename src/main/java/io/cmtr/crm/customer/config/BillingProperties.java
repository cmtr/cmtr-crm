package io.cmtr.crm.customer.config;

import io.cmtr.crm.shared.generic.dto.ConfigurationDefinedParameter;
import io.cmtr.crm.shared.generic.dto.GenericRelationship;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "billingaccount")
public class BillingProperties {

    private List<String> type;

    private Customer customer;

    private Map<String, ConfigurationDefinedParameter> parameters;

    public boolean isType(String type) {
        return this.type
                .stream()
                .anyMatch(e -> e.equals(type));

    }

    @Data
    @Component
    public static class Customer {

        private boolean onCreateGenerateBillingAccount = false;
        private List<GenericRelationship<String, String>> typeMapping;

    }

    @Data
    @Component
    public static class Owner {

        private List<GenericRelationship<String, String>> typeMapping;

    }


}
