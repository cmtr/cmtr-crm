package io.cmtr.crm.shared.billing.model;

import io.cmtr.crm.shared.price.model.IMonetaryAmountOld;

public interface IPayment {

    IMonetaryAmountOld getAmount();

}
