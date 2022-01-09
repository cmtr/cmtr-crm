package io.cmtr.crm.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.cmtr.crm.customer.dto.BillingAccountDeserializer;
import io.cmtr.crm.customer.dto.ICustomerToBillingAccountMapper;
import io.cmtr.crm.customer.event.IllegalBillingAccountStateException;
import io.cmtr.crm.shared.contact.model.AbstractContact;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;



/**
 * Billing Account
 *
 *
 * @author Harald Blikø
 *
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@Accessors(chain = true)
@Entity
@Table(name = "billing_accounts")
@JsonDeserialize(using = BillingAccountDeserializer.class)
@NoArgsConstructor
public class BillingAccount implements GenericEntity<UUID, BillingAccount> {



    /**
     *
     */
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;



    /**
     *
     */
    @NotNull
    private State state;



    /**
     *
     */
    // Should be fixed at entity instantiation time
    @NotNull(message = "Account type cannot be null")
    private String type;



    /**
     *
     */
    @Setter(AccessLevel.PUBLIC)
    private boolean barred;



    /**
     *
     */
    // Should maintain the same relationship throughout the entity life cycle
    @ManyToOne(
            optional = false,
            targetEntity = Customer.class,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private Customer customer;



    /**
     *
     */
    // Value object stored as a entity.
    // Should maintain a single one-to-one relationship throughout the entity life cycle
    @OneToOne(
            cascade = CascadeType.ALL,
            optional = false,
            orphanRemoval = true
    )
    @NotNull(message = "Account owner cannot be null")
    private AbstractContact owner;



    /**
     *
     */
    // Value object stored as a entity.
    // Should maintain a single one-to-one relationship throughout the entity life cycle
    @OneToOne(
            cascade = CascadeType.ALL,
            optional = false,
            orphanRemoval = true
    )
    @NotNull(message = "Account recipient cannot be null")
    private AbstractContact recipient;



    /**
     * https://www.baeldung.com/hibernate-persisting-maps
     */
    @ElementCollection
    @CollectionTable(
            name = "billing_account_parameters",
            joinColumns = { @JoinColumn(name = "billing_account_id", referencedColumnName = "id")}
    )
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> parameters;



    /**
     * Indicates that Billing Account owner should be synced with Customer details
     */
    //
    private boolean syncOwnerWithCustomer = false;



    /**
     * Indicates that Billing Account recipient should be synced with Billing Account owner
     */
    private boolean syncRecipientWithOwner = false;



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



    ///**** GETTERS ****∕∕∕



    /**
     *
     * @return
     */
    public UUID getCustomerId() {
        return getCustomer().getId();
    }



    ///**** SETTERS ****///



    /**
     *
     * @param state
     * @return
     */
    public BillingAccount setState(State state) {
        if (state == null) throw new NullPointerException("Customer state cannot be null.");
        if (this.state == null) this.state = state;
        if (this.state == state) return this;

        if (
                (this.state == State.NEW && state == State.ACTIVE) ||
                        (this.state == State.ACTIVE && state == State.INACTIVE) ||
                        (this.state == State.INACTIVE && state == State.ACTIVE) ||
                        (this.state == State.INACTIVE && state == State.CLOSED)
        ) {
            this.state = state;
            return this;
        } else {
            String message = String.format("Cannot transition from state %s to %s.", this.state, state);
            throw new IllegalBillingAccountStateException(message, this);
        }
    }



    /**
     *
     * @param source
     * @return
     */
    @Override
    public BillingAccount update(BillingAccount source) {
        this.setSyncOwnerWithCustomer(source.syncOwnerWithCustomer);
        this.setSyncRecipientWithOwner(source.syncRecipientWithOwner);
        this.owner.update(syncOwnerWithCustomer ? this.customer.getCustomer() : source.getOwner());
        this.recipient.update(syncRecipientWithOwner ? this.getOwner() : source.getRecipient());
        return this
                .setParameters(source.getParameters());
    }



    /**
     * Use existing instance as a builder to generate a new Account entity.
     * For existing entities it will provide a clone
     *
     * @return New Account entity
     */
    @Override
    public BillingAccount createNewInstance() {
        return new BillingAccount()
                .setId(UUID.randomUUID())
                .setState(State.NEW)
                .setType(this.type)
                .setBarred(false)
                .setCustomer(this.customer)
                .setOwner(this.getOwner() == null ? null : this.getOwner().createNewInstance())
                .setRecipient(this.getRecipient() == null ? null : this.getRecipient().createNewInstance())
                .update(this);
    }



    ///**** STATIC RESOURCES ****///



    /**
     *
     */
    public enum State {
        NEW,
        ACTIVE,
        INACTIVE,
        CLOSED
    }


    ///**** FACTORIES ****///



    /**
     *
     * @param type
     * @param customer
     * @param owner
     * @param recipient
     * @param parameters
     * @return
     */
    public static BillingAccount factory(
            String type,
            Customer customer,
            AbstractContact owner,
            AbstractContact recipient,
            Map<String, String> parameters
    ) {
        return new BillingAccount()
                .setType(type)
                .setCustomer(customer)
                .setOwner(owner)
                .setRecipient(recipient)
                .setParameters(parameters);
    }


    /**
     *
     * @param customer
     * @param billingAccountType
     * @return
     */
    public static BillingAccount factory(
            Customer customer,
            String billingAccountType
    ) {
        return new BillingAccount()
                .setType(billingAccountType)
                .setCustomer(customer)
                .setOwner(customer.getCustomer())
                .setRecipient(customer.getCustomer());
    }

}
