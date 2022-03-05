package io.cmtr.crm.billing.model.billcycle;

import io.cmtr.crm.billing.model.invoice.Invoice;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Bill Run
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@Table(name = "bill_runs")
@Inheritance(strategy = InheritanceType.JOINED)
public class BillRun implements GenericEntity<Long, BillRun> {



    /**
     * Bill Run Identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;



    /**
     * Bill Run State
     */
    @NotNull
    private State state;



    /**
     * Bill Run type - same as Invoice Type
     */
    private String type;



    /**
     *
     */
    @ManyToMany(
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "billing_account_bill_runs",
            joinColumns = { @JoinColumn(name = "bill_run_id") },
            inverseJoinColumns = { @JoinColumn(name = "billing_account_id") }
    )
    private Set<BillingAccount> billingAccounts = new HashSet<>();



    /**
     * Bill Run Start Time
     *
     * The Start Time represents the following given state
     * - NEW - Planned Start Time
     * - CANCELLED - Planned Start Time
     * - IN PROGRESS - Actual Start Time
     * - COMPLETED - Actual Start Time
     * - FAILED - Actual Start Time
     *
     */
    private ZonedDateTime start;



    /**
     * Bill Run Finish Time
     *
     * The Finish Time represent the following given state
     * - NEW - Not Applicable
     * - CANCELLED - Actual Time of Cancellation
     * - IN PROGRESS - Not Applicable
     * - COMPLETED - Actual Time of Completion
     * - FAILED - Actual Time of Failure
     */
    private ZonedDateTime finish;


    /**
     *
     */
    private ZonedDateTime periodStart;



    /**
     *
     */
    private ZonedDateTime periodEnd;

    /**
     *
     */
    private BillCycle billCycle;



    /**
     *
     */
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private Supplier supplier;



    /**
     *
     */
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "Invoce"
    )
    private Set<Invoice> invoices = new HashSet<>();



    ///**** CONSTRUCTORS ****///



    /**
     *
     * @param type
     */
    protected BillRun(String type) {
        this.type = type;
    }



    ///**** SETTERS ****///


    public BillRun start() {
        if (this.state != State.NEW)
            throw new IllegalStateException("Bill Run must be in State NEW to START");
        this.start = ZonedDateTime.now();
        this.state = State.IN_PROGRESS;
        return this;
    }


    /**
     *
     * @return
     */
    public BillRun complete() {
        if (this.state != State.IN_PROGRESS)
            throw new IllegalStateException("Bill Run must be in state IN PROGrESS to COMPLETE");
        this.finish = ZonedDateTime.now();
        this.state = State.COMPLETED;
        return this;
    }


    /**
     *
     * @return
     */
    public BillRun cancel() {
        if (this.state != State.NEW)
            throw new IllegalStateException("Bill Run must be in state NEW to CANCEL");
        this.finish = ZonedDateTime.now();
        this.state = State.CANCELLED;
        return this;
    }



    /**
     *
     * @return
     */
    public BillRun fail() {
        if (this.state != State.IN_PROGRESS)
            throw new IllegalStateException("Bill Run must be in state IN PROGRESS to FAIL");
        this.finish = ZonedDateTime.now();
        this.state = State.FAILED;
        return this;
    }


    /**
     *
     *
     *
     * @param source
     * @return
     */
    @Override
    public BillRun update(BillRun source) {
        if (this.state != State.NEW)
            throw new IllegalStateException("A bill run can only be updated in new state");
        if (this.state == State.NEW) {
            this.setBillCycle(source.getBillCycle())
                .setSupplier(source.getSupplier())
                .setBillingAccounts(source.getBillingAccounts())
                .setStart(source.start);
        }
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public BillRun createNewInstance() {
        return new BillRun(this.getType())
                .setState(State.NEW)
                .update(this);
    }


    ///**** STATIC RESOURCES ****///



    public enum State {
        NEW,
        IN_PROGRESS,
        COMPLETED,
        FAILED,
        CANCELLED
    }


    ///**** FACTORIES ****////



    /**
     *
     * @param billCycle
     * @return
     */
    public static BillRun factory(BillCycle billCycle) {
        return new BillRun(billCycle.getType())
                .setSupplier(billCycle.getSupplier())
                .setBillingAccounts(billCycle.getBillingAccounts())
                .setBillCycle(billCycle);
    }



}
