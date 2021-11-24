package io.cmtr.crm.shared.generic.dto;

import lombok.Data;

@Data
public class GenericRelationship<S, T> {

    private S source;
    private T target;
    private boolean directional;

}
