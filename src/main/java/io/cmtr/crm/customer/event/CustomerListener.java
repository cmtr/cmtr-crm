package io.cmtr.crm.customer.event;

import io.cmtr.crm.customer.dto.ICustomerToBillingAccountMapper;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.service.BillingAccountService;
import io.cmtr.crm.customer.model.Customer;
import io.cmtr.crm.shared.generic.event.CrudEventType;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class CustomerListener {

    private final BillingAccountService billingAccountService;
    private final ICustomerToBillingAccountMapper customerToBillingAccountMapper;

    public CustomerListener(
            @Autowired BillingAccountService billingAccountService,
            @Autowired ICustomerToBillingAccountMapper customerToBillingAccountMapper
    ) {
        this.billingAccountService = billingAccountService;
        this.customerToBillingAccountMapper = customerToBillingAccountMapper;
    }


    public void handleCustomerEvent(CustomerEvent event) {
        if (event.getCurrent().isBarred() && !event.getCurrent().isBarred())

        switch (event.getType()) {
            case CREATE:
                handleCustomerCreated(event);
                break;
            case UPDATE:
                if (updateBarring(event)) handleCustomerBarring(event);
                break;
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleCustomerCreated(CustomerEvent event) {
        if (event.getType() == CrudEventType.CREATE) {
            Customer customer = event.getCurrent();
            BillingAccount billingAccount = BillingAccount.createNewInstance(customer, customerToBillingAccountMapper);
            billingAccountService.create(billingAccount);
        }
    }

    private void handleCustomerBarring(CustomerEvent event) {
        val barred = event.getCurrent().isBarred();
        event
            .getCurrent()
            .getBillingAccountIds()
            .forEach((id) -> billingAccountService.bar(id, barred));
    }

    private boolean updateBarring(CustomerEvent event) {
        val current = event.getCurrent().isBarred();
        val previous = event.getPrevious().isBarred();
        return ((current && !previous) || (!current && previous));
    }
}
