package io.cmtr.crm.shared.generic.service;

import io.cmtr.crm.shared.generic.event.CrudEventType;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

public interface IGenericService<R, T extends GenericEntity<R, T>> {

    Page<T> getPage(Pageable pageable);

    T get(R id);

    @Transactional
    T create(T newDomain);

    @Transactional
    T update(R id, T updated);

    T update(T updated);

    @Transactional
    void delete(R id);

    void publish(T current, T prev, CrudEventType type);
}
