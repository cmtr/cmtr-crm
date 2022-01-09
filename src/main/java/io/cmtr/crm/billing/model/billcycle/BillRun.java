package io.cmtr.crm.billing.model.billcycle;

import io.cmtr.crm.billing.model.invoice.AllowanceCharge;
import io.cmtr.crm.billing.model.invoice.Invoice;
import io.cmtr.crm.billing.model.invoice.InvoiceDocumentLevelAllowanceCharge;
import io.cmtr.crm.billing.model.invoice.InvoiceLineItem;
import io.cmtr.crm.billing.service.IAllowanceChargeService;
import io.cmtr.crm.billing.service.IInvoiceService;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@Table(name = "bill_runs")
public class BillRun implements GenericEntity<Long, BillRun> {



    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;



    /**
     *
     */
    @NotNull
    private State state;



    /**
     *
     */
    private String type;



    /**
     *
     */
    @ManyToOne(
            optional = true,
            targetEntity = BillCycle.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "bill_cycle_id")
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
     *
     */
    @ManyToMany(
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "billing_run_invoices",
            joinColumns = { @JoinColumn(name = "bill_run_id") },
            inverseJoinColumns = { @JoinColumn(name = "invoice_id") }
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


    /**
     *
     * @param allowanceChargeProducer
     * @param invoiceService
     * @return
     */
    public BillRun start(
            BiFunction<Supplier, BillingAccount, Collection<AllowanceCharge>> allowanceChargeProducer,
            IInvoiceService invoiceService
    ) {
        Function<BillingAccount, Invoice> createInvoice = billingAccount -> Invoice
            .factory(this.supplier, billingAccount)
            .createNewInstance();
        UnaryOperator<Invoice> addChargesAllowancesAndLineItems = invoice -> {
            allowanceChargeProducer
                    .apply(invoice.getSupplier(), invoice.getBillingAccount())
                    .forEach(this.addToInvoice(invoice));
            return invoice;
        };
        UnaryOperator<Invoice> collectId = invoice -> Invoice.factory(invoice.getId());
        Function<BillingAccount, Invoice> generateInvoice = createInvoice
            .andThen(addChargesAllowancesAndLineItems)
            .andThen(invoiceService::save)
            .andThen(collectId);

        try {
            this.invoices = this.billingAccounts
                    .parallelStream()
                    .map(generateInvoice)
                    .collect(Collectors.toSet());
            this.state = State.IN_PROGRESS;
        // TODO - Update Exception Handling
        } catch (RuntimeException ex) {
            this.state = State.FAILED;
        } finally {
            return this;
        }

    }



    /**
     *
     * @param invoiceService
     * @return
     */
    public BillRun complete(IInvoiceService invoiceService) {
        if (this.state != State.IN_PROGRESS)
            throw new IllegalStateException("A bill run must be in progress to be completed");

        UnaryOperator<Invoice> getInvoice = invoice -> invoiceService.get(invoice.getId());
        UnaryOperator<Invoice> collectId = invoice -> Invoice.factory(invoice.getId());
        Function<Invoice, Invoice> completeInvoices = getInvoice
                .andThen(Invoice::complete)
                .andThen(invoiceService::save)
                .andThen(collectId);

        try {
            this.invoices = this.invoices
                    .parallelStream()
                    .map(completeInvoices)
                    .collect(Collectors.toSet());
            this.state = State.COMPLETED;
            // TODO -Update Exception Handling
        } catch (RuntimeException e) {
            this.state = State.FAILED;
        } finally {
            return this;
        }
    }



    /**
     *
     * @param invoiceService
     * @return
     */
    public BillRun cancel(IInvoiceService invoiceService) {
        if (this.state == State.COMPLETED || this.state == State.FAILED)
            throw new IllegalStateException("Cannot cancel a completed or failed bill run");
        if (this.state == State.IN_PROGRESS) {
            // TODO - Cancel All invoices
        }
        this.state = State.CANCELLED;
        return this;
    }


    public BillRun simulate() {
        // TODO - Apply Logic
        this.state = State.SIMULATED;
        return this;
    }



    /**
     *
     * @param source
     * @return
     */
    @Override
    public BillRun update(BillRun source) {
        if (this.state != State.NEW)
            throw new IllegalStateException("A bill run can only be updated in new state");
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public BillRun createNewInstance() {
        return new BillRun(this.type)
                .setState(State.NEW)
                .update(this);
    }



    ///**** STATIC RESOURCES ****///



    public enum State {
        NEW,
        IN_PROGRESS,
        COMPLETED,
        SIMULATED,
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
                .setBillingAccounts(billCycle.getBillingAccounts());
    }

    // TODO - Factory Method for Interim and Ad-Hoc invoices

    // TODO - Factory Method for Credit Notes

    ///**** HELPER METHODS ****///



    /**
     *
     * @param allowanceChargeService
     * @return
     */
    private UnaryOperator<Invoice> addLineItemsAndCharges(
            IAllowanceChargeService allowanceChargeService
    ) {
        return (invoice) -> {
            allowanceChargeService
                    .getAllowanceChage(invoice.getSupplier(), invoice.getBillingAccount(), false)
                    .forEach(this.addToInvoice(invoice));
            return invoice;
        };
    }



    /**
     *
     * @param invoice
     * @return
     */
    private Consumer<AllowanceCharge> addToInvoice(Invoice invoice) {
        return ac -> {
            if (ac instanceof InvoiceLineItem)
                invoice.addInvoiceLineItem((InvoiceLineItem) ac);
            else if (ac instanceof InvoiceDocumentLevelAllowanceCharge)
                invoice.addDocumentLevelAllowanceCharge((InvoiceDocumentLevelAllowanceCharge) ac);
        };
    }


}
