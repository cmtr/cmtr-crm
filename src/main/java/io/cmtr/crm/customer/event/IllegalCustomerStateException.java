package io.cmtr.crm.customer.event;


import io.cmtr.crm.customer.model.Customer;
import lombok.Getter;

public class IllegalCustomerStateException extends IllegalStateException {

    @Getter
    private final Customer source;

    public IllegalCustomerStateException(String s, Customer source) {
        super(s);
        this.source = source;
    }

    public IllegalCustomerStateException(Customer source) {
        this.source = source;
    }
}
