package org.pode.cosmos.auth;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

/**
 * Created by patrick on 02.04.16.
 */
@RequestScoped
@Named
public class Authenticator {

    final static int SALT_LEN = 32;
    final static int ITERATIONS = 20*1000;
    final static int KEY_LEN = 256;
    final static String ALGO = "PBKDF2WithHmacSHA512";

    public String createPwHash(String password){
        System.out.println("Create hash");
        byte[] salt = new byte[SALT_LEN];
        new Random().nextBytes(salt);
        byte[] hash = null;
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LEN);
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGO);
            hash = keyFactory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException algoEx){
            System.out.println("No such algorithm!");
            algoEx.printStackTrace();
            //Do loggin here
        } catch (InvalidKeySpecException specEx) {
            System.out.println("Wrong key spec!");
            specEx.printStackTrace();
            //Do loggin here
        }
        return salt.toString() + "$" + hash.toString();
    }

    public boolean checkCredentials(String requestedPw, String storedPwHash){
        System.out.println("requested: " + requestedPw);
        System.out.println("stored: " + storedPwHash);
        return extractPw(storedPwHash).equals(extractPw(createPwHash(requestedPw)));

    }

    private String extractPw (String storedHash){
        return storedHash.split("$")[1];
    }

}
