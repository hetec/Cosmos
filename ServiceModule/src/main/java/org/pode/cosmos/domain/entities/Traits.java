package org.pode.cosmos.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by patrick on 08.03.16.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Traits implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @XmlTransient
    @NotNull
    @Column(unique = true, updatable = false, nullable = false)
    private String uuid;

    private String title;

    private String description;

    @ManyToOne
    @XmlTransient
    private SocialContact contact;

    public Traits(){
        if(!Objects.nonNull(this.uuid)){
            this.uuid = UUID.randomUUID().toString();
        }
    }

    public Traits(String title, String description) {
        if(!Objects.nonNull(this.uuid)){
            this.uuid = UUID.randomUUID().toString();
        }
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SocialContact getContact() {
        return contact;
    }

    public void setContact(SocialContact contact) {
        this.contact = contact;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Standard implementation of equals
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Traits)) return false;

        Traits traits = (Traits) o;

        return uuid.equals(traits.uuid);

    }

    /**
     * Standard implementation of hashCode
     * @return
     */
    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return "Traits{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
