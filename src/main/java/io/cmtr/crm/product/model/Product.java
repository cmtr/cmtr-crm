package io.cmtr.crm.product.model;

import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
@NoArgsConstructor
public class Product implements GenericEntity<UUID, Product> {

    @Id
    private UUID id;

    private String type;

    private String name;

    private List<ProductFeature> features;

    // private List<Price> prices;

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
                .setId(UUID.randomUUID())
                .update(this);
    }

    public enum State {

    }

}
