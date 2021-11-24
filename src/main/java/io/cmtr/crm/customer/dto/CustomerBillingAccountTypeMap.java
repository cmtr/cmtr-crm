package io.cmtr.crm.customer.dto;

import io.cmtr.crm.billing.config.BillingProperties;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Customer;
import io.cmtr.crm.shared.generic.dto.EntityTypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerBillingAccountTypeMap implements EntityTypeMap<Customer, BillingAccount> {

    private final BillingProperties billingProperties;

    public CustomerBillingAccountTypeMap(
            @Autowired BillingProperties billingProperties
    ) {
        this.billingProperties = billingProperties;
    }

    @Override
    public String get(String type) {
        return null;
    }
}
