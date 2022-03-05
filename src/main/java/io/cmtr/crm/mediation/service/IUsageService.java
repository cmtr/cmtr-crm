package io.cmtr.crm.mediation.service;

import io.cmtr.crm.mediation.model.Usage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Usage Service Interface
 *
 * @author Harald Blikø
 */
public interface IUsageService {

    Page<Usage> getPage(Pageable pageable);

    Usage get(Long id);

    Usage create(Usage usage);

    void delete(Long id);

}
