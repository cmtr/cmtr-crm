package io.cmtr.crm.billing.service;


import io.cmtr.crm.billing.model.billcycle.BillRun;
import io.cmtr.crm.billing.repository.BillRunRepository;
import io.cmtr.crm.shared.generic.event.CrudEventType;
import io.cmtr.crm.shared.generic.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Bill Run Service Implementation
 *
 * @author Harald Blikø
 */
@Service
public class BillRunServiceImpl extends GenericService<Long, BillRun> implements BillRunService {

    /**
     *
     */
    private final InvoiceServiceImpl invoiceService;



    /**
     *
     * @param repository
     * @param invoiceService
     */
    public BillRunServiceImpl(
            @Autowired BillRunRepository repository,
            @Autowired InvoiceServiceImpl invoiceService
    ) {
        super(repository);
        this.invoiceService = invoiceService;
    }



    /**
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        BillRun billRun = repository.getById(id);
        switch (billRun.getState()) {
            case NEW:
                repository.deleteById(id);
            case IN_PROGRESS:
                repository.save(billRun.cancel());
            case COMPLETED:
            case CANCELLED:
            case FAILED:
                throw new IllegalStateException("Cannot delete");
        }

    }

    /**
     *
     * @param current
     * @param prev
     * @param type
     */
    @Override
    public void publish(BillRun current, BillRun prev, CrudEventType type) {
        super.publish(current, prev, type);
    }

}
