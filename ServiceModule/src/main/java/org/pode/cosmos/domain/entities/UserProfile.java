package org.pode.cosmos.domain.entities;

import org.pode.cosmos.domain.auth.RegistrationData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.registry.infomodel.User;
import java.io.Serializable;

/**
 * Created by patrick on 02.04.16.
 */
@Entity
@XmlRootElement
public class UserProfile implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    public UserProfile(){
    }

    /**
     * Convenience constructor for creating a new profile during a registration process
     *
     * @param registrationData Data necessary for registering a new user
     */
    public UserProfile(RegistrationData registrationData){
        this.username = registrationData.getUsername();
        this.password = registrationData.getPassword();
        this.email = registrationData.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
