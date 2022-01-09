package io.cmtr.crm.shared.contact.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Person Contact
 *
 * @author Harald Blikø
 */
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(PersonContact.DISCRIMINATOR_VALUE)
public class PersonContact extends AbstractContact implements Cloneable {

    public static final String DISCRIMINATOR_VALUE = "PERSON";



    /**
     *
     */
    private String firstName = "";


    /**
     *
     */
    private String lastName = "";



    // private Date dateOfBirth;



    ///**** CONSTRUCTOR ****///



    protected PersonContact() {
        super(DISCRIMINATOR_VALUE);
    }



    ///**** SETTERS ****///



    /**
     *
     * @param source
     * @return
     */
    @Override
    public PersonContact update(AbstractContact source) {
        if (!(source instanceof PersonContact))
            throw new RuntimeException("Incompatible type");
        super.update(source);
        if (source instanceof PersonContact) {
            PersonContact src = (PersonContact) source;
            this
                .setFirstName(src.getFirstName())
                .setLastName(src.getLastName());
        }
        return this;
    }



    /**
     *
     * @return
     */
    @Override
    public AbstractContact createNewInstance() {
        return new PersonContact()
                .setAddress(this.getAddress() == null ? null : this.getAddress().createNewInstance())
                .update(this);
    }



    ///**** FACTORIES ****///



    /**
     *
     * @param email
     * @param firstName
     * @param lastName
     * @param address
     * @return
     */
    public static PersonContact factory(
        String email,
        String firstName,
        String lastName,
        AbstractAddress address
    ) {
        return (PersonContact) new PersonContact()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setAddress(address)
                .setEmail(email);
    }

}
