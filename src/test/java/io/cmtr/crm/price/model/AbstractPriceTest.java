package io.cmtr.crm.price.model;

import io.cmtr.crm.customer.CustomerTestUtil;
import io.cmtr.crm.customer.model.Supplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Period;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Price Model")
class AbstractPriceTest {

    public Supplier supplier = CustomerTestUtil.getSupplier();
    public String unit = "EA";
    public String currency = "NOK";
    public ZonedDateTime date = ZonedDateTime.now().minus(Period.ofDays(1));

    @Nested
    @DisplayName("Given a fixed price of 10.001 nok")
    class fixedPrice {

        final BigDecimal price = new BigDecimal("10.001");

        @DisplayName("when given a quantity of 10")
        void one() {
            // then the total is 10.001
            // then the total amount is 10.00
        }

    }

    @Nested
    @DisplayName("Given a unit price")
    class unitPrice {

        @Nested
        @DisplayName("that is fixed per unit of 9.999")
        class fixedPerUnit {

            final BigDecimal unitPriceAmount = new BigDecimal("9.999");
            final BigDecimal one = BigDecimal.ONE;
            final BigDecimal many = new BigDecimal("3.52");


            @Test
            @DisplayName("when the quantity is exactly one")
            void one() {

            }


            @DisplayName("with discrete calculation")
            class discrete {

                @Test
                @DisplayName("when the quantity is exactly one")
                void one() {
                    // DTO & Factory
                    UnitPrice unitPriceDto = UnitPrice.factory(supplier, currency, unit);
                    assertEquals(UnitPrice.DISCRIMINATOR_VALUE, unitPriceDto.getType());
                    assertEquals(supplier, unitPriceDto.getSupplier());
                    assertEquals(currency, unitPriceDto.getCurrency());
                    assertEquals(unit, unitPriceDto.getUnit());

                    // New Instance
                    UnitPrice unitPrice = unitPriceDto.createNewInstance();
                    assertEquals(supplier, unitPrice.getSupplier());
                    assertEquals(currency, unitPrice.getCurrency());
                    assertEquals(unit, unitPrice.getUnit());
                    assertEquals(AbstractPrice.State.NEW, unitPrice.getState());

                    // Add Price Price Instance
                    unitPrice.saveUnitPriceInstance(date, unitPriceAmount);
                    assertEquals(AbstractPrice.State.ACTIVE, unitPrice.getState());
                    assertEquals(unitPriceAmount, unitPrice.getUnitPrice(date));
                    assertThrows(NullPointerException.class, () -> unitPrice.getUnitPrice(ZonedDateTime.now()));
                }

                // Rounding mode and step

                @Test
                @DisplayName("when the quantity is 3.52 and rounding mode floor")
                void floor() {

                }

                @Test
                @DisplayName("when the quantity is 3.52 and rounding mode cieling")
                void ciel() {

                }

                @Test
                @DisplayName("when the quantity is 3.52 and rounding mode half-up")
                void halfup() {

                }

            }

            @DisplayName("with continuous calculation")
            class continuous {

            }

        }

        @Nested
        @DisplayName("that is tiered")
        class tieredUnitPrice {



        }

    }

    @Nested
    @DisplayName("Given a period price")
    class periodPrice {

        @Nested
        @DisplayName("that is fixed")
        class fixedPeriodPrice {

            final BigDecimal price = new BigDecimal("8.999");
            // Month
            // Rounding mode and step

        }

        // Electricity
        @Nested
        @DisplayName("that is variable")
        class variablePeriodPrice {


            // Rounding mode and step

        }

    }



}