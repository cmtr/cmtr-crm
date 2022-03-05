package io.cmtr.crm.mediation.model;

public interface IMediationUsageStrategy {

    void delete(Usage usage);

    Usage create(Usage usage);
}
