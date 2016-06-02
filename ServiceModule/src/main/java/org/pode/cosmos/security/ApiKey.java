package org.pode.cosmos.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Represents the api key
 */
@Named
@RequestScoped
public class ApiKey {

    private static final String API_KEY_FILE = "apiKey.properties";
    private static final String KEY = "key";

    private static String secret = null;

    /**
     * Loads the secret api key from the configuration file
     *
     * @return Api key
     * @throws IllegalStateException if the api file cannot be loaded
     */
    private void loadSecret() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(API_KEY_FILE)){
            Properties keyProperties = new Properties();
            keyProperties.load(inputStream);
            secret = keyProperties.getProperty(KEY);
        }catch (final IOException ioEx){
            throw new IllegalStateException("Cannot load api key from " + API_KEY_FILE);
        }
    }

    /**
     * Provides the secret api key
     *
     * @return Api key
     */
    public String getSecret(){
        if(secret == null){
            this.loadSecret();
        }
        return secret;
    }
}
