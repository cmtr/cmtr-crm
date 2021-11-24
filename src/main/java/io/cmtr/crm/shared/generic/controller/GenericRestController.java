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

public abstract class GenericRestController<R, T extends GenericEntity<R,T>> {

    private final GenericService<R, T> service;

    public GenericRestController(GenericService<R, T> service) {
        this.service = service;
    }
    
    @GetMapping("")
    public ResponseEntity<Page<T>> getPage(Pageable pageable){
        return ResponseEntity.ok(service.getPage(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getOne(@PathVariable R id) {
        try {
            return ResponseEntity.ok(service.get(id));
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PostMapping("")
    public ResponseEntity<T> create(@RequestBody T created) {
        // try {
            return ResponseEntity.ok(service.create(created));
        // }
        /*
        catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } */
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(
            @PathVariable R id,
            @RequestBody T updated
    ) {
        try {
            return ResponseEntity.ok(service.update(id, updated));
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } /*
        catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } */
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable R id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Ok");
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

}
