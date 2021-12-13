package io.cmtr.crm.billing.model.billcycle;

import io.cmtr.crm.customer.CustomerTestUtil;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Bill Cycle Model")
class BillCycleTest {

    @Nested
    @DisplayName("Given a bill cycle with a supplier and a billing account")
    class NewBillCycle {

        final Supplier supplier = CustomerTestUtil.getSupplier();
        final BillingAccount billingAccount = CustomerTestUtil.getBillingAccount();
        final String billCycleName = "BILL_CYCLE_NAME";

        @Nested
        @DisplayName("that is continuous")
        class Continuous {

            final int daysShift = 2;
            final int monthStep = 2;

            ContinuousBillCycle billCycle;

            @BeforeEach
            void setUp() {
                billCycle = ContinuousBillCycle
                    .factory(supplier, billCycleName, monthStep, daysShift)
                    .createNewInstance();
            }


            @Test
            @DisplayName("when it is created")
            void newBillCycle() {
                assertEquals(ContinuousBillCycle.DISCRIMINATOR_VALUE, billCycle.getType());
                assertEquals(billCycleName, billCycle.getName());
                assertEquals(supplier, billCycle.getSupplier());
                assertFalse(billCycle.isLocked());
                assertEquals(daysShift, billCycle.getDayShift());
                assertEquals(monthStep, billCycle.getMonthStep());
            }

            @Test
            @DisplayName("when a billing account is added")
            void addBillingAccount() {
                billCycle.addBillingAccount(billingAccount);
                assertTrue(billCycle.getBillingAccounts().contains(billingAccount));
            }

            @Test
            @DisplayName("when the bill cycle is locked and a billing account is added")
            void whenLockedAddBillingAccount() {
                billCycle.setLocked(true);
                assertThrows(IllegalStateException.class, () -> billCycle.addBillingAccount(billingAccount));
            }

            @Nested
            @DisplayName("and incremental")
            class Incremental {

                IncrementalBillCycle billCycle;
                final int monthShift = 3;

                @BeforeEach
                void setUp() {
                    billCycle = IncrementalBillCycle
                            .factory(supplier, billCycleName, monthStep, daysShift, monthShift)
                            .createNewInstance();
                }

                @Test
                @DisplayName("when it is created")
                void newBillCycle() {
                    assertEquals(IncrementalBillCycle.DISCRIMINATOR_VALUE, billCycle.getType());
                    assertEquals(billCycleName, billCycle.getName());
                    assertEquals(supplier, billCycle.getSupplier());
                    assertFalse(billCycle.isLocked());
                    assertEquals(daysShift, billCycle.getDayShift());
                    assertEquals(monthStep, billCycle.getMonthStep());
                    assertEquals(monthShift, billCycle.getMonthShift());
                }

            }

        }

    }

}