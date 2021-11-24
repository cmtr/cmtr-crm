package io.cmtr.crm.auth.model;

import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.util.UUID;


@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class User implements GenericEntity<UUID, User> {

    @Id
    private UUID id;

    @Override
    public User update(User source) {
        return this;
    }

    @Override
    public User createNewInstance() {
        return new User()
                .setId(UUID.randomUUID())
                .update(this);
    }

}
