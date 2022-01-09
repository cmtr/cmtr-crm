package io.cmtr.crm.price.controller;

import io.cmtr.crm.price.model.AbstractPrice;
import io.cmtr.crm.price.service.PriceService;
import io.cmtr.crm.shared.generic.controller.GenericRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/price")
public class PriceController extends GenericRestController<Long, AbstractPrice> {

    public PriceController(PriceService service) {
        super(service);
    }

}
