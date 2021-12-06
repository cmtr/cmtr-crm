package io.cmtr.crm.billing.model.billcycle;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
public class BillRun implements GenericEntity<Long, BillRun> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private State state;

    private String type;

    private List<BillingAccount> billingAccounts;

    protected BillRun() {
    }

    @Override
    public BillRun update(BillRun source) {
        return null;
    }

    @Override
    public BillRun createNewInstance() {
        return null;
    }

    public enum State {
        NEW,
        IN_PROGRESS,
        COMPLETE,
        FAILED,
        PARTIALLY_FAILED,
        CANCELLED
    }

}
