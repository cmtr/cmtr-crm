package io.cmtr.crm.shared.generic.dto;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.Getter;

import java.io.IOException;

public abstract class GenericDeserializer<T> extends StdDeserializer<T> {

    @Getter
    private final EntityDtoValidator<T> validator;

    public GenericDeserializer(EntityDtoValidator<T> validator) {
        this(null, validator);
    }

    public GenericDeserializer(Class<?> vc, EntityDtoValidator<T> validator) {
        super(vc);
        this.validator = validator;
    }

    @Override
    public T deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jp);
        T entity = getEntity(node);
        validator.validate(entity);
        return entity;
    }

    protected abstract T getEntity(JsonNode node) throws IOException;

    public static <T> T deserializeSubtree(String root, StdDeserializer<T> deserializer) throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(root);
        return deserializer.deserialize(parser, null);
    }
}
