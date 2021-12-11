package io.cmtr.crm.billing.model.invoice;

import io.cmtr.crm.shared.billing.model.IVatCategory;
import io.cmtr.crm.shared.generic.model.GenericEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
public class VatCategory implements GenericEntity<Long, VatCategory>, IVatCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @PositiveOrZero
    @Max(100)
    private BigDecimal rate;

    @NotEmpty
    private String category;

    @NotEmpty
    private String currency;

    // private ZonedDateTime validFrom;
    // private ZonedDateTime validTo;

    ///*** SETTERS ***///

    @Override
    public VatCategory update(VatCategory source) {
        return this
                .setRate(source.getRate())
                .setCategory(source.getCategory())
                .setCurrency(source.currency);
    }

    @Override
    public VatCategory createNewInstance() {
        return new VatCategory()
        // TODO set state
                .update(this);
    }

    ///**** STATIC RESOURCES ****///

    public static VatCategory factory(
            BigDecimal rate,
            String category,
            String currency
    ) {
        return new VatCategory()
                .setRate(rate)
                .setCategory(category)
                .setCurrency(currency);
    }
}
