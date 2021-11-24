package io.cmtr.crm.customer.dto;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.shared.generic.dto.EntityDtoValidator;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class BillingAccountValidator implements EntityDtoValidator<BillingAccount> {

    @Override
    public void validate(BillingAccount entity) {

    }

}
