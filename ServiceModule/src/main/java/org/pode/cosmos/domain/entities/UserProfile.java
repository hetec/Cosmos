package org.pode.cosmos.domain.entities;

import org.pode.cosmos.domain.auth.Credentials;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Holds the users credentials
 *
 * @author Patrick Hebner
 */
@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(
                name = "userCredentials.findByUserName",
                query = "SELECT u FROM UserProfile u WHERE u.username = :username")
})
public class UserProfile implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
//    @Pattern(regexp = "[a-zA-Z]{5,20}")
    private String username;

//    @NotNull
    private String password;

    @Column(length = 50, nullable = false)
//    @NotNull
//    @Size(min = 6)
    private String email;

    public UserProfile(){
    }

    /**
     * Convenience constructor for creating a new profile during a registration process
     *
     * @param credentials Data necessary for registering a new user
     */
    public UserProfile(Credentials credentials){
        this.username = credentials.getUsername();
        this.password = credentials.getPassword();
        this.email = credentials.getEmail();
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
