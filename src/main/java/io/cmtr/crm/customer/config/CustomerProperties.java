package io.cmtr.crm.customer.config;

import io.cmtr.crm.shared.generic.dto.ConfigurationDefinedParameter;
import io.cmtr.crm.shared.generic.dto.GenericRelationship;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 *
 * https://www.baeldung.com/spring-yaml-inject-map
 *
 * @author Harald Blikø
 */
@Data
@Component
@ConfigurationProperties(prefix = "customer")
public class CustomerProperties {

    private List<String> type;

    private Contact contact;

    private Map<String, ConfigurationDefinedParameter> parameters;

    public boolean isType(String type) {
        return this.type
                .stream()
                .anyMatch(e -> e.equals(type));

    }

    @Data
    @Component
    public static class Contact {

        private List<GenericRelationship<String, String>> typeMapping;

    }
}
