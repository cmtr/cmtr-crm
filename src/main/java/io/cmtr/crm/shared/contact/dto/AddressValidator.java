package io.cmtr.crm.shared.contact.dto;

import io.cmtr.crm.shared.contact.config.AddressProperties;
import io.cmtr.crm.shared.contact.model.AbstractAddress;
import io.cmtr.crm.shared.generic.dto.EntityDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressValidator implements EntityDtoValidator<AbstractAddress> {

    private final AddressProperties addressProperties;

    public AddressValidator(
            @Autowired AddressProperties addressProperties
    ) {
        this.addressProperties = addressProperties;
    }

    @Override
    public void validate(AbstractAddress entity) {

    }
}
