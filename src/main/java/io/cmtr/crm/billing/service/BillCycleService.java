package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.billcycle.BillCycle;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.shared.generic.repository.GenericRepository;
import io.cmtr.crm.shared.generic.service.GenericService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BillCycleService extends GenericService<UUID, BillCycle> implements IBillCycleService {

    public BillCycleService(GenericRepository<UUID, BillCycle> repository) {
        super(repository);
    }

}
