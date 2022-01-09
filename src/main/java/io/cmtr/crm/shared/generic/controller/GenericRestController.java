package io.cmtr.crm.shared.generic.controller;

import io.cmtr.crm.shared.generic.model.GenericEntity;
import io.cmtr.crm.shared.generic.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.function.Supplier;

/**
 *
 * @param <R>
 * @param <T>
 */
public abstract class GenericRestController<R, T extends GenericEntity<R,T>> {

    private final GenericService<R, T> service;

    public GenericRestController(GenericService<R, T> service) {
        this.service = service;
    }



    /**
     *
     * @param pageable
     * @return
     */
    @GetMapping("")
    public ResponseEntity<Page<T>> getPage(Pageable pageable){
        return handleException(() -> ResponseEntity.ok(service.getPage(pageable)));
    }



    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<T> getOne(@PathVariable R id) {
        return handleException(() -> ResponseEntity.ok(service.get(id)));

    }



    /**
     *
     * @param created
     * @return
     */
    @PostMapping("")
    public ResponseEntity<T> create(@RequestBody T created) {
        return handleException(() -> ResponseEntity.ok(service.create(created)));
    }



    /**
     *
     * @param id
     * @param updated
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<T> update(
            @PathVariable R id,
            @RequestBody T updated
    ) {
        return handleException(() -> ResponseEntity.ok(service.update(id, updated)));
    }



    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable R id) {
        return handleException(() -> {
            service.delete(id);
            return ResponseEntity.ok("Ok");
        });
    }



    /**
     *
     * @param supplier
     * @param <T>
     * @return
     */
    public static <T> T handleException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (IllegalStateException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }


}
