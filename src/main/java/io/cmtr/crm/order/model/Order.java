package io.cmtr.crm.order.model;

import io.cmtr.crm.customer.model.BillingAccount;
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
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
// @Entity
@NoArgsConstructor
public class Order implements GenericEntity<Long, Order> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private BillingAccount billingAccount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Override
    public Order update(Order source) {
        return this;
    }

    @Override
    public Order createNewInstance() {
        return new Order()
                .update(this);
    }

    public enum State {
        NEW,
        IN_PROGRESS,
        PARTIALLY_COMPLETE,
        COMPLETE,
        FAILED,
        CANCELLED
    }

}
