package io.cmtr.crm.billing.model.invoice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreditNote extends Invoice {

    private List<Invoice> invoices;

}
