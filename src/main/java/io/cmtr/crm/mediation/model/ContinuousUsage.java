package io.cmtr.crm.mediation.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
public abstract class ContinuousUsage extends Usage {

    protected ContinuousUsage(String type) {
        super(type);
    }
}
