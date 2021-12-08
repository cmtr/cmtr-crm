package io.cmtr.crm.billing.model.billcycle;

import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@Table(name = "bill_runs")
public abstract class BillRun implements GenericEntity<Long, BillRun> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private State state;

    private String type;

    @ManyToOne(
            optional = true,
            targetEntity = BillCycle.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "bill_cycle_id")
    private BillCycle billCycle;

    // private Supplier supplier
    // private Set<BillingAccount> billingAccounts;

    // private List<Invoices> invoices


    protected BillRun(String type) {
        this.type = type;
    }



    /**
     *
     * @param source
     * @return
     */
    @Override
    public BillRun update(BillRun source) {
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public BillRun createNewInstance() {
        return this.setState(State.NEW);
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
