package io.cmtr.crm.shared.contact;

import io.cmtr.crm.shared.contact.model.*;

public class ContactTestUtil {

    public static PersonContact getPersonContact() {
        return (PersonContact) PersonContact
                .factory(
                        "John@doe.com",
                        "John",
                        "Doe",
                        getPostboxAddress()
                )
                .createNewInstance();
    }

    public static BusinessContact getBusinessContact() {
        return (BusinessContact) BusinessContact
                .factory(
                        "contact@company.no",
                        "1234",
                        "Company",
                        "NOR",
                        "Company",
                        getStreetAddress()
                )
                .createNewInstance();
    }

    public static StreetAddress getStreetAddress() {
        return (StreetAddress) StreetAddress
                .factory(
                        "NOR",
                        "1234",
                        "Oslo",
                        "Street",
                        "123"
                )
                .createNewInstance();
    }

    public static PostboxAddress getPostboxAddress() {
        return (PostboxAddress) PostboxAddress
                .factory(
                        "NOR",
                        "1234",
                        "Oslo",
                        "100"
                )
                .createNewInstance();
    }
}
