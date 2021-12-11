package io.cmtr.crm.billing.model.invoice;

import io.cmtr.crm.shared.billing.model.IAmount;
import io.cmtr.crm.shared.billing.model.IUnitPrice;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

/**
 *
 * Immutable Embeddable UnitPrice Value Object
 *
 * @author Harald Blikø
 */
@Getter
@EqualsAndHashCode
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnitPrice implements IUnitPrice {


    /**
     *  Unit Price Currency
     */
    private final String currency;



    /**
     * Unit of Measure
     */
    private final String unit;



    /**
     * Gross Price for base quantity
     */
    private final BigDecimal grossPrice;



    /**
     * Will be divided by 100.0
     *
     * @param discount - as percent 0.0 to 100.0
     */
    private BigDecimal unitPriceDiscount;



    /**
     * Base quantity for gross price
     */
    private final BigDecimal grossBasePriceQuantity;



    ///**** FACTORIES ****///



    /**
     *
     * @param currency
     * @param unit
     * @param grossPrice
     * @param unitPriceDiscount
     * @param grossBasePriceQuantity
     * @return
     */
    public static UnitPrice factory(
            String currency,
            String unit,
            BigDecimal grossPrice,
            BigDecimal unitPriceDiscount,
            BigDecimal grossBasePriceQuantity
    ) {
        return new UnitPrice(currency, unit, grossPrice, unitPriceDiscount, grossBasePriceQuantity);
    }



    /**
     *
     * @param currency
     * @param unit
     * @param grossPrice
     * @return
     */
    public static UnitPrice factory(String currency, String unit, BigDecimal grossPrice) {
        return factory(currency, unit, grossPrice, BigDecimal.ZERO, BigDecimal.ONE);
    }



    /**
     *
     * @param unitPrice
     * @return
     */
    public static UnitPrice factory(IUnitPrice unitPrice) {
        return factory(
                unitPrice.getCurrency(),
                unitPrice.getUnit(),
                unitPrice.getGrossPrice(),
                unitPrice.getUnitPriceDiscount(),
                unitPrice.getGrossBasePriceQuantity()
        );
    }

}
