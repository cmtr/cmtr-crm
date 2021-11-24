package io.cmtr.crm.shared.contact.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter(AccessLevel.PRIVATE)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(BusinessContact.DISCRIMINATOR_VALUE)
public class BusinessContact extends AbstractContact {

    public static final String DISCRIMINATOR_VALUE = "BUSINESS";

    private BusinessContact() {
        super(DISCRIMINATOR_VALUE);
    }

    @Override
    protected AbstractContact setFirstName(String firstName) {
        return this;
    }

    @Override
    protected AbstractContact setLastName(String lastName) {
        return this;
    }

    @Override
    public AbstractContact update(AbstractContact source) {
        if (!(source instanceof BusinessContact))
            throw new RuntimeException("Incompatible type");
        super.update(source);
        return this
                .setOrgNr(source.getOrgNr())
                .setLegalName(source.getLegalName())
                .setCountryCode(source.getCountryCode())
                .setBusinessName(source.getBusinessName());
    }

    @Override
    public AbstractContact createNewInstance() {
        return new BusinessContact()
                .setAddress(this.getAddress() == null ? null : this.getAddress().createNewInstance())
                .update(this);
    }

    public static BusinessContact factory(
        String orgNr,
        String legalName,
        String countryCode,
        String businessName,
        AbstractAddress address
    ) {
        return (BusinessContact) new BusinessContact()
                .setOrgNr(orgNr)
                .setLegalName(legalName)
                .setCountryCode(countryCode)
                .setBusinessName(businessName)
                .setAddress(address);
    }

}
