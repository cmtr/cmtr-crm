package io.cmtr.crm.customer.model;

import io.cmtr.crm.customer.CustomerTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Customer Model")
class CustomerTest {

    @Nested
    @DisplayName("Given a customer")
    class customerTest {

        @Test
        @DisplayName("when it is created")
        void whenCreated() {
            Customer customer = CustomerTestUtil
                    .getCustomer()
                    .createNewInstance();
            assertEquals(Customer.State.NEW, customer.getState());
        }

        @Test
        @DisplayName("when it is closed")
        void whenClosed() {

        }

        @DisplayName("that is a supplier")
        class supplierTest{

            @Test
            @DisplayName("when updating type")
            void updateType() {

            }
        }
    }

}