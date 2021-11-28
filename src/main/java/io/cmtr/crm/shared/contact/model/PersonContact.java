package io.cmtr.crm.shared.contact.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(PersonContact.DISCRIMINATOR_VALUE)
public class PersonContact extends AbstractContact implements Cloneable {

    public static final String DISCRIMINATOR_VALUE = "PERSON";

    // Person
    private String firstName = "";
    private String lastName = "";
    // private Date dateOfBirth;

    private PersonContact() {
        super(DISCRIMINATOR_VALUE);
    }

    @Override
    public AbstractContact update(AbstractContact source) {
        if (!(source instanceof PersonContact))
            throw new RuntimeException("Incompatible type");
        super.update(source);
        PersonContact src = (PersonContact) source;
        return this
                .setFirstName(src.getFirstName())
                .setLastName(src.getLastName());
    }

    @Override
    public AbstractContact createNewInstance() {
        return new PersonContact()
                .setAddress(this.getAddress() == null ? null : this.getAddress().createNewInstance())
                .update(this);
    }

    public static PersonContact factory(
        String firstName,
        String lastName,
        AbstractAddress address
    ) {
        return (PersonContact) new PersonContact()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setAddress(address);
    }

}
