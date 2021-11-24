package io.cmtr.crm.shared.generic.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public abstract class GenericCrudEvent<T> extends ApplicationEvent {

    public static final String DEFAULT_MESSAGE = "Cred Event";

    final T previous;
    final String message;
    final UUID id;
    final CrudEventType type;

    public GenericCrudEvent(T current, T previous, CrudEventType type, String message, UUID id) {
        super(current);
        this.previous = previous;
        this.type = type;
        this.message = message;
        this.id = id;
    }

    public GenericCrudEvent(T current, T previous, CrudEventType type, String message) {
        this(current, previous, type, message, UUID.randomUUID());
    }

    public GenericCrudEvent(T current, T previous, CrudEventType type) {
        this(current, previous, type, DEFAULT_MESSAGE, UUID.randomUUID());
    }

    public GenericCrudEvent(T current, CrudEventType type, String message) {
        this(current, null, type, message, UUID.randomUUID());
    }

    public GenericCrudEvent(T current, CrudEventType type) {
        this(current, null, type, DEFAULT_MESSAGE, UUID.randomUUID());
    }

    public T getCurrent() {
        return (T) super.getSource();
    }

}
