package io.cmtr.crm.price.model;

import io.cmtr.crm.customer.model.Supplier;
import javassist.NotFoundException;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.crossstore.ChangeSetPersister;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Unit Price
 *
 *
 * @author Harald Blikø
 *
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Entity
@Accessors(chain = true)
@DiscriminatorValue(UnitPrice.DISCRIMINATOR_VALUE)
public class UnitPrice extends AbstractPrice {

    public static final String DISCRIMINATOR_VALUE = "UNIT_PRICE";



    /**
     *
     */
    @Getter(AccessLevel.PROTECTED)
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<UnitPriceInstance> unitPriceInstances = new LinkedList<>();



    ///**** CONSTRUCTORS ****∕∕∕



    /**
     *
     * @param type
     */
    protected  UnitPrice(String type) {
        super(type);
    }



    /**
     *
     */
    protected UnitPrice() {
        this(DISCRIMINATOR_VALUE);
    }



    ///**** GETTER ****///


    /**
     *
     * @param time
     * @return
     */
    public BigDecimal getUnitPrice(ZonedDateTime time) {
        return this.unitPriceInstances
                .stream()
                .filter(e -> e.getValidFrom().isBefore(time) || e.getValidFrom().isEqual(time))
                .findFirst()
                .orElseThrow(NullPointerException::new)
                .getUnitPrice();
    }



    ///**** SETTERS ****///



    /**
     *
     * @param unitPrice
     * @param validFrom
     * @return
     */
    public UnitPrice saveUnitPriceInstance(ZonedDateTime validFrom, BigDecimal unitPrice) {
        // Check that the unit price validFrom date is unique.
        if (!isUniquePrinceInstanceDate(validFrom))
            throw new IllegalArgumentException("Unit Price Instance validFrom date must be unique.");
        // TODO - add code to modify
        val instance = UnitPriceInstance.factory(this, validFrom, unitPrice);
        this.unitPriceInstances.add(instance);
        return this;
    }



    /**
     *
     * @param unitPrice
     * @param validFrom
     * @return
     */
    public UnitPrice removeUnitPriceInstance(ZonedDateTime validFrom, BigDecimal unitPrice) {
        if (validFrom.isBefore(ZonedDateTime.now()))
            throw new IllegalArgumentException("Cannot remove Unit Price Instance with past valid from date.");
        this.unitPriceInstances = this.unitPriceInstances
                .stream()
                .filter(e -> !(e.getValidFrom().equals(validFrom) && e.getUnitPrice().equals(unitPrice)))
                .collect(Collectors.toList());
        return this;
    }



    /**
     *
     * @param source
     * @return
     */
    @Override
    public UnitPrice update(AbstractPrice source) {
        super.update(source);
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public UnitPrice createNewInstance() {
        return (UnitPrice) new UnitPrice()
                .setSupplier(this.getSupplier())
                .setState(State.NEW)
                .update(this);
    }



    ///**** HELPER METHODS ****////



    /**
     *
     * @param validFrom
     * @return
     */
    private boolean isUniquePrinceInstanceDate(ZonedDateTime validFrom) {
        return !this.unitPriceInstances
                .stream()
                .map(UnitPriceInstance::getValidFrom)
                .anyMatch(Predicate.isEqual(validFrom));
    }



    ///**** FACTORIES ****///



    public static UnitPrice factory(
            Supplier supplier,
            String currency,
            String unit
    ) {
        UnitPrice unitPrice = new UnitPrice();
        unitPrice
                .setSupplier(supplier)
                .setCurrency(currency)
                .setUnit(unit);
        return unitPrice;
    }
}

