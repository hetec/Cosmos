package org.pode.cosmos.domain.entities;


import org.pode.cosmos.domain.utils.jaxbAdapter.LocalDateAdapter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "contact")
    private List<Traits> traits;

    public SocialContact(){}

    public SocialContact(String firstName, String lastName, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    /**
     * Establishes the n to 1 relationship between the social contact and
     * the trait by setting the related properties.
     *
     * @param trait The trait object which will be added to the social contact
     * @return trait
     */
    public Traits addTrait(Traits trait){
        if(Objects.isNull(trait)){
            throw new NullPointerException("Trait must not be null");
        }
        //Initialize with empty list if null
        if(Objects.isNull(traits)){
            this.traits = new ArrayList<>();
        }
        trait.setContact(this);
        this.traits.add(trait);
        return trait;
    }

    /**
     * Dissolves the n to 1 relationship between the social contact and
     * the trait by setting the related property to null or removing it from the list.
     *
     * @param trait The trait object which will be removed from the social contact
     * @return trait
     */
    public Traits removeTrait(Traits trait){
        if(Objects.isNull(trait)){
            throw new NullPointerException("Trait must not be null");
        }
        if(Objects.nonNull(this.traits)){
            this.traits.remove(trait);
        }
        trait.setContact(null);
        return trait;
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

    public List<Traits> getTraits() {
        return traits;
    }

    public void setTraits(List<Traits> traits) {
        this.traits = traits;
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
