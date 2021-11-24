package io.cmtr.crm.customer.controller;

import io.cmtr.crm.customer.model.Customer;
import io.cmtr.crm.customer.service.CustomerService;
import io.cmtr.crm.shared.generic.controller.GenericRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/customer")
public class CustomerController extends GenericRestController<UUID, Customer> {


    public CustomerController(CustomerService service) {
        super(service);
    }
}
