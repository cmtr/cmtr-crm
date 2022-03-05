package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.billcycle.BillRun;
import io.cmtr.crm.billing.model.invoice.Invoice;
import io.cmtr.crm.billing.model.invoice.InvoiceLineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 *
 * @author Harald Blikø
 */
@Component
public class InvoiceGenerationStrategyImpl implements InvoiceInstantiationStrategy, InvoiceProcessingStrategy, InvoiceFinalizationStrategy {

    /**
     *
     */
    private final InvoiceService invoiceService;
    private final AllowanceChargeService allowanceChargeService;


    public InvoiceGenerationStrategyImpl(
            @Autowired InvoiceService invoiceService,
            @Autowired AllowanceChargeService allowanceChargeService
    ) {
        this.invoiceService = invoiceService;
        this.allowanceChargeService = allowanceChargeService;
    }

    /**
     *
     * @param billRun
     * @return
     */
    @Override
    public void instantiateInvoice(BillRun billRun) {
        billRun
            .getBillingAccounts()
            .forEach(billingAccount -> {
                Invoice invoice = Invoice.factory(billRun, billingAccount);
                invoiceService.create(invoice);
            });
    }



    /**
     *
     * @param id
     * @return
     */
    @Override
    public Invoice processInvoice(Long id) {
        Invoice invoice = invoiceService.get(id);
        return processInvoice(invoice);
    }



    /**
     *
     * @param invoice
     * @return
     */
    @Override
    public Invoice processInvoice(Invoice invoice) {
        addInvoiceLineItems(invoice);
        addInvoiceDocumentLevelAllowanceCharge(invoice);
        return invoiceService.save(invoice);
    }



    /**
     *
     * @param invoice
     */
    private void addInvoiceLineItems(Invoice invoice) {
        Set<InvoiceLineItem> lineItems = allowanceChargeService
                .getLineItems(
                        invoice.getSupplier(),
                        invoice.getBillingAccount(),
                        false,
                        invoice.getBillRun().getPeriodEnd()
                );
        invoice.addInvoiceLineItem(lineItems);
    }



    /**
     *
     * @param invoice
     */
    private void addInvoiceDocumentLevelAllowanceCharge(Invoice invoice) {
        // TODO
    }



    /**
     *
     * @param id
     * @return
     */
    @Override
    public Invoice finalizeInvoice(Long id) {
        Invoice invoice = invoiceService.get(id);
        return finalizeInvoice(invoice);
    }



    /**
     *
     * @param invoice
     * @return
     */
    @Override
    public Invoice finalizeInvoice(Invoice invoice) {
        // TODO - Check Invoice and Bill Run Type
        if (true) {
            invoice.complete();
        } else {
            invoice.simulate();
        }
        return invoiceService.save(invoice);
    }

}
