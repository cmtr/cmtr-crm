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

/**
 * VAT Category
 *
 * Value Added Tax Category Entity
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
public class VatCategory implements GenericEntity<Long, VatCategory>, IVatCategory {



    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;



    /**
     * VAT Rate as Percentage between 0 and 100
     */
    @NotNull
    @PositiveOrZero
    @Max(100)
    private BigDecimal rate;



    /**
     * VAT Category Code
     */
    @NotEmpty
    private String category;



    /**
     * Currency applicable for the VAT Category
     *
     * Introduced as a validation that a VAT Category is applicable for a single currency
     *
     */
    @NotEmpty
    private String currency;

    // private ZonedDateTime validFrom;
    // private ZonedDateTime validTo;



    ///*** SETTERS ***///



    /**
     *
     * @param source
     * @return
     */
    @Override
    public VatCategory update(VatCategory source) {
        return this
                .setRate(source.getRate())
                .setCategory(source.getCategory())
                .setCurrency(source.currency);
    }



    /**
     *
     * @return
     */
    @Override
    public VatCategory createNewInstance() {
        return new VatCategory()
        // TODO set state
                .update(this);
    }



    ///**** STATIC RESOURCES ****///





    ///**** FACTORIES ****///



    /**
     *
     * @param rate
     * @param category
     * @param currency
     * @return
     */
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
