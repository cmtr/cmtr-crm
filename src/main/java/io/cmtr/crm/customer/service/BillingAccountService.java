package io.cmtr.crm.customer.service;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.repository.BillingAccountRepository;
import io.cmtr.crm.shared.generic.event.CrudEventType;
import io.cmtr.crm.shared.generic.service.GenericService;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class BillingAccountService extends GenericService<UUID, BillingAccount> {

    public BillingAccountService(@Autowired BillingAccountRepository repository) {
        super(repository);
    }

    @Transactional
    public BillingAccount bar(UUID id, boolean barred) {
        BillingAccount dbDomain = get(id);
        BillingAccount previous = SerializationUtils.clone(dbDomain);
        dbDomain.setBarred(barred);
        publish(dbDomain, previous, CrudEventType.UPDATE);
        return repository
                    .save(dbDomain);
    }

    @Override
    protected void publish(BillingAccount current, BillingAccount prev, CrudEventType type) {
        super.publish(current, prev, type);
        System.out.printf("Billing Account Event of '%s'%n", type);
    }
}