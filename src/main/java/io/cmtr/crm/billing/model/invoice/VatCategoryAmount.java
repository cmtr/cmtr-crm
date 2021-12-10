package io.cmtr.crm.billing.model.invoice;

import io.cmtr.crm.shared.billing.model.IAmount;
import io.cmtr.crm.shared.billing.model.IVatCategory;
import io.cmtr.crm.shared.billing.model.IVatCategoryAmount;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class VatCategoryAmount implements IVatCategoryAmount {

    @NotNull
    @PositiveOrZero
    @Max(100)
    private final BigDecimal rate;

    @NotEmpty
    private final String category;

    @NotEmpty
    private final String currency;

    private final BigDecimal netAmount;

    /**
     *
     * @return
     */
    @Override
    public BigDecimal getAmount() {
        return getVat();
    }

    public VatCategoryAmount add(BigDecimal netAmount) {
        return VatCategoryAmount.factory(this, this.netAmount.add(netAmount));
    }

    public VatCategoryAmount subtract(BigDecimal netAmount) {
        return VatCategoryAmount.factory(this, this.netAmount.subtract(netAmount));
    }

    public static VatCategoryAmount factory(IVatCategory vatCategory, BigDecimal netAmount) {
        return new VatCategoryAmount(
                vatCategory.getRate(),
                vatCategory.getCategory(),
                vatCategory.getCurrency(),
                netAmount
        );
    }
}
