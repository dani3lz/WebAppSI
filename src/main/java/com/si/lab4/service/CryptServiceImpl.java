package com.si.lab4.service;

import com.si.lab4.exceptions.SomethingIsWrongException;
import com.si.lab4.model.requests.TextRequest;
import com.si.lab4.model.response.TextResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
@Slf4j
public class CryptServiceImpl implements CryptService {

    public CryptServiceImpl() throws NoSuchPaddingException, NoSuchAlgorithmException {
    }

    public enum Mode {
        ENCRYPT,
        DECRYPT
    }

    public enum Algorithm {
        RSA,
        DES,
        AES
    }

    private SecretKey keyAES = null;
    private SecretKey keyDES = null;
    private static final int KEY_SIZE_AES = 128;
    private static final int KEY_SIZE_DES = 56;

    private final Cipher encryptionCipherAES = Cipher.getInstance("AES/GCM/NoPadding");
    private final Cipher encryptionCipherDES = Cipher.getInstance("DES/CBC/PKCS5Padding");
    private final Cipher decryptionCipherDES = Cipher.getInstance("DES/CBC/PKCS5Padding");
    private final Cipher decryptionCipherAES = Cipher.getInstance("AES/GCM/NoPadding");

    @Override
    public TextResponse doOperation(TextRequest request) throws Exception {
        TextResponse response = new TextResponse();
        initAES();
        initDES();
        log.info("Starting to do something...");
        if (request.getMode().equals(Mode.ENCRYPT.toString())) {
            response = encryptText(request);
        } else if (request.getMode().equals(Mode.DECRYPT.toString())) {
            response = decryptText(request);
        }
        log.info("Finish");
        return response;
    }

    private TextResponse decryptText(TextRequest request) {
        if ("AES".equals(request.getAlgorithm())) {
            byte[] decodedKey = Base64.getDecoder().decode(request.getKey());
            SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, Algorithm.AES.toString());
            return aesDecrypt(request.getInputText(), originalKey);
        } else if ("DES".equals(request.getAlgorithm())) {

            byte[] decodedKey = Base64.getDecoder().decode(request.getKey());
            SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, Algorithm.DES.toString());
            return desDecrypt(request.getInputText(), originalKey);
        } else if ("RSA".equals(request.getAlgorithm())) {
            // TODO
        } else
            return null;
        throw new RuntimeException("mda");
    }

    private TextResponse encryptText(TextRequest request) {

        if (Algorithm.AES.toString().equals(request.getAlgorithm())) {
            return aesEncrypt(request.getInputText());
        } else if (Algorithm.DES.toString().equals(request.getAlgorithm())) {
            return desEncrypt(request.getInputText());
        } else if (Algorithm.RSA.toString().equals(request.getAlgorithm())) {
            // TODO
        } else
            return null;
        throw new RuntimeException("mda");
    }

    private TextResponse aesEncrypt(String inputText) {
        try {
            TextResponse response = new TextResponse();
            response.setOutputText(encryptAES(inputText, keyAES));
            response.setKey(Base64.getEncoder().encodeToString(keyAES.getEncoded()));
            return response;
        } catch (Exception e) {
            throw new SomethingIsWrongException();
        }
    }

    private TextResponse aesDecrypt(String inputText, SecretKey key) {
        try {
            TextResponse response = new TextResponse();
            response.setOutputText(decryptAES(inputText, key));
            return response;
        } catch (Exception e) {
            throw new SomethingIsWrongException();
        }
    }

    public String encryptAES(String data, SecretKey key) {
        try {
            byte[] dataInBytes = data.getBytes();
            encryptionCipherAES.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = encryptionCipherAES.doFinal(dataInBytes);
            return encode(encryptedBytes);
        } catch (Exception e) {
            throw new SomethingIsWrongException();
        }
    }

    public String decryptAES(String encryptedData, SecretKey key) {
        try {
            byte[] dataInBytes = decode(encryptedData);
            GCMParameterSpec spec = new GCMParameterSpec(KEY_SIZE_AES, encryptionCipherAES.getIV());
            decryptionCipherAES.init(Cipher.DECRYPT_MODE, key, spec);
            byte[] decryptedBytes = decryptionCipherAES.doFinal(dataInBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new SomethingIsWrongException();
        }
    }

    private TextResponse desEncrypt(String inputText) {
        try {
            TextResponse response = new TextResponse();
            response.setOutputText(encryptDES(inputText, keyDES));
            response.setKey(Base64.getEncoder().encodeToString(keyDES.getEncoded()));
            return response;
        } catch (Exception e) {
            throw new SomethingIsWrongException();
        }
    }

    public String encryptDES(String data, SecretKey key) {
        try {
            byte[] dataInBytes = data.getBytes();
            // encryptionCipherDES = Cipher.getInstance("DES/CBC/PKCS5Padding");
            encryptionCipherDES.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = encryptionCipherDES.doFinal(dataInBytes);
            return encode(encryptedBytes);
        } catch (Exception e) {
            throw new SomethingIsWrongException();
        }
    }

    private TextResponse desDecrypt(String inputText, SecretKey key) {
        try {
            TextResponse response = new TextResponse();
            response.setOutputText(decryptDES(inputText, key));
            return response;
        } catch (Exception e) {
            throw new SomethingIsWrongException();
        }
    }

    public String decryptDES(String encryptedData, SecretKey key) {
        try {
            byte[] dataInBytes = decode(encryptedData);
            decryptionCipherDES.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(encryptionCipherDES.getIV()));
            byte[] decryptedBytes = decryptionCipherDES.doFinal(dataInBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new SomethingIsWrongException();
        }
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public void initAES() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(Algorithm.AES.toString());
        keyGenerator.init(KEY_SIZE_AES);
        this.keyAES = keyGenerator.generateKey();
    }

    public void initDES() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(Algorithm.DES.toString());
        keyGenerator.init(KEY_SIZE_DES);
        this.keyDES = keyGenerator.generateKey();
    }


}
