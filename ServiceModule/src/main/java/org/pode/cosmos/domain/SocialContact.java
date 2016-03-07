package org.pode.cosmos.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by patrick on 19.02.16.
 */
@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name = "socialContact.findAll",
                    query = "SELECT sc FROM SocialContact sc")
})
public class SocialContact implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthdate;

    public SocialContact(){}

    public SocialContact(String firstName, String lastName, Date birthdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        //Address
        //Traits
        //Type
        //relations
        //notes
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
