package io.cmtr.crm.shared.contact.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Harald Blikø
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(BusinessContact.DISCRIMINATOR_VALUE)
public class BusinessContact extends AbstractContact {

    public static final String DISCRIMINATOR_VALUE = "BUSINESS";



    /**
     *
     */
    private String orgNr = "";



    /**
     *
     */
    private String legalName = "";



    /**
     *
     */
    private String countryCode = "";



    /**
     *
     */
    private String businessName = "";



    ///**** CONSTRUCTOR ****///



    protected BusinessContact() {
        super(DISCRIMINATOR_VALUE);
    }



    ///**** SETTERS ****///



    /**
     *
     * @param source
     * @return
     */
    @Override
    public AbstractContact update(AbstractContact source) {
        if (!(source instanceof BusinessContact))
            throw new RuntimeException("Incompatible type");
        super.update(source);
        if (source instanceof BusinessContact) {
            BusinessContact src = (BusinessContact) source;
            this
                .setOrgNr(src.getOrgNr())
                .setLegalName(src.getLegalName())
                .setCountryCode(src.getCountryCode())
                .setBusinessName(src.getBusinessName());
        }
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public AbstractContact createNewInstance() {
        return new BusinessContact()
                .setAddress(this.getAddress() == null ? null : this.getAddress().createNewInstance())
                .update(this);
    }



    ///**** FACTORIES ****///



    /**
     *
     * @param email
     * @param orgNr
     * @param legalName
     * @param countryCode
     * @param businessName
     * @param address
     * @return
     */
    public static BusinessContact factory(
        String email,
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
                .setAddress(address)
                .setEmail(email);
    }

}
