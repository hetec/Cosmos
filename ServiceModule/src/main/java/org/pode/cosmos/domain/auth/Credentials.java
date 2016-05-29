package org.pode.cosmos.domain.auth;

import org.pode.cosmos.domain.validators.Email;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by patrick on 02.04.16.
 *
 * Provides information necessary to register a user to the application
 */
public class Credentials {

    @NotNull
    private String username;

    @Pattern(regexp = "[a-zA-Z]{5,20}")
    @NotNull
    private String password;

    @NotNull
    @Size(min = 6, message = "OH OH this email seems to be wrong")
    private String email;

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
