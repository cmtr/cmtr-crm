package io.cmtr.crm.product.model;

import io.cmtr.crm.price.model.AbstractPrice;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Product Feature
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Entity
public class ProductFeature implements GenericEntity<Long, ProductFeature> {



    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;



    /**
     *
     */
    @ManyToOne(
            optional = false,
            cascade = CascadeType.ALL
    )
    private Product product;



    /**
     *
     */
    private State state;


    /**
     *
     */
    @ManyToOne(
            optional = true,
            cascade = CascadeType.ALL
    )
    private AbstractPrice price;



    /**
     *
     */
    private String name;



    /**
     *
     */
    private boolean hidden;



    /**
     *
     */
    private String options;



    /**
     *
     */
    private String dependency;


    /**
     *
     */
    private MediationType mediation;

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
    protected ProductFeature() {
    }


    ///**** GETTERS *****///

    ///**** SETTERS ****///


    /**
     *
     * @param source
     * @return
     */
    @Override
    public ProductFeature update(ProductFeature source) {
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public ProductFeature createNewInstance() {
        return new ProductFeature()
                .update(this);
    }


    ///**** STATIC RESOURCES ****///



    /**
     *
     */
    public enum State {
        DRAFT,
        ACTIvE,
        DEACTIVE,
        DELETED
    }



    ///**** FACTORIES ****////



    /**
     *
     * @return
     */
    public static ProductFeature factory() {
        return new ProductFeature();
    }
}
