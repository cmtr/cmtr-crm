package io.cmtr.crm.customer.dto;

import io.cmtr.crm.customer.config.CustomerProperties;
import io.cmtr.crm.customer.model.Customer;
import io.cmtr.crm.shared.generic.dto.EntityDtoValidator;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ValidationException;

@Component
public class CustomerValidator implements EntityDtoValidator<Customer> {

    private final CustomerProperties customerProperties;

    public CustomerValidator(
            @Autowired CustomerProperties customerProperties
    ) {
        this.customerProperties = customerProperties;
    }

    @Override
    public void validate(Customer entity) {
        validateCustomerAndContactType(
                entity,
                String.format(
                        "Customer type and customer contact type cannot be respectively '%s' and '%s'.",
                        entity.getType(),
                        entity.getCustomer().getType()
                )
        );
    }

    private <T> void throwIfNull(T val, String reason) {
        if(val == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, reason);
    }

    public boolean isValidCustomerAndContactType(Customer customer) {
        val customerType = customer.getType().toUpperCase();
        val contactType = customer.getCustomer().getType().toUpperCase();
        return customerProperties
                .getContact()
                .getTypeMapping()
                .stream()
                .anyMatch(e -> e.getSource().equals(customerType) && e.getTarget().equals(contactType));
    }

    private void validateCustomerAndContactType(Customer customer, String reason) {
        if (!isValidCustomerAndContactType(customer))
            throw new ValidationException(reason);
    }
}
