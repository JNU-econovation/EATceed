package com.gaebaljip.exceed.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.common.exception.DecryptionErrorException;
import com.gaebaljip.exceed.common.exception.EncryptionErrorException;

@Component
public class Encryption {

    @Value("${encryption.spec}")
    private String encryptionSpec;

    @Value("${encryption.algorithm}")
    private String algorithm;

    @Value("${encryption.secret}")
    private String secret;

    private SecretKeySpec secretKeySpec;
    private IvParameterSpec ivParameterSpec;
    private Cipher cipher;

    public String encrypt(String value) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8); // String을 바이트 배열로 변환
            byte[] encrypted = cipher.doFinal(valueBytes);
            return Base64.getUrlEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw EncryptionErrorException.EXECPTION;
        }
    }

    public String decrypt(final String encryptedValue) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encryptedBytes = Base64.getUrlDecoder().decode(encryptedValue);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8); // 바이트 배열을 String으로 변환
        } catch (Exception e) {
            throw DecryptionErrorException.EXECPTION;
        }
    }

    @PostConstruct
    public void init() throws NoSuchPaddingException, NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16]; // 16bytes = 128bits
        secureRandom.nextBytes(iv);
        ivParameterSpec = new IvParameterSpec(iv);

        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        keyBytes = sha.digest(keyBytes);
        keyBytes = Arrays.copyOf(keyBytes, 16); // AES-128을 위한 16바이트 키
        secretKeySpec = new SecretKeySpec(keyBytes, algorithm);

        cipher = Cipher.getInstance(encryptionSpec);
    }
}
