package io.cmtr.crm.shared.generic.service;

import io.cmtr.crm.shared.generic.event.CrudEventType;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import io.cmtr.crm.shared.generic.repository.GenericRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;


public abstract class GenericService<R, T extends GenericEntity<R, T>> {

    protected final GenericRepository<R, T> repository;

    public GenericService(GenericRepository<R, T> repository) {
        this.repository = repository;
    }

    public Page<T> getPage(Pageable pageable){
        return repository.findAll(pageable);
    }

    public T get(R id){
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Entity with id '%s'not found", id)));
    }

    @Transactional
    public T create(T newDomain){
        T dbDomain = newDomain.createNewInstance();
        publish(dbDomain, null, CrudEventType.CREATE);
        return repository
                .save(dbDomain);
    }

    @Transactional
    public T update(R id, T updated){
        T dbDomain = get(id);
        T previous = SerializationUtils.clone(dbDomain);
        dbDomain.update(updated);
        publish(dbDomain, previous, CrudEventType.UPDATE);
        return repository
                .save(dbDomain);
    }

    public T update(T updated){
        return update(updated.getId(), updated);
    }

    @Transactional
    public void delete(R id){
        //check if object with this id exists
        T dbDomain = get(id);
        publish(null, dbDomain, CrudEventType.DELETE);
        repository
                .deleteById(id);
    }

    protected void publish(T current, T prev, CrudEventType type) {

    }

}
