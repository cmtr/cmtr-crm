package io.cmtr.crm.billing.model.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;
import io.cmtr.crm.shared.billing.model.IInvoiceLineItem;
import io.cmtr.crm.shared.billing.model.IInvoiceLineItemAllowanceCharge;
import io.cmtr.crm.shared.billing.model.IUnitPrice;
import io.cmtr.crm.shared.mediation.model.IQuantity;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@DiscriminatorValue(InvoiceLineItem.DISCRIMINATOR_VALUE)
public class InvoiceLineItem extends AllowanceCharge implements IInvoiceLineItem {

    /**
     *
     */
    public static final String DISCRIMINATOR_VALUE = "LINE_ITEM";



    /**
     *
     */
    private BigDecimal net;




    /**
     *
     */
    @Delegate
    @Getter(AccessLevel.PROTECTED)
    @JsonIgnore
    private UnitPrice price;



    /**
     *
     */
    private BigDecimal quantity;



    /**
     *
     */
    @ManyToOne(
            optional = false,
            targetEntity = VatCategory.class,
            fetch = FetchType.EAGER
    )
    private VatCategory vatCategory;



    /**
     *
     */
    @JsonIgnore
    @Getter(AccessLevel.PROTECTED)
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "line_item_allowance_charges",
            joinColumns = { @JoinColumn(name = "invoice_line_item_id") },
            inverseJoinColumns = { @JoinColumn(name = "allowance_charge_id")}
    )
    private Set<InvoiceLineItemAllowanceCharge> allowanceCharges = new HashSet<>();



    ///**** CONSTRUCTOR ****∕∕∕



    /**
     *
     */
    protected InvoiceLineItem() {
        super(DISCRIMINATOR_VALUE);
    }



    ///**** GETTERS ****∕∕∕



    /**
     *
     * @return
     */
    @Override
    public boolean isCharge() {
        // TODO - Apply logic
        return true;
    }



    /**
     *
     * @return
     */
    @Transient
    public List<IInvoiceLineItemAllowanceCharge> getAllowances() {
        return allowanceCharges
                .stream()
                .filter(Predicate.not(IInvoiceLineItemAllowanceCharge::isCharge))
                .collect(Collectors.toList());
    }



    /**
     *
     * @return
     */
    @Transient
    public List<IInvoiceLineItemAllowanceCharge> getCharges() {
        return allowanceCharges
                .stream()
                .filter(IInvoiceLineItemAllowanceCharge::isCharge)
                .collect(Collectors.toList());
    }



    ///**** SETTERS ****///


    /**
     *
     * @param allowanceCharge
     * @return
     */
    public InvoiceLineItem addAllowanceCharge(@NotNull InvoiceLineItemAllowanceCharge allowanceCharge) {
        validateAllowanceCharge(allowanceCharge);
        setStateToPrepareIfNew();
        allowanceCharges.add(allowanceCharge);
        return this;
    }



    /**
     *
     * @param allowanceCharge
     * @return
     */
    public InvoiceLineItem removeAllowanceCharge(InvoiceLineItemAllowanceCharge allowanceCharge) {
        allowanceCharges.remove(allowanceCharge);
        return this;
    }



    /**
     *
     * @param invoice
     * @return
     */
    @Override
    protected InvoiceLineItem complete(Invoice invoice) {
        super.complete(invoice);
        this.allowanceCharges.forEach(ac -> ac.complete(invoice));
        return this;
    }



    /**
     *
     * @param source
     * @return
     */
    @Override
    public InvoiceLineItem update(AllowanceCharge source) {
        // TODO - Sett all line items to new supplier or billing account
        super.update(source);
        // if (source instanceof InvoiceLineItem invoiceLineItem)
        if (source instanceof InvoiceLineItem) {
            InvoiceLineItem invoiceLineItem = (InvoiceLineItem) source;
            // TODO - validation
            this.setVatCategory(((InvoiceLineItem) source).getVatCategory());
            this.setPrice(UnitPrice.factory(invoiceLineItem));
            this.setQuantity(invoiceLineItem.getQuantity());
        }
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public InvoiceLineItem createNewInstance() {
        InvoiceLineItem invoiceLineItem = new InvoiceLineItem();
        invoiceLineItem
                .setState(State.NEW);
        return invoiceLineItem.update(this);
    }



    ///**** FACTORIES ****///



    public static InvoiceLineItem factory(
            Supplier supplier, BillingAccount billingAccount, VatCategory vatCategory, IUnitPrice unitPrice, IQuantity quantity
    ) {
        InvoiceLineItem lineItem = new InvoiceLineItem()
                .setVatCategory(vatCategory)
                .setPrice(UnitPrice.factory(unitPrice))
                .setQuantity(quantity.getQuantity());
        lineItem
                .setSupplier(supplier)
                .setBillingAccount(billingAccount);
        return lineItem;
    }



    ///**** HELPER METHODS ****∕∕∕



    /**
     *
     * @param lineItemAllowanceCharge
     */
    private void validateAllowanceCharge(InvoiceLineItemAllowanceCharge lineItemAllowanceCharge) {
        if (!lineItemAllowanceCharge.getSupplier().equals(this.getSupplier()) || !lineItemAllowanceCharge.getBillingAccount().equals(this.getBillingAccount()))
            throw new IllegalArgumentException("Allowance charge Billing account or Supplier does not match invoice");
    }


}
