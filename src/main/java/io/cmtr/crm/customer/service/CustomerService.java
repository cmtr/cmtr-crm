package io.cmtr.crm.customer.service;

import io.cmtr.crm.customer.event.CustomerEvent;
import io.cmtr.crm.customer.model.Customer;
import io.cmtr.crm.customer.repository.CustomerRepository;
import io.cmtr.crm.shared.generic.event.CrudEventType;
import io.cmtr.crm.shared.generic.service.GenericService;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 *
 * @author Harald Blikø
 */
@Service
public class CustomerService extends GenericService<UUID, Customer> {

    private final ApplicationEventPublisher applicationEventPublisher;


    /**
     *
     * @param repository
     * @param applicationEventPublisher
     */
    public CustomerService(
            @Autowired CustomerRepository repository,
            @Autowired ApplicationEventPublisher applicationEventPublisher
    ) {
        super(repository);
        this.applicationEventPublisher = applicationEventPublisher;
    }


    /**
     *
     * @param id
     * @param barred
     * @return
     */
    @Transactional
    public Customer bar(UUID id, boolean barred) {
        Customer dbDomain = get(id);
        Customer previous = SerializationUtils.clone(dbDomain);
        dbDomain.setBarred(barred);
        publish(dbDomain, previous, CrudEventType.UPDATE);
        return repository
                .save(dbDomain);
    }



    /**
     *
     * @param current
     * @param prev
     * @param type
     */
    @Override
    public void publish(Customer current, Customer prev, CrudEventType type) {
        super.publish(current, prev, type);
        applicationEventPublisher.publishEvent(new CustomerEvent(current, prev, type));
    }


}
