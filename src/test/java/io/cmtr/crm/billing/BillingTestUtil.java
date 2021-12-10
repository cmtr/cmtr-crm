package io.cmtr.crm.billing;

import io.cmtr.crm.billing.model.invoice.DocumentLevelAllowanceCharge;
import io.cmtr.crm.billing.model.invoice.VatCategory;

import java.math.BigDecimal;

public class BillingTestUtil {

    public static VatCategory getVatCategory() {
        return VatCategory
                .factory(
                        new BigDecimal("25"),
                        "NORMAL",
                        "NOK"
                )
                .createNewInstance();
    }

}
