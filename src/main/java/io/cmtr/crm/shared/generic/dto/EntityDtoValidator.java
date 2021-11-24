package io.cmtr.crm.shared.generic.dto;

public interface EntityDtoValidator<T> {

    void validate(T entity);

}
