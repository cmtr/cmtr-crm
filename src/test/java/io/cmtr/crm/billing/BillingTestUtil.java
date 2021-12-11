package io.cmtr.crm.billing;

import io.cmtr.crm.billing.model.invoice.*;
import io.cmtr.crm.customer.CustomerTestUtil;
import io.cmtr.crm.customer.model.Customer;

import java.math.BigDecimal;

public class BillingTestUtil {

    public final static BigDecimal VAT_RATE = new BigDecimal("25");
    public final static String CURRENCY = "NOK";
    public final static String CATEGORY = "N";
    public final static String UNIT = "EA";
    public final static BigDecimal QUANTITY = new BigDecimal("201.345");
    public final static BigDecimal GROSS_PRICE = new BigDecimal("1.5338");
    public final static BigDecimal DOCUMENT_CHARGE_NET = new BigDecimal("154.344");
    public final static BigDecimal DOCUMENT_ALLOWANCE_NET = new BigDecimal("72.344");

    public static VatCategory getVatCategory() {
        return VatCategory
                .factory(VAT_RATE, CURRENCY, CATEGORY)
                .createNewInstance();
    }


    public static InvoiceDocumentLevelAllowanceCharge getDocumentLevelCharge() {
        return InvoiceDocumentLevelAllowanceCharge
                .factory(true, CustomerTestUtil.getSupplier(), CustomerTestUtil.getBillingAccount(), getVatCategory(), DOCUMENT_CHARGE_NET)
                .createNewInstance();
    }

    public static InvoiceDocumentLevelAllowanceCharge getDocumentLevelAllowance() {
        return InvoiceDocumentLevelAllowanceCharge
                .factory(true, CustomerTestUtil.getSupplier(), CustomerTestUtil.getBillingAccount(), getVatCategory(), DOCUMENT_ALLOWANCE_NET)
                .createNewInstance();
    }


    public static UnitPrice getUnitPrice() {
        return UnitPrice.factory(CURRENCY, UNIT, GROSS_PRICE);
    }


    public static Quantity getQuantity() {
        return Quantity.factory(UNIT, QUANTITY);
    }


    public static InvoiceLineItem getLineItem() {
        return InvoiceLineItem
                .factory(CustomerTestUtil.getSupplier(), CustomerTestUtil.getBillingAccount(), getVatCategory(), getUnitPrice(), getQuantity())
                .createNewInstance();
    }
}
