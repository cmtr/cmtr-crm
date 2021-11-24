package io.cmtr.crm.location.model;

import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
@Accessors(chain = true)
public class Address implements GenericEntity<UUID, Address> {

    @Id
    private final UUID id;
    private String addressType;


    public Address(UUID id) {
        this.id = id;
    }

    @Override
    public Address update(Address source) {
        return this;
    }

    @Override
    public Address createNewInstance() {
        return new Address(UUID.randomUUID());
    }

}
