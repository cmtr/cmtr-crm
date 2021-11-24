package io.cmtr.crm.shared.contact.dto;

import io.cmtr.crm.shared.contact.config.ContactProperties;
import io.cmtr.crm.shared.contact.model.AbstractContact;
import io.cmtr.crm.shared.generic.dto.EntityDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactValidator implements EntityDtoValidator<AbstractContact> {

    private final ContactProperties contactProperties;

    public ContactValidator(
            @Autowired ContactProperties contactProperties
    ) {
        this.contactProperties = contactProperties;
    }

    @Override
    public void validate(AbstractContact entity) {

    }

}
