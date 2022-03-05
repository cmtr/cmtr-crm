package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.billcycle.BillCycle;
import io.cmtr.crm.billing.model.billcycle.BillRun;
import io.cmtr.crm.billing.repository.BillCycleRepository;
import io.cmtr.crm.shared.generic.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * BillCycle Service Implementation
 *
 * @author Harald Blikø
 */
@Service
public class BillCycleServiceImpl extends GenericService<UUID, BillCycle> implements BillCycleService {



    /**
     *
     */
    private final BillRunService billRunService;



    /**
     *
     * @param repository
     * @param billRunService
     */
    public BillCycleServiceImpl(
            @Autowired BillCycleRepository repository,
            @Autowired BillRunService billRunService
    ) {
        super(repository);
        this.billRunService = billRunService;
    }



    /**
     *
     * @param billCycleId
     * @return
     */
    public BillRun createBillRun(UUID billCycleId) {
        BillCycle billCycle = get(billCycleId);
        BillRun billRun = BillRun.factory(billCycle);
        return billRunService.create(billRun);
    }



}
