package io.cmtr.crm.customer.dto;

import io.cmtr.crm.customer.model.BillingAccountType;
import io.cmtr.crm.customer.model.Customer;

public interface ICustomerToBillingAccountMapper {

    BillingAccountType getBillingAccountType(Customer customer);

}
