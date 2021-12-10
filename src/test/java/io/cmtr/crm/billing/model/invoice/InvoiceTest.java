package io.cmtr.crm.billing.model.invoice;

import io.cmtr.crm.billing.BillingTestUtil;
import io.cmtr.crm.customer.CustomerTestUtil;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Invoice Model")
class InvoiceTest {

    static final Supplier supplier = CustomerTestUtil.getSupplier();
    static final BillingAccount billingAccount = CustomerTestUtil.getBillingAccount();


    @Nested
    @DisplayName(
            "Given a supplier and billing account"
    )
    class A {


        @Test
        @DisplayName("when a invoice is initiated")
        void testFactory() {
            Invoice invoice = Invoice
                    .factory(supplier, billingAccount)
                    .createNewInstance();
            assertEquals(Invoice.State.NEW, invoice.getState(), "then state is NEW");
        }

        @Nested
        @DisplayName("and document level charges and allowances")
        class AA {

            final BigDecimal charge = new BigDecimal("150.333");
            final BigDecimal allowance = new BigDecimal("77.456");
            final VatCategory vatCategory = BillingTestUtil.getVatCategory();
            Invoice invoice;
            DocumentLevelAllowanceCharge documentLevelCharge;
            DocumentLevelAllowanceCharge documentLevelAllowance;

            @BeforeEach
            void setUp() {
                invoice = Invoice
                        .factory(supplier, billingAccount)
                        .createNewInstance();
                documentLevelCharge = DocumentLevelAllowanceCharge
                        .factory(true, supplier, billingAccount, vatCategory, charge)
                        .createNewInstance();
                documentLevelAllowance = DocumentLevelAllowanceCharge
                        .factory(false, supplier, billingAccount, vatCategory, allowance)
                        .createNewInstance();
            }

            @Test
            void testSetup() {
                assertEquals(AllowanceCharge.State.PREPARING, documentLevelCharge.getState());
                assertTrue(supplier == documentLevelCharge.getSupplier());
                assertEquals(supplier, documentLevelCharge.getSupplier());
                assertEquals(AllowanceCharge.State.PREPARING, documentLevelAllowance.getState());
                assertEquals(charge, documentLevelCharge.getNetAmount());
                assertEquals(allowance, documentLevelAllowance.getNetAmount());
            }

            @Test
            @DisplayName("when a invoice is created then")
            void noCharge() {
                Invoice invoice = Invoice
                        .factory(supplier, billingAccount)
                        .createNewInstance();
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
            }


            @Test
            @DisplayName("when a charge is added to a invoice then")
            void withCharge() {

            }

            @Test
            @DisplayName("when a allowance is added to a invoice then")
            void withAllowance() {

            }


            @Test
            @DisplayName("when a charge and allowance is added to a invoice then")
            void withChargeAndAllowance() {

            }

            @Nested
            @DisplayName("and line items")
            class AAA {

                @Nested
                @DisplayName("without line item charges")
                class AAAA {



                }

                @Nested
                @DisplayName("with line item charges")
                class AAAB {

                }

                @Nested
                @DisplayName("with and without  line items charges")
                class AAAC {

                }
            }
        }


    }

}