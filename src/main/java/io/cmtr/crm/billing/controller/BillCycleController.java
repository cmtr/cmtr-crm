package io.cmtr.crm.billing.controller;

import io.cmtr.crm.billing.model.billcycle.BillCycle;
import io.cmtr.crm.shared.generic.controller.GenericRestController;
import io.cmtr.crm.shared.generic.service.GenericService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/billing/cycles")
public class BillCycleController extends GenericRestController<UUID, BillCycle> {

    public BillCycleController(GenericService<UUID, BillCycle> service) {
        super(service);
    }

}
