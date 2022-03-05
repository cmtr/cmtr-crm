package io.cmtr.crm.mediation.model;

import io.cmtr.crm.order.OrderTestUtil;
import io.cmtr.crm.order.model.Offer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Mediation Model test")
class MediationTest {

    @Nested
    @DisplayName("Given a Mediation")
    class A {

        // Setup
        Offer offer = OrderTestUtil.getOffer();
        String unit = "EA";

        @Test
        @DisplayName("when it is instantiated")
        void initiated() {
            // DTO
            Mediation mediation = Mediation
                    .factory(offer, unit);
            assertNull(mediation.getState());
            assertEquals(0, mediation.getUsages().size());
            // assertEquals(offer, mediation.getOffer());
            assertEquals(unit, mediation.getUnit());
            assertNull(mediation.getId());

            // Entity
            Mediation entity = mediation.createNewInstance();
            assertNotNull(entity.getId());
            assertEquals(Mediation.State.NEW, entity.getState());
            assertEquals(0, entity.getUsages().size());
            // assertEquals(offer, entity.getOffer());
            assertEquals(unit, entity.getUnit());

        }

    }

}