package io.cmtr.crm.shared.generic.model;

import java.io.Serializable;

public interface GenericEntity<R, T> extends Serializable {

        // update current instance with provided data
        T update(T source);

        R getId();

        // based on current data create new instance with new id
        T createNewInstance();

}
