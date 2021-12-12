package io.cmtr.crm.shared.billing.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Invoice
 *
 * Based on Poppel Bis 3 Standard
 *
 * @author Harald Blikø
 */
public interface IInvoice extends IAmount, IMonetary {

    List<IInvoiceLineItem> getLineItems();

    List<IDocumentLevelAllowanceCharge> getAllowances();

    List<IDocumentLevelAllowanceCharge> getCharges();

    default BigDecimal getTotalLineItemNetAmount() {
        return getLineItems()
                .stream()
                .map(IInvoiceLineItem::getTotalNetAmount)
                // TODO - Differentiate between charge and allowance
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    default BigDecimal getTotalAllowanceNetAmount() {
        return getAllowances()
                .stream()
                .map(IAllowanceCharge::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    default BigDecimal getTotalChargeNetAmount() {
        return getCharges()
                .stream()
                .map(IAllowanceCharge::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    default BigDecimal getTotalNetAmount() {
        return getTotalLineItemNetAmount()
                .add(getTotalChargeNetAmount())
                .subtract(getTotalAllowanceNetAmount());
    }



    default BigDecimal getTotalAmountIncludingVat() {
        return getTotalNetAmount().add(getTotalVatAmount());
    }

    List<IVatCategoryAmount> getVatCategoryAmounts();

    default BigDecimal getTotalVatAmount() {
        return getVatCategoryAmounts()
                .stream()
                .map(IVatCategoryAmount::getVatAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }



}