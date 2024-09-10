package com.gaebaljip.exceed.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.common.annotation.Timer;
import com.gaebaljip.exceed.common.exception.DecryptionErrorException;
import com.gaebaljip.exceed.common.exception.EncryptionErrorException;
import com.gaebaljip.exceed.common.exception.member.ExpiredCodeException;

@Component
public class Encryption {

    @Value("${encryption.spec}")
    private String encryptionSpec;

    @Value("${encryption.algorithm}")
    private String algorithm;

    @Value("${encryption.secret}")
    private String secret;

    private SecretKeySpec secretKeySpec;
    private Cipher cipher;

    @Timer
    public String encrypt(String value) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8); // String을 바이트 배열로 변환
            byte[] encrypted = cipher.doFinal(valueBytes);
            return Base64.getUrlEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw EncryptionErrorException.EXECPTION;
        }
    }

    @Timer
    public String decrypt(final String encryptedValue) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] encryptedBytes = Base64.getUrlDecoder().decode(encryptedValue);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8); // 바이트 배열을 String으로 변환
        } catch (Exception e) {
            throw DecryptionErrorException.EXECPTION;
        }
    }

    public Boolean match(final String decrypt, final String value) {
        if (!Objects.equals(decrypt, value)) {
            throw ExpiredCodeException.EXECPTION;
        }
        return true;
    }

    @PostConstruct
    public void init() throws NoSuchPaddingException, NoSuchAlgorithmException {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        keyBytes = sha.digest(keyBytes);
        keyBytes = Arrays.copyOf(keyBytes, 16); // AES-128을 위한 16바이트 키
        secretKeySpec = new SecretKeySpec(keyBytes, algorithm);
        cipher = Cipher.getInstance(encryptionSpec);
    }
}
