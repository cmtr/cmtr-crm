package io.cmtr.crm.customer.event;

import io.cmtr.crm.customer.model.BillingAccount;
import lombok.Getter;

public class IllegalBillingAccountStateException extends IllegalStateException {

    @Getter
    private final BillingAccount source;

    public IllegalBillingAccountStateException(BillingAccount source) {
        this.source = source;
    }

    public IllegalBillingAccountStateException(String s, BillingAccount source) {
        super(s);
        this.source = source;
    }
}
