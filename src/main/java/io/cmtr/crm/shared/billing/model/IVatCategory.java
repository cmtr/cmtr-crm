package io.cmtr.crm.shared.billing.model;

import java.math.BigDecimal;

public interface IVatCategory {

    String getCategory();
    BigDecimal getRate();

}
