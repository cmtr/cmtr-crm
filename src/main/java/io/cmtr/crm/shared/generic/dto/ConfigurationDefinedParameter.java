package io.cmtr.crm.shared.generic.dto;

import lombok.Data;

import javax.validation.ValidationException;
import java.util.regex.Pattern;

@Data
public class ConfigurationDefinedParameter {

    private String key;
    private boolean required = false;
    private String validation;
    private String message = "Validation error";

    public boolean isValid(String value) {
        return Pattern.compile(validation).asMatchPredicate().test(value);
    }

    public void  validate(String value) {
        if (!isValid(value))
            throw new ValidationException(message);
    }

}
