package com.example.demo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCrypt password encryption utility class using Spring Security's BCryptPasswordEncoder
 */
public class BCrypt {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    /**
     * Encrypt password
     * @param password Plain text password
     * @param salt Salt (ignored in this implementation as BCryptPasswordEncoder automatically generates and includes salt)
     * @return Encrypted password
     */
    public static String hashpw(String password, String salt) {
        return encoder.encode(password);
    }
    
    /**
     * Generate salt (returns empty string as BCryptPasswordEncoder handles salt automatically)
     * @return Empty string
     */
    public static String gensalt() {
        return "";
    }
    
    /**
     * Check if password matches
     * @param plaintext Plain text password
     * @param hashed Encrypted password
     * @return Whether they match
     */
    public static boolean checkpw(String plaintext, String hashed) {
        return encoder.matches(plaintext, hashed);
    }
} 