package io.cmtr.crm.shared.billing.model;

import io.cmtr.crm.shared.mediation.model.IQuantity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Invoice Line Item
 *
 * Based on Poppel Bis 3 Standard
 *
 * @author Harald Blikø
 */
public interface IInvoiceLineItem extends IAllowanceCharge, IQuantity {

    BigDecimal getGrossPrice();

    BigDecimal getGrossPriceBaseQuantity();

    BigDecimal getUnitPriceDiscount();

    default BigDecimal getNetPrice() {
        return getGrossPrice()
                .multiply(getUnitPriceDiscount())
                .divide(new BigDecimal("100"));
    }

    IVatCategory getVatCategory();

    default BigDecimal getTotalNetAmount() {
        return getAmount();
    }

    List<IInvoiceLineItemAllowanceCharge> getAllowances();

    List<IInvoiceLineItemAllowanceCharge> getCharges();

    default BigDecimal getTotalAllowanceNetAmount() {
        return getAllowances()
                .stream()
                .map(IInvoiceLineItemAllowanceCharge::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    default BigDecimal getTotalChargeNetAmount() {
        return getCharges()
                .stream()
                .map(IAllowanceCharge::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    default BigDecimal getAmount() {
        return getNetPrice()
                .divide(getGrossPriceBaseQuantity())
                .multiply(getQuantity())
                .add(getTotalChargeNetAmount())
                .subtract(getTotalAllowanceNetAmount());
    }

}
