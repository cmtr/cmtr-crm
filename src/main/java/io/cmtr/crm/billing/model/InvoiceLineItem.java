package io.cmtr.crm.billing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@Entity
public class InvoiceLineItem implements GenericEntity<Long, InvoiceLineItem> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(
            optional = false,
            targetEntity = Invoice.class,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private Invoice invoice;

    @JsonInclude
    public Long getInvoiceId() {
        return invoice.getId();
    }

    @Override
    public InvoiceLineItem update(InvoiceLineItem source) {
        return null;
    }

    @Override
    public InvoiceLineItem createNewInstance() {
        return null;
    }
}
