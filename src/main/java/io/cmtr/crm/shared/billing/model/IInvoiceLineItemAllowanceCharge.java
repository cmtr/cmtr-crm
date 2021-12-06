package io.cmtr.crm.shared.billing.model;

/**
 *
 * Invoice Line Item Allowance Charge
 *
 * Unlike the Account or Document Level the Vat Category is not defined on the Line Item Allowance or Charge. Per
 * the standard definition the Vat Category shall be inferred from the Line Item Vat Category
 *
 * Based on Poppel Bis 3 Standard
 *
 * @author Harald Blikø
 *
 */
public interface IInvoiceLineItemAllowanceCharge extends IAllowanceCharge {

}
