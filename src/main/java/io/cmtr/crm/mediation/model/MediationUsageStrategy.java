package io.cmtr.crm.mediation.model;

import io.cmtr.crm.mediation.service.IMediationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mediation Usage Strategy Implementation
 *
 * @author Harald Blikø
 */
@Component
public class MediationUsageStrategy implements IMediationUsageStrategy {

    private final IMediationService mediationService;

    public MediationUsageStrategy(
            @Autowired IMediationService mediationService
    ) {
        this.mediationService = mediationService;
    }

    @Override
    public void delete(Usage usage) {
        // TODO
        if (false) {
            throw new IllegalStateException("Cannot delete");
        }
    }

    @Override
    public Usage create(Usage usage) {
        return null;
    }
}
