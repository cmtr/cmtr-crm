package io.cmtr.crm.shared.generic.service;

import io.cmtr.crm.shared.generic.event.CrudEventType;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import io.cmtr.crm.shared.generic.repository.GenericRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;


/**
 * Abstract Generic Service
 *
 * @param <R> identifier type
 * @param <T> entity class
 *
 *
 * @author Harald Blikø
 */
public abstract class GenericService<R, T extends GenericEntity<R, T>> implements IGenericService<R, T> {



    /**
     *
     */
    protected final GenericRepository<R, T> repository;



    ///**** CONSTRUCTORS ****///



    /**
     *
     * @param repository
     */
    public GenericService(GenericRepository<R, T> repository) {
        this.repository = repository;
    }



    ///**** GETTERS ****///



    /**
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<T> getPage(Pageable pageable){
        return repository.findAll(pageable);
    }



    /**
     *
     * @param id
     * @return
     */
    @Override
    public T get(R id){
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Entity with id '%s'not found", id)));
    }



    ///**** SETTERS ****///



    /**
     *
     * @param newDomain
     * @return
     */
    @Override
    @Transactional
    public T create(T newDomain){
        T dbDomain = newDomain.createNewInstance();
        publish(dbDomain, null, CrudEventType.CREATE);
        return repository
                .save(dbDomain);
    }



    /**
     *
     * @param id
     * @param updated
     * @return
     */
    @Override
    @Transactional
    public T update(R id, T updated){
        T dbDomain = get(id);
        T previous = SerializationUtils.clone(dbDomain);
        dbDomain.update(updated);
        publish(dbDomain, previous, CrudEventType.UPDATE);
        return repository
                .save(dbDomain);
    }



    /**
     *
     * @param updated
     * @return
     */
    @Override
    public T update(T updated){
        return update(updated.getId(), updated);
    }



    /**
     *
     * @param id
     */
    @Override
    @Transactional
    public void delete(R id){
        //check if object with this id exists
        T dbDomain = get(id);
        publish(null, dbDomain, CrudEventType.DELETE);
        repository
                .deleteById(id);
    }



    /**
     *
     * @param current
     * @param prev
     * @param type
     */
    protected void publish(T current, T prev, CrudEventType type) {

    }


}
