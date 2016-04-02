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

    public String createPwHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = new byte[SALT_LEN];
        new Random().nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LEN);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGO);
        byte[] hash = keyFactory.generateSecret(spec).getEncoded();
        return salt.toString() + "$" + hash.toString();
    }

}
