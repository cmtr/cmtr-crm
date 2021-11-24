package io.cmtr.crm.customer.dto;

import io.cmtr.crm.customer.model.BillingAccountType;
import io.cmtr.crm.customer.model.Customer;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class CustomerToBillingAccountMapper implements ICustomerToBillingAccountMapper {

    public BillingAccountType getBillingAccountType(Customer customer) {
        switch (customer.getType()) {
            case "PRIVATE":
                return BillingAccountType.PRIVATE;
            case "BUSINESS":
                return BillingAccountType.BUSINESS;
            default:
                throw new UnsupportedOperationException();
        }
    }

}
