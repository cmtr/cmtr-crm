package io.cmtr.crm.shared.billing.model;


/**
 * Document Level Allowance Charge
 *
 * Unlike at Line Item on the Document Level the Allowance and Charge require a VAT category
 *
 * Based on Poppel Bis 3 Standard
 *
 * @author Harald Blikø
 *
 */
public interface IDocumentLevelAllowanceCharge extends IAllowanceCharge {

    IVatCategory getVatCategory();


}
