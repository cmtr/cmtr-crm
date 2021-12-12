package io.cmtr.crm.billing.model.invoice;

import io.cmtr.crm.billing.BillingTestUtil;
import io.cmtr.crm.customer.CustomerTestUtil;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;
import io.cmtr.crm.shared.billing.model.IAmount;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Invoice Model")
class InvoiceTest {

    static final Supplier supplier = CustomerTestUtil.getSupplier();
    static final BillingAccount billingAccount = CustomerTestUtil.getBillingAccount();


    @Nested
    @DisplayName(
            "Given a invoice with a supplier and billing account"
    )
    class NewInvoice {

        final BigDecimal charge = BillingTestUtil.DOCUMENT_CHARGE_NET;
        final BigDecimal allowance = BillingTestUtil.DOCUMENT_ALLOWANCE_NET;
        final VatCategory vatCategory = BillingTestUtil.getVatCategory();
        final UnitPrice unitPrice = BillingTestUtil.getUnitPrice();
        final Quantity quantity = BillingTestUtil.getQuantity();
        final InvoiceDocumentLevelAllowanceCharge documentLevelCharge = InvoiceDocumentLevelAllowanceCharge
                .factory(true, supplier, billingAccount, vatCategory, charge)
                .createNewInstance();
        final InvoiceDocumentLevelAllowanceCharge documentLevelAllowance = InvoiceDocumentLevelAllowanceCharge
                .factory(false, supplier, billingAccount, vatCategory, allowance)
                .createNewInstance();
        final InvoiceLineItem lineItem = InvoiceLineItem
                .factory(supplier, billingAccount, vatCategory, unitPrice, quantity)
                .createNewInstance();
        final InvoiceLineItemAllowanceCharge lineItemCharge = InvoiceLineItemAllowanceCharge
                .factory(true, supplier, billingAccount, charge)
                .createNewInstance();
        final InvoiceLineItemAllowanceCharge lineItemAllowance = InvoiceLineItemAllowanceCharge
                .factory(false, supplier, billingAccount, allowance)
                .createNewInstance();


        Invoice invoice;

        @BeforeEach
        void setUp() {
            invoice = Invoice
                    .factory(supplier, billingAccount)
                    .createNewInstance();
        }


        @Test
        @DisplayName("when it is created then")
        void noCharge() {
            assertEquals(Invoice.State.NEW, invoice.getState(), "then state is NEW");
            assertEquals(supplier, invoice.getSupplier());
            assertEquals(billingAccount, invoice.getBillingAccount());
            assertEquals(BigDecimal.ZERO, invoice.getAmount(), "Amount - Net Amount");
            assertEquals(BigDecimal.ZERO, invoice.getTotalNetAmount(), "Net Amount");
            assertEquals(BigDecimal.ZERO, invoice.getTotalChargeNetAmount(), "Charge Net Amount");
            assertEquals(BigDecimal.ZERO, invoice.getTotalAllowanceNetAmount(), "Allowance Net Amount");
            assertEquals(BigDecimal.ZERO, invoice.getTotalLineItemNetAmount(), "Line Item Net Amount");
            assertEquals(BigDecimal.ZERO, invoice.getTotalVatAmount(), "Total VAT Amount");
            assertEquals(BigDecimal.ZERO, invoice.getTotalAmountIncludingVat(), "Total inc Vat");
            assertEquals(Invoice.State.NEW, invoice.getState());
            assertEquals(AllowanceCharge.State.PREPARING, documentLevelCharge.getState());
            assertTrue(supplier == documentLevelCharge.getSupplier());
            assertEquals(supplier, documentLevelCharge.getSupplier());
            assertEquals(AllowanceCharge.State.PREPARING, documentLevelAllowance.getState());
            assertEquals(charge, documentLevelCharge.getNet());
            assertEquals(allowance, documentLevelAllowance.getNet());
            assertEquals(supplier, invoice.getSupplier());
            assertEquals(billingAccount, invoice.getBillingAccount());
        }


        @Test
        @DisplayName("when a charge is added to a invoice then")
        void withCharge() {
            invoice.addDocumentLevelAllowanceCharge(documentLevelCharge);
            assertEquals(Invoice.State.PREPARE, invoice.getState());
            BigDecimal net = charge.setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE);
            assertEquals(net, invoice.getAmount(), "Amount - Net Amount");
            assertEquals(net, invoice.getTotalNetAmount(), "Net Amount");

            BigDecimal vat = vatCategory.getVatAmount(net);
            assertEquals(vat, invoice.getTotalVatAmount());
        }

        @Test
        @DisplayName("when a allowance is added to a invoice then")
        void withAllowance() {
            invoice.addDocumentLevelAllowanceCharge(documentLevelAllowance);
            BigDecimal net = allowance.negate().setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE);
            assertEquals(net, invoice.getAmount(), "Amount - Net Amount");
            assertEquals(net, invoice.getTotalNetAmount(), "Net Amount");

            BigDecimal vat = vatCategory.getVatAmount(net);
            assertEquals(vat, invoice.getTotalVatAmount());
        }


        @Test
        @DisplayName("when a charge and allowance is added to a invoice then")
        void withChargeAndAllowance() {
            invoice.addDocumentLevelAllowanceCharge(documentLevelAllowance);
            invoice.addDocumentLevelAllowanceCharge(documentLevelCharge);
            BigDecimal net = charge.setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE)
                    .subtract(allowance.setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE));
            assertEquals(net, invoice.getAmount(), "Amount - Net Amount");
            assertEquals(net, invoice.getTotalNetAmount(), "Net Amount");

            BigDecimal vat = vatCategory.getVatAmount(net);
            assertEquals(vat, invoice.getTotalVatAmount());
        }

        @Test
        @DisplayName("when charge with different supplier and/or billing account is added then")
        void withChargeOfDifferentSupplierBillingAccount() {
            assertThrows(IllegalArgumentException.class,
                    () -> invoice.addDocumentLevelAllowanceCharge(BillingTestUtil.getDocumentLevelCharge()));
        }


        @Test
        @DisplayName("when allowance with different supplier and/or billing account is added then")
        void withAllowanceOfDifferentSupplierBillingAccount() {
            assertThrows(IllegalArgumentException.class,
                    () -> invoice.addDocumentLevelAllowanceCharge(BillingTestUtil.getDocumentLevelCharge()));
        }

        @Test
        @DisplayName("when charge with different supplier and/or billing account is added then")
        void withLineItemOfDifferentSupplierBillingAccount() {
            assertThrows(IllegalArgumentException.class,
                    () -> invoice.addInvoiceLineItem(BillingTestUtil.getLineItem()));
        }

        @Test
        @DisplayName("when a line item is added")
        void withLineItem() {
            invoice.addInvoiceLineItem(lineItem);
            BigDecimal net = unitPrice.getNetAmount(quantity);
            assertEquals(net, invoice.getTotalNetAmount());
        }


        @Test
        @DisplayName("when a line item with line item charge is added")
        void withLineItemWithCharges() {
            InvoiceLineItem lineItem = InvoiceLineItem
                    .factory(supplier, billingAccount, vatCategory, unitPrice, quantity)
                    .createNewInstance();
            lineItem.addAllowanceCharge(lineItemCharge);
            invoice.addInvoiceLineItem(lineItem);
            BigDecimal net = unitPrice
                    .getNetAmount(quantity)
                    .add(charge.setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE));
            assertEquals(net, invoice.getTotalNetAmount());
        }

        @Test
        @DisplayName("when a line item with line item allowance is added")
        void withLineItemWithAllowance() {
            InvoiceLineItem lineItem = InvoiceLineItem
                    .factory(supplier, billingAccount, vatCategory, unitPrice, quantity)
                    .createNewInstance();
            lineItem.addAllowanceCharge(lineItemAllowance);
            invoice.addInvoiceLineItem(lineItem);
            BigDecimal net = unitPrice
                    .getNetAmount(quantity)
                    .subtract(allowance.setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE));
            assertEquals(net, invoice.getTotalNetAmount());
        }


        @Test
        @DisplayName("when a line item with line item allowance and charge is added")
        void withLineItemWithAllowanceAndCharge() {
            InvoiceLineItem lineItem = InvoiceLineItem
                    .factory(supplier, billingAccount, vatCategory, unitPrice, quantity)
                    .createNewInstance();
            lineItem.addAllowanceCharge(lineItemCharge);
            lineItem.addAllowanceCharge(lineItemAllowance);
            invoice.addInvoiceLineItem(lineItem);
            BigDecimal net = unitPrice
                    .getNetAmount(quantity)
                    .add(charge.setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE))
                    .subtract(allowance.setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE));
            assertEquals(net, invoice.getTotalNetAmount());
        }

        @Test
        @DisplayName("when a document level allowance and charge, and" +
                " line item with line item allowance and charge is added ")
        void withDocumentLevelAllowanceAndChargeAndLineItemWithLineItemAllowanceAndCharge() {
            InvoiceLineItem lineItem = InvoiceLineItem
                    .factory(supplier, billingAccount, vatCategory, unitPrice, quantity)
                    .createNewInstance()
                    .addAllowanceCharge(lineItemCharge)
                    .addAllowanceCharge(lineItemAllowance);
            invoice
                    .addInvoiceLineItem(lineItem)
                    .addDocumentLevelAllowanceCharge(documentLevelCharge)
                    .addDocumentLevelAllowanceCharge(documentLevelAllowance);
            BigDecimal net = unitPrice
                    .getNetAmount(quantity)
                    .add(charge.setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE))
                    .add(charge.setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE))
                    .subtract(allowance.setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE))
                    .subtract(allowance.setScale(IAmount.PRECISION, IAmount.ROUNDING_MODE));
            assertEquals(net, invoice.getTotalNetAmount());
        }

        @Test
        @DisplayName("when updating the supplier and billing account in NEW state")
        void updatingSupplierWhenNew() {
            Invoice other = Invoice.factory(CustomerTestUtil.getSupplier(), CustomerTestUtil.getBillingAccount());
            invoice.update(other);
            assertEquals(other.getSupplier(), invoice.getSupplier());
            assertEquals(other.getBillingAccount(), invoice.getBillingAccount());
        }

        @Test
        @DisplayName("when updating the supplier and billing account in NOT new state")
        void updatingSupplierWhenNotNew() {
            invoice.addDocumentLevelAllowanceCharge(documentLevelCharge);
            assertThrows(IllegalStateException.class,
                    () -> invoice.update(Invoice.factory(CustomerTestUtil.getSupplier(), CustomerTestUtil.getBillingAccount())));
        }


        @Test
        @DisplayName("when completing with charges, allowances and invoice lines")
        void completingInvoice() {
            InvoiceLineItem lineItem = InvoiceLineItem
                    .factory(supplier, billingAccount, vatCategory, unitPrice, quantity)
                    .createNewInstance()
                    .addAllowanceCharge(lineItemCharge)
                    .addAllowanceCharge(lineItemAllowance);
            invoice
                    .addInvoiceLineItem(lineItem)
                    .addDocumentLevelAllowanceCharge(documentLevelCharge)
                    .addDocumentLevelAllowanceCharge(documentLevelAllowance);
            invoice.complete();
            assertEquals(Invoice.State.COMPLETE, invoice.getState());
            List<AllowanceCharge> allowanceChargeList = invoice
                    .getAllowanceCharges()
                    .stream()
                    .map(e -> (AllowanceCharge) e)
                    .collect(Collectors.toList());
            allowanceChargeList.addAll(invoice
                    .getLineItems()
                    .stream()
                    .flatMap(e -> ((InvoiceLineItem) e).getAllowanceCharges().stream().map(i -> (AllowanceCharge) i))
                    .collect(Collectors.toList()));
            Predicate<AllowanceCharge> allowanceChargePredicate = e ->
                    e.getState() == AllowanceCharge.State.COMPLETE &&
                    e.getInvoice().equals(invoice);
            assertTrue(allowanceChargeList.stream().allMatch(allowanceChargePredicate));
            assertNotEquals(billingAccount.getOwner(), invoice.getOwner());
            assertNotNull(invoice.getOwner());
            assertNotEquals(billingAccount.getRecipient(), invoice.getRecipient());
            assertNotNull(invoice.getRecipient());
            assertNotEquals(billingAccount.getOwner(), invoice.getIssuer());
            assertNotNull(invoice.getIssuer());
            assertNotNull(invoice.getIssueDate());
        }
    }



}