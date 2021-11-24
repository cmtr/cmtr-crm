package io.cmtr.crm.customer.dto;

import io.cmtr.crm.customer.config.BillingProperties;
import io.cmtr.crm.customer.model.Customer;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerToBillingAccountMapper implements ICustomerToBillingAccountMapper {

    private final BillingProperties billingProperties;

    public CustomerToBillingAccountMapper(
            @Autowired BillingProperties billingProperties
    ) {
        this.billingProperties = billingProperties;
    }

    public String getBillingAccountType(Customer customer) {
        val customerType = customer.getType();
        return billingProperties
                .getCustomer()
                .getTypeMapping()
                .stream()
                .filter(source -> source.getSource().equals(customerType))
                .findAny()
                .orElseThrow(() -> new UnsupportedOperationException(String.format("Illegal customer type '%s'", customerType)))
                .getTarget();
    }

}
