package io.cmtr.crm.customer;

import io.cmtr.crm.customer.dto.ICustomerToBillingAccountMapper;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Customer;
import io.cmtr.crm.customer.model.Supplier;
import io.cmtr.crm.shared.contact.ContactTestUtil;


import java.util.Collections;

public class CustomerTestUtil {

    public static Supplier getSupplier() {
        return (Supplier) Supplier
                .factory(
                        Collections.emptyMap(),
                        ContactTestUtil.getBusinessContact()
                )
                .createNewInstance();
    }

    public static Customer getCustomer() {
        return Customer
                .factory(
                        "PERSON",
                        Collections.EMPTY_MAP,
                        ContactTestUtil.getPersonContact(),
                        "john@email.com"
                )
                .createNewInstance();
    }

    public static BillingAccount getBillingAccount() { ;
        return BillingAccount.createNewInstance(getCustomer(), e -> e.getType());
    }
}
