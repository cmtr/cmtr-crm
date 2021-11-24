package io.cmtr.crm.shared.generic.dto;

/**
 *
 * @param <S> source entity class
 * @param <T> target
 */
public interface EntityTypeMap<S, T> {

    String get(String type);

}
