package io.cmtr.crm.mediation.service;

import io.cmtr.crm.mediation.model.IMediationUsageStrategy;
import io.cmtr.crm.mediation.model.Usage;
import io.cmtr.crm.mediation.repository.IMediationRepository;
import io.cmtr.crm.mediation.repository.IUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

/**
 * Usage Service Implementation
 *
 * @author Harald Blikø
 */
@Service
public class UsageService implements IUsageService {

    private final IUsageRepository usageRepository;
    private final IMediationUsageStrategy mediationUsageStrategy;

    public UsageService(
            @Autowired IUsageRepository usageRepository,
            @Autowired IMediationUsageStrategy mediationUsageStrategy
    ) {
        this.usageRepository = usageRepository;
        this.mediationUsageStrategy = mediationUsageStrategy;
    }

    @Override
    public Page<Usage> getPage(Pageable pageable) {
        return usageRepository.findAll(pageable);
    }

    @Override
    public Usage get(Long id) {
        return usageRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity with id '%s'not found", id)));
    }

    @Override
    public Usage create(Usage usage) {
        return usageRepository.save(mediationUsageStrategy.create(usage));
    }

    @Override
    public void delete(Long id) {
        Usage dbDomain = usageRepository.getById(id);
        mediationUsageStrategy.delete(dbDomain);
        usageRepository.save(dbDomain);
    }
}
