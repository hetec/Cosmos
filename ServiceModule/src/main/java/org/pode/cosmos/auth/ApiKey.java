package org.pode.cosmos.auth;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.Signature;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by patrick on 18.04.16.
 */
public class ApiKey {

    private String key;

    public String getSecret() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("apiKey.properties");
        Properties keyProperties = new Properties();
        keyProperties.load(inputStream);
        return keyProperties.getProperty("key");
    }

    public static void main(String[] args) {
        Key key = new SecretKeySpec("TEST".getBytes(), SignatureAlgorithm.HS256.getJcaName());
        System.out.println(Arrays.toString(key.getEncoded()));
    }
}
