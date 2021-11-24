package io.cmtr.crm.customer.dto;

import io.cmtr.crm.customer.model.Customer;

public interface ICustomerToBillingAccountMapper {

    String getBillingAccountType(Customer customer);

}
