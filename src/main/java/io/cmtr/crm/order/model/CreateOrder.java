package io.cmtr.crm.order.model;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.product.model.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.ZonedDateTime;

/**
 * Create Order
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@DiscriminatorValue(CreateOrder.DISCRIMINATOR_VALUE)
public class CreateOrder extends Order{

    public static final String DISCRIMINATOR_VALUE = "CREATE";


    /**
     *
     */
    public Product product;



    /**
     *
     */
    public BillingAccount billingAccount;







    ///**** CONSTRUCTOR ****////



    protected CreateOrder() {
        super(DISCRIMINATOR_VALUE);
    }


    ///**** GETTERS *****///

    ///**** SETTERS ****///


    @Override
    public CreateOrder update(Order source) {
        return (CreateOrder) super
                .update(source);
    }

    @Override
    public CreateOrder createNewInstance() {
        return new CreateOrder()
                .update(this);
    }


    ///**** STATIC RESOURCES ****///

    ///**** FACTORIES ****///
}
