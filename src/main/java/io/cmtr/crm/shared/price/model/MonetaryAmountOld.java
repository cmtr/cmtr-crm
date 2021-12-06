package io.cmtr.crm.shared.price.model;

import lombok.Data;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@Embeddable
public class MonetaryAmountOld implements IMonetaryAmountOld {

    public static final int PRECISION = 2;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    private final BigDecimal amount;
    private final String currency;

    public MonetaryAmountOld(BigDecimal amount, String currency) {
        this.amount = amount.setScale(PRECISION, ROUNDING_MODE);
        this.currency = currency;
    }

    public MonetaryAmountOld(String amount, String currency) {
        this(new BigDecimal(amount), currency);
    }

    public MonetaryAmountOld(double amount, String currency) {
        this(BigDecimal.valueOf(amount), currency);
    }

}
