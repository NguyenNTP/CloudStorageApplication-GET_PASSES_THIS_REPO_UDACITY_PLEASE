package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class EncryptionService {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public String encryptValue(String value, String key) {
        try {
            IvParameterSpec iv = generateIv();
            SecretKey secretKey = getKeyFromEncodedKey(key);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] cipherText = cipher.doFinal(value.getBytes());
            String encryptedValue = Base64.getEncoder().encodeToString(cipherText);
            String ivBase64 = Base64.getEncoder().encodeToString(iv.getIV());
            return ivBase64 + ":" + encryptedValue;  // Kết hợp IV và ciphertext
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while encrypting value", e);
        }
    }

    public String decryptValue(String encryptedValue, String key) {
        try {
            String[] parts = encryptedValue.split(":");
            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(parts[0]));
            byte[] cipherText = Base64.getDecoder().decode(parts[1]);
            SecretKey secretKey = getKeyFromEncodedKey(key);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] originalText = cipher.doFinal(cipherText);
            return new String(originalText);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while decrypting value", e);
        }
    }

    private SecretKey getKeyFromEncodedKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    private IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        return new IvParameterSpec(iv);
    }
}