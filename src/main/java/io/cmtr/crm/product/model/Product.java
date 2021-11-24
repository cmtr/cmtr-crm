package io.cmtr.crm.product.model;

import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
// @Entity
@NoArgsConstructor
public class Product implements GenericEntity<Long, Product> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Override
    public Product update(Product source) {
        return this;
    }

    @Override
    public Product createNewInstance() {
        return new Product()
                .update(this);
    }

}
