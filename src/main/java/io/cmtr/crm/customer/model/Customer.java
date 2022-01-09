package io.cmtr.crm.customer.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.cmtr.crm.customer.dto.CustomerDeserializer;
import io.cmtr.crm.customer.event.IllegalCustomerStateException;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Customer
 *
 * Purchase products or services from Suppliers.
 *
 * Customer is a Global Entity.
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Entity
@Accessors(chain = true)
@Table(name = "customers")
@JsonDeserialize(using = CustomerDeserializer.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer implements GenericEntity<UUID, Customer> {



    /**
     *
     */
    @Id
    private UUID id;



    /**
     *
     */
    // Should be fixed at entity instantiation time
    @Column(
            name = "customer_type",
            updatable = false,
            insertable = true,
            nullable = false
    )
    @NotNull(message = "Customer type cannot be null")
    private String type;



    /**
     *
     */
    @NotNull(message = "Customer state cannot be null")
    private State state;



    /**
     *
     */
    private boolean barred;



    /**
     *
     */
    @OneToMany(
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<BillingAccount> billingAccounts = new HashSet<>();



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
    @NotNull(message = "Customer details cannot be null")
    private AbstractContact customer;



    /**
     *
     */
    @ElementCollection
    @CollectionTable(
            name = "customer_parameters",
            joinColumns = { @JoinColumn(name = "customer_parameter_id", referencedColumnName = "id")}
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



    ///**** CONSTRUCTORS ****///



    /**
     *
     * @param type
     */
    protected Customer(@NotNull(message = "Customer type cannot be null") String type) {
        this.type = type;
    }



    ///**** GETTERS ****///



    /**
     *
     * @return
     */
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public List<UUID> getBillingAccountIds() {
        return billingAccounts
                .stream()
                .map(BillingAccount::getId)
                .collect(Collectors.toList());
    }



    /**
     *
     * @return
     */
    @JsonInclude
    public String getEmail() {
        return this.customer.getEmail();
    }



    ///**** SETTERS ****∕∕∕


    /**
     *
     * @param state
     * @return
     */
    public Customer setState(State state) {
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
            throw new IllegalCustomerStateException(
                    String.format("Cannot transition from state %s to %s.", this.state, state),
                    this
            );
        }
    }


    /**
     *
     * @param barred
     * @return
     */
    public Customer setBarred(boolean barred) {
        ifClosedThrow("Cannot bar a Closed Customer");
        this.barred = barred;
        return this;
    }



    /**
     *
     * @param source
     * @return
     */
    @Override
    public Customer update(Customer source) {
        if (this.state == State.CLOSED)
            throw new IllegalCustomerStateException("Cannot bar a Closed Customer", this);
        this.customer.update(source.customer);
        return this
                .setParameters(source.parameters);
    }



    /**
     *
     * @return
     */
    @Override
    public Customer createNewInstance() {
        return this
                .setId(UUID.randomUUID())
                .setState(State.NEW)
                .setBarred(false)
                .setType(this.type)
                .setCustomer(this.getCustomer() == null ? null : this.getCustomer().createNewInstance())
                .update(this);
    }



    ///**** STATIC RESOURCES ****∕∕∕



    /**
     *
     */
    public enum State {
        NEW,
        ACTIVE,
        INACTIVE,
        CLOSED
    }



    ///**** FACTORIES ****∕∕∕



    /**
     *
     * @param type
     * @param parameters
     * @param customer
     * @param email
     * @return
     */
    public static Customer factory(
            String type,
            Map<String, String> parameters,
            AbstractContact customer,
            String email // TODO Remove
    ) {
        return new Customer()
            .setType(type)
            .setParameters(parameters)
            .setCustomer(customer);
    }



    /**
     *
     * @param id
     * @return
     */
    public static Customer factory(UUID id) {
        return new Customer().setId(id);
    }



    ///**** HELPER METHODS ****///



    /**
     *
     * @param message
     */
    private void ifClosedThrow(String message) {
        if (this.state == State.CLOSED)
            throw new IllegalCustomerStateException(message, this);
    }


}
