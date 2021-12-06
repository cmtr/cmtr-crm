package io.cmtr.crm.shared.price.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Given a Monetary Amount with precision '" + MonetaryAmountOld.PRECISION + "' and Rounding Mode 'half up' when")
class MonetaryAmountTest {

    @DisplayName("provided a value of '44.345' and currency 'USD' then")
    @Nested
    class RoundingUp {

        static final String amount = "44.345";
        static final String currency =  "USD";
        final MonetaryAmountOld monetaryAmount = new MonetaryAmountOld(amount, currency);

        @Test
        @DisplayName("it rounds up")
        void rounding() {
            BigDecimal expected = new BigDecimal("44.35");
            assertEquals(expected, monetaryAmount.getAmount());

        }

        @Test
        @DisplayName("equals different monetary amount with same values")
        void equals() {
            MonetaryAmountOld expected = new MonetaryAmountOld(amount, currency);
            assertEquals(expected, monetaryAmount);
            assertFalse(expected == monetaryAmount);
        }

    }

    @DisplayName("provided a value of '44.342' and currency 'USD' then")
    @Nested
    class RoundingDown{


        static final String amount = "44.342";
        static final String currency =  "USD";
        final MonetaryAmountOld monetaryAmount = new MonetaryAmountOld(amount, currency);


        @Test
        @DisplayName("it rounds down")
        void rounding() {
            BigDecimal expected = new BigDecimal("44.34");
            assertEquals(expected, monetaryAmount.getAmount());
        }

        @Test
        @DisplayName("equals different monetary amount with same values")
        void equals() {
            MonetaryAmountOld expected = new MonetaryAmountOld(amount, currency);
            assertEquals(expected, monetaryAmount);
            assertFalse(expected == monetaryAmount);
        }

    }

    @Nested
    @DisplayName("the amount is the same but the currencies is")
    class differentCurrency {

        static final String amount = "44.342";

        @Test
        @DisplayName("different then it does not equal")
        void different() {
            MonetaryAmountOld one = new MonetaryAmountOld(amount, "USD");
            MonetaryAmountOld two = new MonetaryAmountOld(amount, "EUR");
            assertNotEquals(one, two);
        }

        @Test
        @DisplayName("the same then it does equal")
        void same() {
            MonetaryAmountOld one = new MonetaryAmountOld(amount, "USD");
            MonetaryAmountOld two = new MonetaryAmountOld(amount, "USD");
            assertEquals(one, two);
        }

    }

}