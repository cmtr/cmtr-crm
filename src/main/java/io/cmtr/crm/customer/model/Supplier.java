package io.cmtr.crm.customer.model;

import io.cmtr.crm.shared.contact.model.AbstractContact;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Entity
@DiscriminatorValue(Supplier.DISCRIMINATOR_VALUE)
public class Supplier extends Customer {

    public static final String DISCRIMINATOR_VALUE = "SUPPLIER";

    public Supplier() {
        super(DISCRIMINATOR_VALUE);
    }

    @Override
    protected Customer setType(@NotNull(message = "Customer type cannot be null") String type) {
        return this;
    }

    @Override
    public Customer update(Customer source) {
        return super.update(source);
    }

    @Override
    public Customer createNewInstance() {
        return super.createNewInstance();
    }

    public static Supplier factory(
            Map<String, String> parameters,
            AbstractContact customer,
            String email
    ) {
        return (Supplier) new Supplier()
            .setParameters(parameters)
            .setCustomer(customer)
            .setEmail(email);
    }
}
