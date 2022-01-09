package io.cmtr.crm.billing.model.billcycle;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Customer;
import io.cmtr.crm.customer.model.Supplier;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@Table(name = "bill_cycles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "bill_cycle_type")
public abstract class BillCycle implements GenericEntity<UUID, BillCycle> {



    /**
     *
     */
    @Id
    private UUID id;



    /**
     *
     */
    @NotEmpty
    @Column(name = "bill_cycle_type")
    private String type;



    /**
     *
     */
    @NotEmpty
    @Column(
            unique = true
    )
    private String name;



    /**
     * Prevented for adding more billing accounts
     */
    private boolean locked;


    /**
     * Date and time for when the next bill run should include allowances and charges
     */
    private ZonedDateTime periodEnd;



    /**
     *
     */
    @OneToMany(
            mappedBy = "billCycle"
    )
    private List<BillRun> billRuns;



    /**
     * A bill cycle belongs to a single supplier, but a supplier can have multiple bill cycles
     * The bill cycle supplier cannot be updated
     */
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    private Supplier supplier;



    /**
     *
     * Option 1 - One billing account to one bill cycle -> relationship controlled by the billing account
     * Pros:
     * - logically cleaner as a billing account get a more predictable
     * Cons:
     * - a billing account may not belong to any bill cycles
     * - assuring every billing account belongs to a bill cycle requires handling outside
     *
     * Option 2 - One billing account to many bill cycles -> relationship controlled by the bill cycles
     * Pros:
     * - easier implementation
     * - the lock function is better
     * - in principle there is nothing preventing someone to belong to multiple bill cycles
     * - easier to (unit) test
     * - less coupling
     * Cons:
     * - requires outside handling
     *
     * Conclusion:
     * - Option 2 - One Billing Account to many Bill Cycles
     *
     */
    @ManyToMany(
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "billing_account_bill_cycles",
            joinColumns = { @JoinColumn(name = "bill_cycle_id") },
            inverseJoinColumns = { @JoinColumn(name = "billing_account_id") }
    )
    private Set<BillingAccount> billingAccounts = new HashSet<>();



    /**
     *
     */
    @ElementCollection
    @CollectionTable(
            name = "bill_cycle_parameters",
            joinColumns = { @JoinColumn(name = "bill_cycle_id", referencedColumnName = "id")}
    )
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> parameters;



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



    ///**** CONSTRUCTOR ****///



    protected BillCycle(@NotEmpty String type) {
        this.type = type;
    }


    ///**** GETTERS ****///



    /**
     * The bill cycle state is linked to the supplier (customer) state.
     *
     * @return bill cycle state
     */
    public Customer.@NotNull(message = "Customer state cannot be null") State getState() {
        return getSupplier().getState();
    }



    /**
     *
     * Indicates if the current billing period is finished
     * I.e. if a bill run could be initiated
     *
     * @return
     */
    @Transient
    public boolean isPeriodEndBeforeNow() {
        return periodEnd.isBefore(ZonedDateTime.now());
    }



    ///**** SETTERS ****∕∕∕



    /**
     *
     * @param billingAccount
     * @return
     */
    public BillCycle addBillingAccount(BillingAccount billingAccount) {
        if (isLocked())
            throw new IllegalStateException("Cannot add billing accounts to a locked bill cycle");
        this.billingAccounts.add(billingAccount);
        return this;
    }



    /**
     *
     * @param billingAccount
     * @return
     */
    public BillCycle removeBillingAccount(BillingAccount billingAccount) {
        this.billingAccounts.remove(billingAccount);
        return this;
    }

    /**
     *
     * @param source
     * @return
     */
    @Override
    public BillCycle update(BillCycle source) {
        return this
                .setLocked(source.isLocked())
                .setName(source.name)
                .setParameters(source.getParameters());
    }



    /**
     *
     * @return
     */
    @Override
    public BillCycle createNewInstance() {
        return this.setId(UUID.randomUUID())
            .setLocked(false);
    }


    ///**** STATIC RESOURCES ****///


    ///**** FACTORIES ****∕∕∕


}
