package io.cmtr.crm.product.model;

import io.cmtr.crm.price.model.AbstractPrice;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@Accessors(chain = true)
public class Product implements GenericEntity<UUID, Product> {



    /**
     *
     */
    @Id
    private UUID id;



    /**
     *
     */
    private State state;



    /**
     *
     */
    private String type;



    /**
     *
     */
    private String name;



    /**
     *
     */
    @OneToMany(
            mappedBy = "product_feature",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<ProductFeature> features;



    /**
     *
     */
    @ManyToOne(
            fetch = FetchType.EAGER,
            optional = true
    )
    private AbstractPrice price;



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
    protected Product() {
    }


    ///**** GETTERS *****///



    ///**** SETTERS ****///



    /**
     *
     * @param source
     * @return
     */
    @Override
    public Product update(Product source) {
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public Product createNewInstance() {
        return new Product()
                .setId(UUID.randomUUID())
                .update(this);
    }



    ///**** STATIC RESOURCES ****///



    /**
     *
     */
    public enum State {
        NEW,
        ACTIVE,
        COMPLETE
    }



    ///**** FACTORIES ****///



    /**
     *
     * @return
     */
    public static Product factory() {
        return new Product();
    }

}
