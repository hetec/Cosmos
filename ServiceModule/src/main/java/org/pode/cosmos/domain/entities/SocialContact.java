package org.pode.cosmos.domain.entities;


import org.pode.cosmos.domain.utils.jaxbAdapter.LocalDateAdapter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

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
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate birthday;

    public SocialContact(){}

    public SocialContact(String firstName, String lastName, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "SocialContact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate=" + birthday +
                '}';
    }
}
