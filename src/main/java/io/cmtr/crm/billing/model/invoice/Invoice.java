package io.cmtr.crm.billing.model.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;
import io.cmtr.crm.shared.billing.model.*;
import io.cmtr.crm.shared.contact.model.AbstractContact;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Invoice
 *
 *
 * https://docs.peppol.eu/poacc/billing/3.0/
 *
 * @author Harald Blikø
 * @
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invoice implements GenericEntity<Long, Invoice>, IInvoice {



    /**
     *
     */
    @Id
    @GeneratedValue
    private Long id;



    /**
     *
     */
    @NotNull
    private State state;



    /**
     * Invoice Issuer defined as the Supplier Entity
     *
     */
    @NotNull(message = "A invoice must have have defined a Supplier")
    private Supplier supplier;



    /**
     * Invoice Issuer as defined by the supplier contact information on issuing time
     *
     * Value object stored as a entity.
     * Should maintain a single one-to-one relationship throughout the entity life cycle
     */
    @OneToOne(
            cascade = CascadeType.ALL,
            optional = true,
            fetch = FetchType.EAGER
    )
    private AbstractContact issuer;



    /**
     * Billing Recipient as defined by the Customer Billing Account Entity
     *
     *
     */
    @ManyToOne(
            fetch = FetchType.LAZY,
            targetEntity = BillingAccount.class,
            optional = false
    )
    @JsonIgnore
    private BillingAccount billingAccount;



    /**
     * The legal billing recipient as defiend by the Billing Account Onwer at time of invoice issue.
     *
     * Value object stored as a entity.
     * Should maintain a single one-to-one relationship throughout the entity life cycle
     */
    @OneToOne(
            cascade = CascadeType.ALL,
            optional = false,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private AbstractContact owner;



    /**
     * Invoice Bill Recipient as defined by the Billing Account Recipient at time of invoice issuer.
     *
     * The person recieving the invoice. Not necessarily the same as the legal customer.
     *
     * Value object stored as a entity.
     * Should maintain a single one-to-one relationship throughout the entity life cycle
     */
    @OneToOne(
            cascade = CascadeType.ALL,
            optional = false,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private AbstractContact recipient;



    /**
     *
     *
     * Mapped by the Invoice to Allowance / Charge Mapping Table in preparation 
     * 
     */
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    private List<InvoiceLineItem> lineItems;



    /**
     *
     * Mapped by the Invoice to Allowance / Charge Mapping Table in preparation
     */
    @JsonIgnore
    private List<DocumentLevelAllowanceCharge> allowanceCharges;



    /**
     *
     */
    private ZonedDateTime issueDate;

    ///**** GETTERS ****///


    /**
     * List filtering executed on method call
     *
     * @return list of document level allowances
     */
    @JsonInclude
    public List<IDocumentLevelAllowanceCharge> getAllowances() {
        return allowanceCharges
                .stream()
                .filter(e -> !e.isCharge())
                .collect(Collectors.toList());
    }



    /**
     * List filtering executed on method call
     *
     * @return list of document level charges
     */
    @JsonInclude
    public List<IDocumentLevelAllowanceCharge> getCharges() {
        return allowanceCharges
                .stream()
                .filter(DocumentLevelAllowanceCharge::isCharge)
                .collect(Collectors.toList());
    }



    /**
     *
     * @return
     */
    @JsonInclude
    public UUID getBillingAccountId() {
        return billingAccount.getId();
    }



    /**
     * Set is collected on method call
     *
     * @return set of all unique vat categories used
     */
    @JsonInclude
    public Set<IVatCategory> getVatCategories() {
        Set<IVatCategory> vatCategories = allowanceCharges
                .stream()
                .map(DocumentLevelAllowanceCharge::getVatCategory)
                .collect(Collectors.toSet());
        vatCategories.addAll(lineItems
                .stream()
                .map(IInvoiceLineItem::getVatCategory)
                .collect(Collectors.toSet()));
        return vatCategories;

    }



    /**
     *
     * @return
     */
    @Override
    public List<IVatCategoryAmount> getVatCategoryAmounts() {
        // TODO - when VatCategoryAmount is defined
        return null;
    }



    /**
     *
     * @return BigDecimal - Net Amount
     */
    @Override
    public BigDecimal getAmount() {
        // Todo
        return null;
    }



    /**
     *
     * @return
     */
    @Override
    public String getCurrency() {
        // TODO
        return null;
    }

    public List<IInvoiceLineItem> getLineItems() {
        return getLineItems();
    }



    ///**** SETTERS ****///



    /**
     *
     * @param lineItem
     * @return
     */
    public Invoice addInvoiceLineItem(@NotNull InvoiceLineItem lineItem) {
        setStateToInProgressIfNew();
        inPrepareOrThrow("Invoice lines can only be added in state NEW or IN PROGRESS");
        lineItems.add(lineItem);
        return this;
    }



    /**
     *
     * @param lineItem
     * @return
     */
    public Invoice removeInvoiceLineItem(InvoiceLineItem lineItem) {
        inPrepareOrThrow("Invoice lines can only be removed in state IN PROGRESS");
        lineItems.remove(lineItem);
        return this;
    }



    /**
     *
     * @param allowanceCharge
     * @return
     */
    public Invoice addDocumentLevelAllowanceCharge(DocumentLevelAllowanceCharge allowanceCharge) {
        setStateToInProgressIfNew();
        inPrepareOrThrow("Document Level Allowances and Charges can only be changed when state is IN PROGRESS");
        // TODO
        return this;
    }



    /**
     *
     * @param allowanceCharge
     * @return
     */
    public Invoice removeDocumentLevelAllowanceCharge(DocumentLevelAllowanceCharge allowanceCharge) {
        inPrepareOrThrow("Document Level Allowances and Charges can only be changed when state is IN PROGRESS");
        // TODO
        return this;
    }



    /**
     *
     * @return
     */
    public Invoice complete() {
        inPrepareOrThrow("A invoice can only be set to COMPLETE when in state IN PROGRESS");
        this.state = State.COMPLETE;
        createContacts();
        this.issueDate = ZonedDateTime.now();
        this.allowanceCharges.forEach(ac -> ac.complete(this));
        this.lineItems.forEach(ac -> ac.complete(this));
        return this;
    }

    /**
     *
     * @return
     */
    public Invoice simulate() {
        inPrepareOrThrow("A invoice can only be set to SIMULATE when in state IN PROGRESS");
        this.state = State.SIMULATED;
        createContacts();
        // Do not set the invoice property of line items or allowances
        return this;
    }


    /**
     *
     * @param source
     * @return
     */
    @Override
    public Invoice update(Invoice source) {
        return null;
    }



    /**
     *
     * @return
     */
    @Override
    public Invoice createNewInstance() {
        return new Invoice()
                .setState(State.NEW)
                .setSupplier(this.supplier)
                .setBillingAccount(this.billingAccount)
                .update(this);
    }


    ///**** FACTORIES ****∕∕∕


    /**
     *
     * @param supplier
     * @param billingAccount
     * @return Invoice Dto Instance
     */
    public static Invoice factory(Supplier supplier, BillingAccount billingAccount) {
        return new Invoice()
                .setSupplier(supplier)
                .setBillingAccount(billingAccount);
    }



    ///**** STATIC RESOURCES ****∕∕∕



    /**
     *
     */
    public enum State {
        NEW,
        PREPARE,
        COMPLETE,
        CANCELLED,
        SIMULATED
    }



    ///**** HELPER METHODS ****∕∕∕



    /**
     *
     */
    private void setStateToInProgressIfNew() {
        if (this.state == State.NEW)
            this.state = State.PREPARE;
    }



    /**
     *
     * @param message
     */
    private void inPrepareOrThrow(String message) {
        if (this.state != State.PREPARE)
            throw new IllegalStateException(message);
    }



    /**
     *
     */
    private void createContacts() {
        this.issuer = this.supplier.getCustomer().createNewInstance();
        this.owner = this.billingAccount.getOwner().createNewInstance();
        this.recipient = this.billingAccount.getRecipient().createNewInstance();
    }

}
