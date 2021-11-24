package io.cmtr.crm.customer.event;

import io.cmtr.crm.customer.model.Customer;
import io.cmtr.crm.shared.generic.event.CrudEventType;
import io.cmtr.crm.shared.generic.event.GenericCrudEvent;

import java.util.UUID;

public class CustomerEvent extends GenericCrudEvent<Customer> {

    public CustomerEvent(Customer current, Customer previous, CrudEventType type, String message, UUID id) {
        super(current, previous, type, message, id);
    }

    public CustomerEvent(Customer current, Customer previous, CrudEventType type, String message) {
        super(current, previous, type, message);
    }

    public CustomerEvent(Customer current, Customer previous, CrudEventType type) {
        super(current, previous, type);
    }

    public CustomerEvent(Customer current, CrudEventType type, String message) {
        super(current, type, message);
    }

    public CustomerEvent(Customer current, CrudEventType type) {
        super(current, type);
    }
}
