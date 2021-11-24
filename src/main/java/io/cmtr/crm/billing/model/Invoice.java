package io.cmtr.crm.billing.model;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
@Entity
@NoArgsConstructor
public class Invoice implements GenericEntity<Long, Invoice> {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(
            fetch = FetchType.LAZY,
            targetEntity = BillingAccount.class,
            optional = false
    )
    private BillingAccount billingAccount;

    @Override
    public Invoice update(Invoice source) {
        return null;
    }

    @Override
    public Invoice createNewInstance() {
        return null;
    }


}
