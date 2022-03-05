package io.cmtr.crm.billing.event;

import io.cmtr.crm.billing.model.billcycle.BillRun;
import io.cmtr.crm.billing.model.invoice.Invoice;
import io.cmtr.crm.billing.service.BillRunService;
import io.cmtr.crm.billing.service.InvoiceFinalizationStrategy;
import io.cmtr.crm.billing.service.InvoiceInstantiationStrategy;
import io.cmtr.crm.billing.service.InvoiceProcessingStrategy;
import io.cmtr.crm.shared.generic.event.GenericCrudEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * BillRun Event Handler
 *
 * TODO - Valdiate if Singleton, or else make it to a Singleton Orchestrator
 *
 * @author Harald Blikø
 */
@Component
public class BillRunEventHandler {



    /**
     *
     */
    private final BillRunService billRunService;



    /**
     *
     */
    private final InvoiceInstantiationStrategy invoiceInstantiationStrategy;



    /**
     *
     */
    private final InvoiceProcessingStrategy invoiceProcessingStrategy;



    /**
     *
     */
    private final InvoiceFinalizationStrategy invoiceFinalizationStrategy;



    /**
     *
     * @param billRunService
     * @param invoiceInstantiationStrategy
     * @param invoiceProcessingStrategy
     * @param invoiceFinalizationStrategy
     */
    public BillRunEventHandler(
            @Autowired BillRunService billRunService,
            @Autowired InvoiceInstantiationStrategy invoiceInstantiationStrategy,
            @Autowired InvoiceProcessingStrategy invoiceProcessingStrategy,
            @Autowired InvoiceFinalizationStrategy invoiceFinalizationStrategy
    ) {
        this.billRunService = billRunService;
        this.invoiceInstantiationStrategy = invoiceInstantiationStrategy;
        this.invoiceProcessingStrategy = invoiceProcessingStrategy;
        this.invoiceFinalizationStrategy = invoiceFinalizationStrategy;
    }

    /**
     * Creates the Invoice
     *
     * @param billRunEvent
     */
    @Async
    @EventListener
    public void handleBillRunEvent(GenericCrudEvent<BillRun> billRunEvent) {
        if (isBillRunStateChangedToInProgress(billRunEvent))
            invoiceInstantiationStrategy.instantiateInvoice(billRunEvent.getCurrent());
    }


    /**
     *
     * @param invoiceEvent
     */
    @Async
    @EventListener
    public void handleInvoiceCreatedEvent(GenericCrudEvent<Invoice> invoiceEvent) {
        // TODO - Check for Invoice Type
        if (isInvoiceStateChangedToNew(invoiceEvent)) {
            invoiceProcessingStrategy.processInvoice(invoiceEvent.getCurrent().getId());
        } else if (isInvoiceStateChangedToInProgress(invoiceEvent)) {
            invoiceFinalizationStrategy.finalizeInvoice(invoiceEvent.getCurrent().getId());
        } else if (isInvoiceStateChangedToComplete(invoiceEvent)) {
            // TODO - Generate Invoice XML / EHF
            // TODO - Persist Invoice XML / EHF
            // TODO - Render Invoice PDF
            // TODO - Persist Invoice PDF
        }

        handleBillRunFinished(invoiceEvent);
    }


    /**
     *
     * @param invoiceEvent
     */
    public void handleBillRunFinished(GenericCrudEvent<Invoice> invoiceEvent) {
        if (isBillRunFinished(invoiceEvent)) {
            Long id = invoiceEvent.getCurrent().getBillRun().getId();
            BillRun billRun = billRunService.get(id);
            // TODO - Check if simulated or failed
            billRun.complete();
            billRunService.update(billRun);
        }

    }



    /**
     *
     * @param invoiceEvent
     * @return
     */
    private boolean isBillRunFinished(GenericCrudEvent<Invoice> invoiceEvent) {
        // TODO - Create Predicate
        return false;
    }



    /**
     *
     * @param billRunEvent
     * @return
     */
    private boolean isBillRunStateChangedToInProgress(GenericCrudEvent<BillRun> billRunEvent) {
        return billRunEvent.getPrevious().getState() == BillRun.State.NEW
                && billRunEvent.getCurrent().getState() == BillRun.State.IN_PROGRESS;
    }



    /**
     *
     * @param invoiceEvent
     * @return
     */
    private boolean isInvoiceStateChangedToComplete(GenericCrudEvent<Invoice> invoiceEvent) {
        return invoiceEvent.getPrevious().getState() == Invoice.State.IN_PROGRESS
                && invoiceEvent.getCurrent().getState() == Invoice.State.COMPLETE;
    }


    /**
     *
     * @param invoiceEvent
     * @return
     */
    private boolean isInvoiceStateChangedToInProgress(GenericCrudEvent<Invoice> invoiceEvent) {
        return invoiceEvent.getPrevious().getState() == Invoice.State.NEW
                && invoiceEvent.getCurrent().getState() == Invoice.State.IN_PROGRESS;
    }



    /**
     *
     * @param invoiceEvent
     * @return
     */
    private boolean isInvoiceStateChangedToNew(GenericCrudEvent<Invoice> invoiceEvent) {
        return invoiceEvent.getPrevious() == null
                && invoiceEvent.getCurrent().getState() == Invoice.State.NEW;
    }



}
