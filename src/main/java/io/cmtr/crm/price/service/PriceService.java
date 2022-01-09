package io.cmtr.crm.price.service;

import io.cmtr.crm.price.model.AbstractPrice;
import io.cmtr.crm.price.repository.PriceRepository;
import io.cmtr.crm.shared.generic.repository.GenericRepository;
import io.cmtr.crm.shared.generic.service.GenericService;
import org.springframework.stereotype.Service;

/**
 * Price Service
 *
 * @author Harald Blikø
 */
@Service
public class PriceService extends GenericService<Long, AbstractPrice> {

    public PriceService(PriceRepository repository) {
        super(repository);
    }
}
