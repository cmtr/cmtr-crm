package io.cmtr.crm.billing.model.billcycle;

import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Accessors(chain = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "bill_cycle_type")
public abstract class BillCycle implements GenericEntity<UUID, BillCycle> {

    @Id
    private UUID id;

    @NotEmpty
    @Column(
            unique = true
    )
    private String name;

    @NotEmpty
    @Column(name = "bill_cycle_type")
    private String type;

    private boolean locked;

    private List<BillRun> billRuns;

    @ElementCollection
    @CollectionTable(
            name = "bill_cycle_parameters",
            joinColumns = { @JoinColumn(name = "bill_cycle_id", referencedColumnName = "id")}
    )
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> parameters;


    public BillCycle(@NotEmpty String type) {
        this.type = type;
    }

    @Override
    public BillCycle createNewInstance() {
        this.setId(UUID.randomUUID());
        return this;
    }
}
