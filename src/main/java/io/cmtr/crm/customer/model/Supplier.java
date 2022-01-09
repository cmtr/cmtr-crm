package io.cmtr.crm.customer.model;

import io.cmtr.crm.shared.contact.model.AbstractContact;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Supplier
 *
 * Offers services and products to customers
 *
 * The Supplier is a Global Customer Entity.
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@DiscriminatorValue(Supplier.DISCRIMINATOR_VALUE)
public class Supplier extends Customer {

    public static final String DISCRIMINATOR_VALUE = "SUPPLIER";



    ///**** CONSTRUCTOR ****///



    /**
     *
     */
    protected  Supplier() {
        super(DISCRIMINATOR_VALUE);
    }



    ///**** SETTERS ****///



    /**
     *
     * @param source
     * @return
     */
    @Override
    public Customer update(Customer source) {
        return super.update(source);
    }



    /**
     *
     * @return
     */
    @Override
    public Customer createNewInstance() {
        return super.createNewInstance();
    }



    ///**** FACTORIES ****///



    /**
     *
     * @param parameters
     * @param customer
     * @return
     */
    public static Supplier factory(
            Map<String, String> parameters,
            AbstractContact customer
    ) {
        return (Supplier) new Supplier()
            .setParameters(parameters)
            .setCustomer(customer);
    }

}
