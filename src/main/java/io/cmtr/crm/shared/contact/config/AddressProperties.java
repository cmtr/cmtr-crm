package io.cmtr.crm.shared.contact.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "address")
public class AddressProperties {

    private List<String> type;


    public boolean isType(String type) {
        return this
                .type
                .stream()
                .anyMatch((e) -> e.equals(type));
    }
}
