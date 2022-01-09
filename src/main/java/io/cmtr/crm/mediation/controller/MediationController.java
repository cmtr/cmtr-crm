package io.cmtr.crm.mediation.controller;

import io.cmtr.crm.mediation.model.Mediation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MediationController {

    @GetMapping("/{id}")
    public ResponseEntity<Mediation> getOne() {
        return null;
    }

}
