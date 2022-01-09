package io.cmtr.crm.price.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;


/**
 *
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Entity
@DiscriminatorValue(ContractPrice.DISCRIMINATOR_VALUE)
public class ContractPrice extends AbstractPrice {

    public static final String DISCRIMINATOR_VALUE = "CONTRACT_PRICE";



    /**
     *
     */
    @NotNull
    @ManyToOne(
            optional = false,
            fetch = FetchType.EAGER
    )
    private AbstractPrice price;


    /**
     *
     */
    private BigDecimal overridePrice;



    /**
     *
     */
    private ZonedDateTime contractStart;



    /**
     *
     */
    private ZonedDateTime contractEnd;



    ///**** CONSTRUCTOR ****///



    /**
     *
     */
    protected ContractPrice() {
        super(DISCRIMINATOR_VALUE);
    }


    ///**** GETTERS ****///


    /**
     *
     * @param time
     * @return
     */
    public boolean isInContractPeriod(ZonedDateTime time) {
        return (time.isEqual(contractStart) || time.isAfter(contractStart))
                && time.isBefore(contractEnd);
    }



    /**
     *
     * @return
     */
    public boolean isInContractPeriod() {
        return isInContractPeriod(ZonedDateTime.now());
    }



    /**
     *
     * @return currency of reference price
     */
    @Override
    public @NotEmpty String getCurrency() {
        return this.price.getCurrency();
    }



    /**
     *
     * @return unit of reference price
     */
    @Override
    public String getUnit() {
        return this.price.getUnit();
    }



    ///**** SETTERS ****///


    /**
     *
     * @param source
     * @return
     */
    @Override
    public ContractPrice update(AbstractPrice source) {
        super.update(source);
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public ContractPrice createNewInstance() {
        return new ContractPrice();
    }



    ///**** FACTORY ****///



    ///**** HELPER METHODS ****///





}
