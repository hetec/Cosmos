package org.pode.cosmos.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

/**
 * Handles user authentication issues like password hashing
 * @author Patrick Hebner
 */
@RequestScoped
@Named
public class Authenticator {

    final static int SALT_LEN = 32;
    final static int ITERATIONS = 20*1000;
    final static int KEY_LEN = 256;
    final static String ALGO = "PBKDF2WithHmacSHA512";
    final static String SEPARATOR = "$";

    /**
     * Creates a salted password hash with the PBKDF2 algorithm
     * @param password The password which should be hashed
     * @return The created password hash
     */
    public String hash(String password){
        return createPwHash(password, buildSalt());
    }

    private String createPwHash(final String password, byte[] salt){
        byte[] hash = null;
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LEN);
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGO);
            hash = keyFactory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException algoEx){
            algoEx.printStackTrace();
            throw new IllegalStateException("Wrong algorithm");
        } catch (InvalidKeySpecException specEx) {
            specEx.printStackTrace();
            throw new IllegalStateException("Wrong key specification");
        }
        Base64.Encoder enc = Base64.getEncoder();
        String saltAndHash = enc.encodeToString(salt) + SEPARATOR + enc.encodeToString(hash);
        return saltAndHash;
    }

    private byte[] buildSalt(){
        byte[] salt = new byte[SALT_LEN];
        new Random().nextBytes(salt);
        return salt;
    }

    public boolean checkCredentials(final String requestedPw, final String storedSaltAndHash){
        // Get the stored salt from the hash
        String storedSalt = extractSalt(storedSaltAndHash);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] saltBytes = decoder.decode(storedSalt);
        // Hash the new password with the stored salt
        String requestedSaltAndHash = createPwHash(requestedPw, saltBytes);
        // If both hashes are equal also the passwords is valid
        return storedSaltAndHash.equals(requestedSaltAndHash);
    }

    private String extractSalt (final String storedHash){
        String[]pw = storedHash.split("\\$");
        return pw[0];
    }

}
