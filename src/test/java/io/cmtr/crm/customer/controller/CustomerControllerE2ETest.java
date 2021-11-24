package io.cmtr.crm.customer.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CustomerControllerE2ETest {

    @Autowired
    private CustomerController controller;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void contextLoads() {
        assertNotNull(controller);
    }

    @Test
    public void post(@Value("${./data/CystomerPayload.json}") String payload) {
        System.out.println(payload);
        assertNotNull(null);
    }
}