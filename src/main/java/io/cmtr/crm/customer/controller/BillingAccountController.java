package io.cmtr.crm.customer.controller;

import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.service.BillingAccountService;
import io.cmtr.crm.shared.generic.controller.GenericRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/billing/account")
public class BillingAccountController extends GenericRestController<UUID, BillingAccount> {

    public BillingAccountController(BillingAccountService repository) {
        super(repository);
    }
}
