package io.cmtr.crm.mediation.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
public abstract class DiscreteUsage extends Usage {

    protected DiscreteUsage(String type) {
        super(type);
    }
}
