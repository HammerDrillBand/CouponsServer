package com.oleg.coupons.utils;

import com.oleg.coupons.enums.ErrorType;
import com.oleg.coupons.exceptions.ApplicationException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordEncryption {


    public static String encryptPassword(String password) throws ApplicationException {
        try {
            byte[] salt = "Religion is stupid anyway. I mean, a virgin gets pregnant by a ghost! You would never get away with that in a divorce court, would you?".getBytes();

            // Concatenate the salt and password
            byte[] saltedPassword = concatenateByteArrays(salt, password.getBytes(StandardCharsets.UTF_8));

            // Create an instance of the SHA-256 hashing algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Compute the hash of the salted password
            byte[] encodedPassword = digest.digest(saltedPassword);

            // Encode the salt and hashed password as a Base64 string
            String encodedSalt = Base64.getEncoder().encodeToString(salt);
            String encodedPasswordString = Base64.getEncoder().encodeToString(encodedPassword);

            // Return the concatenated salt and hashed password
            return encodedSalt + encodedPasswordString;
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception appropriately
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to encrypt password", e);
        }
    }

    private static byte[] concatenateByteArrays(byte[] array1, byte[] array2) {
        byte[] combined = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, combined, 0, array1.length);
        System.arraycopy(array2, 0, combined, array1.length, array2.length);
        return combined;
    }
}
