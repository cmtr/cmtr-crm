package io.cmtr.crm.customer.model;

import io.cmtr.crm.shared.contact.model.AbstractContact;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Map;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@DiscriminatorValue(Supplier.DISCRIMINATOR_VALUE)
public class Supplier extends Customer {

    public static final String DISCRIMINATOR_VALUE = "SUPPLIER";

    protected  Supplier() {
        super(DISCRIMINATOR_VALUE);
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
