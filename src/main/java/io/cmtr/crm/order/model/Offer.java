package io.cmtr.crm.order.model;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.price.model.AbstractPrice;
import io.cmtr.crm.product.model.Product;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
public class Offer implements GenericEntity<Long, Offer> {



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
    private State state;



    /**
     *
     */
    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY
    )
    private BillingAccount billingAccount;



    /**
     *
     */
    @ManyToOne(
            fetch = FetchType.EAGER,
            optional = false
    )
    private Product product;



    /**
     *
     */
    @ManyToOne(
            optional = true,
            fetch = FetchType.EAGER
    )
    private AbstractPrice price;



    /**
     *
     */
    @OneToMany(
            mappedBy = "offer_feature",
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<OfferFeature> features;



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



    ///**** CONSTRUCTORS ****///



    /**
     *
     */
    protected Offer() {
    }



    ///**** GETTERS *****///



    ///**** SETTERS ****///



    /**
     *
     * @param source
     * @return
     */
    @Override
    public Offer update(Offer source) {
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public Offer createNewInstance() {
        return new Offer()
                .update(this);
    }



    ///**** STATIC RESOURCES ****///



    /**
     *
     */
    public enum State {
        NEW,
        PENDING,
        ACTIVE,
        DEACTIVATED,
        DELETED
    }



    ///**** FACTORIES ****///



    public static Offer factory(

    ) {
        return new Offer();
    }

}
