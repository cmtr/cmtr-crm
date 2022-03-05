package io.cmtr.crm.order.model;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Abstract Order
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@Accessors(chain = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Order implements GenericEntity<Long, Order> {



    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     *
     */
    @NotNull
    @ManyToOne(
            fetch = FetchType.EAGER,
            optional = false
    )
    private BillingAccount billingAccount;



    /**
     *
     */
    private String type;



    /**
     *
     */
    public ZonedDateTime at;



    /**
     *
     */
    @CreationTimestamp
    private LocalDateTime createdAt;



    /**
     *
     */
    @UpdateTimestamp
    private LocalDateTime modifiedAt;



    ///**** CONSTRUCTOR *****///



    /**
     *
     * @param type
     */
    protected Order(String type) {
        this.type = type;
    }


    ///**** GETTERS *****///

    ///**** SETTERS ****///



    /**
     *
     * @param source
     * @return
     */
    @Override
    public Order update(Order source) {
        return this;
    }



    ///**** STATIC RESOURCES ****///



    /**
     *
     */
    public enum State {
        NEW,
        IN_PROGRESS,
        PARTIALLY_COMPLETE,
        COMPLETE,
        FAILED,
        CANCELLED
    }

}
