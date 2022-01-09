package io.cmtr.crm.price.repository;

import io.cmtr.crm.price.model.AbstractPrice;
import io.cmtr.crm.shared.generic.repository.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends GenericRepository<Long, AbstractPrice> {


}
