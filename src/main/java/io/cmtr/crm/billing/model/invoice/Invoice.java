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
import java.util.*;
import java.util.stream.Collectors;


/**
 * Invoice
 *
 * https://docs.peppol.eu/poacc/billing/3.0/
 *
 * @author Harald Blikø
 *
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@Table(name = "invoices")
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
    @NotNull(message = "State cannot be null")
    private State state;



    /**
     * Invoice currency
     */
    private String currency;




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
     * Mapped by the Invoice to Allowance / Charge Mapping Table in preparation
     */
    @JsonIgnore
    @Getter(AccessLevel.PROTECTED)
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "invoice_document_allowance_charges",
            joinColumns = { @JoinColumn(name = "invoice_id") },
            inverseJoinColumns = { @JoinColumn(name = "allowance_charge_id")}
    )
    private Set<AllowanceCharge> allowanceCharges = new HashSet<>();



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
    @Transient
    public List<IDocumentLevelAllowanceCharge> getAllowances() {
        return allowanceCharges
                .stream()
                .filter(e -> !e.isCharge() && e instanceof IDocumentLevelAllowanceCharge)
                .map(e -> (IDocumentLevelAllowanceCharge) e)
                .collect(Collectors.toList());
    }



    /**
     * List filtering executed on method call
     *
     * @return list of document level charges
     */
    @JsonInclude
    @Transient
    public List<IDocumentLevelAllowanceCharge> getCharges() {
        return allowanceCharges
                .stream()
                .filter(e -> e.isCharge() && e instanceof IDocumentLevelAllowanceCharge)
                .map(e -> (IDocumentLevelAllowanceCharge) e)
                .collect(Collectors.toList());
    }


    /**
     * List filtered executed on method call
     *
     * @return list of invoice line items
     */
    @Transient
    public List<IInvoiceLineItem> getLineItems() {
        return allowanceCharges
                .stream()
                .filter(e -> e instanceof IInvoiceLineItem)
                .map(e -> (InvoiceLineItem) e)
                .collect(Collectors.toList());
    }

    /**
     *
     * @return
     */
    @JsonInclude
    @Transient
    public UUID getBillingAccountId() {
        return billingAccount.getId();
    }



    /**
     * Set is collected on method call
     *
     * @return set of all unique vat categories used
     */
    @JsonInclude
    @Transient
    public Set<IVatCategory> getVatCategories() {
        return  allowanceCharges
                .stream()
                .map(this::getVatCategoryFromAllowanceCharge)
                .collect(Collectors.toSet());

    }



    /**
     *
     * TODO - Make execution nicer
     *
     * @return
     */
    @Override
    @Transient
    public List<IVatCategoryAmount> getVatCategoryAmounts() {
        final Map<IVatCategory, VatCategoryAmount> categoryMap = new HashMap<>();
        for (AllowanceCharge ac : allowanceCharges) {
            final IVatCategory vatCategory = getVatCategoryFromAllowanceCharge(ac);
            VatCategoryAmount vatCategoryAmount = categoryMap
                    .getOrDefault(vatCategory, VatCategoryAmount.factory(vatCategory, BigDecimal.ZERO));
            VatCategoryAmount newVatCategoryAmount = ac.isCharge()
                    ? vatCategoryAmount.add(ac.getAmount())
                    : vatCategoryAmount.subtract(ac.getAmount());
            categoryMap.put(vatCategory, newVatCategoryAmount);
        }
        return categoryMap
                .values()
                .stream()
                .collect(Collectors.toList());
    }



    /**
     *
     * @return BigDecimal - Net Amount
     */
    @Override
    public BigDecimal getAmount() {
        return getTotalNetAmount();
    }



    ///**** SETTERS ****///



    /**
     *
     * @param lineItem
     * @return
     */
    public Invoice addInvoiceLineItem(@NotNull InvoiceLineItem lineItem) {
        setStateToPrepareIfNew();
        inPrepareOrThrow("Invoice lines can only be added in state NEW or IN PROGRESS");
        validateLineItem(lineItem);
        allowanceCharges.add(lineItem);
        return this;
    }

    /**
     *
     * @param lineItem
     * @return
     */
    public Invoice removeInvoiceLineItem(InvoiceLineItem lineItem) {
        inPrepareOrThrow("Invoice lines can only be removed in state IN PROGRESS");
        validateAllowanceCharge(lineItem);
        allowanceCharges.remove(lineItem);
        return this;
    }



    /**
     *
     * @param allowanceCharge
     * @return
     */
    public Invoice addDocumentLevelAllowanceCharge(@NotNull InvoiceDocumentLevelAllowanceCharge allowanceCharge) {
        setStateToPrepareIfNew();
        inPrepareOrThrow("Document Level Allowances and Charges can only be changed when state is IN PROGRESS");
        validateAllowanceCharge(allowanceCharge);
        allowanceCharges.add((AllowanceCharge) allowanceCharge);
        return this;
    }



    /**
     *
     * @param allowanceCharge
     * @return
     */
    public Invoice removeDocumentLevelAllowanceCharge(InvoiceDocumentLevelAllowanceCharge allowanceCharge) {
        inPrepareOrThrow("Document Level Allowances and Charges can only be changed when state is IN PROGRESS");
        allowanceCharges.remove(allowanceCharge);
        return this;
    }



    /**
     *
     * @return
     */
    public Invoice complete() {
        inPrepareOrThrow("A invoice can only be set to COMPLETED when in state IN PROGRESS");
        this.state = State.COMPLETE;
        finalizeEntity();
        this.allowanceCharges.forEach(ac -> ac.complete(this));
        return this;
    }

    /**
     *
     * @return
     */
    public Invoice simulate() {
        inPrepareOrThrow("A invoice can only be set to SIMULATE when in state IN PROGRESS");
        this.state = State.SIMULATED;
        finalizeEntity();
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
        inNewOrThrow("A invoice can only be updated in NEW state");
        return this
            .setSupplier(source.supplier)
            .setBillingAccount(source.billingAccount);
    }



    /**
     *
     * @return
     */
    @Override
    public Invoice createNewInstance() {
        return new Invoice()
                .setState(State.NEW)
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


    public static Invoice factory(Long id) {
        return new Invoice()
                .setId(id);
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
     *  Set states to PREPARING if NEW
     */
    private void setStateToPrepareIfNew() {
        if (this.state == State.NEW)
            this.setState(State.PREPARE);
    }



    /**
     * Validate state or throw exception
     *
     * @param message - thrown with IllegalSateException
     */
    private void inPrepareOrThrow(String message) {
        if (this.state != State.PREPARE)
            throw new IllegalStateException(message);
    }



    /**
     *
     * @param message
     */
    private void inNewOrThrow(String message) {
        if (this.state != State.NEW)
            throw new IllegalStateException(message);
    }



    /**
     * Validates that the state and allocation of the allowance charge meet requirements
     * for being added to a invoice
     *
     * @param allowanceCharge - allowance charge to be added to invoice
     */
    protected void validateAllowanceCharge(AllowanceCharge allowanceCharge) {
        val state = allowanceCharge.getState();
        if (
                    allowanceCharge.getInvoice() != null
                ||  state == AllowanceCharge.State.COMPLETE
                ||  state == AllowanceCharge.State.DELETED
        )
            throw new UnsupportedOperationException("A completed or deleted allowance charge cannot be added to a invoice");

        if (isMatchBetweenSupplierAndBillingAccount(allowanceCharge))
            throw new IllegalArgumentException("Allowance charge Billing Account or Supplier does not match invoice");
    }



    /**
     *
     * @param allowanceCharge
     * @return
     */
    private boolean isMatchBetweenSupplierAndBillingAccount(AllowanceCharge allowanceCharge) {
        return !allowanceCharge.getSupplier().equals(this.supplier) || !allowanceCharge.getBillingAccount().equals(this.billingAccount);
    }



    /**
     *
     * @param lineItem
     */
    private void validateLineItem(InvoiceLineItem lineItem) {
        validateAllowanceCharge(lineItem);
    }



    /**
     *
     * Copy and persists the issuer, owner and recipient contact information at time of finalization.
     * Sets the issue date of finalization.
     *
     */
    private void finalizeEntity() {
        this.issuer = this.supplier.getCustomer().createNewInstance();
        this.owner = this.billingAccount.getOwner().createNewInstance();
        this.recipient = this.billingAccount.getRecipient().createNewInstance();
        this.issueDate = ZonedDateTime.now();
    }



    /**
     *
     * @param e
     * @return
     */
    private IVatCategory getVatCategoryFromAllowanceCharge(AllowanceCharge e) {
        if (e instanceof IVatCategory) return (IVatCategory) e;
        if (e instanceof IDocumentLevelAllowanceCharge) return ((InvoiceDocumentLevelAllowanceCharge) e).getVatCategory();
        if (e instanceof IInvoiceLineItem) return ((IInvoiceLineItem) e).getVatCategory();
        throw new UnsupportedOperationException("Illegal allowance charge type stored as invoice allowance charge");
    }

}
