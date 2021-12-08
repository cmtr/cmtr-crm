package io.cmtr.crm.mediation.model;

import io.cmtr.crm.shared.generic.model.GenericEntity;
import io.cmtr.crm.shared.mediation.model.IUsage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class Usage implements IUsage, GenericEntity<Long, Usage> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(
        name = "usage_type",
        nullable = false,
        updatable = false
    )
    private String type;

    @NotNull
    private Mediation mediation;

    @NotEmpty
    private String unit;

    protected Usage(String type) {
        this.type = type;
    }

}
