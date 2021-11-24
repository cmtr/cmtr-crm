package io.cmtr.crm.shared.contact.model;

import javax.persistence.*;
/*
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "phone_type",
        discriminatorType = DiscriminatorType.STRING
)
*/
abstract class AbstractPhone {

    @Column(
            name = "phone_type",
            nullable = false,
            updatable = true,
            insertable = true
    )
    private String type;
    private Long phoneNr;
    private int countryCode;

    public AbstractPhone(String type) {
        this.type = type;
    }
}
