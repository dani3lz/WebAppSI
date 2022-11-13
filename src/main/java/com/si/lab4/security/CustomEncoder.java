package com.si.lab4.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class CustomEncoder implements PasswordEncoder {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final String ALGORITHM = "RSA";

    public CustomEncoder() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(2048);
        KeyPair generatedKeyPair = keyGen.generateKeyPair();
        this.privateKey = generatedKeyPair.getPrivate();
        this.publicKey = generatedKeyPair.getPublic();
    }

    @Override
    public String encode(CharSequence rawPassword) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
        try {
            cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        try {
            return new String(cipher.doFinal(rawPassword.toString().getBytes()));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
        try {
            cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        byte[] bytes;
        try {
            bytes = cipher.doFinal(encodedPassword.getBytes());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return new String(bytes).equals(rawPassword.toString());
    }
}
