package com.si.lab4.service;

import com.si.lab4.model.requests.TextRequest;
import com.si.lab4.model.response.TextResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
@Slf4j
public class CryptServiceImpl implements CryptService {

    private SecretKey key = null;
    private static final int KEY_SIZE_AES = 128;
    private static final int KEY_SIZE_DES = 56;

    private Cipher encryptionCipherAES;

    private Cipher encryptionCipherDES;


    @Override
    public TextResponse doOperation(TextRequest request) throws Exception {
        TextResponse response = new TextResponse();
        if ("encrypt".equals(request.getMode())) {
           response = encryptText(request);
        } else if ("decrypt".equals(request.getMode())) {
           response = decryptText(request);
        }
            log.info("O trecut de if");
            return response;
    }

    private TextResponse decryptText(TextRequest request) throws Exception {
        if ("AES".equals(request.getAlgorithm())) {
            byte[] decodedKey = Base64.getDecoder().decode(request.getKey());
            SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            return aesDecrypt(request.getInputText(), originalKey);
        } else if ("DES".equals(request.getAlgorithm())) {
            byte[] decodedKey = Base64.getDecoder().decode(request.getKey());
            SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
            return desDecrypt(request.getInputText(), originalKey);
        } else if ("RSA".equals(request.getAlgorithm())) {

        } else
            return null;
        throw new RuntimeException("mda");
    }



    private TextResponse encryptText(TextRequest request) throws Exception {
        if ("AES".equals(request.getAlgorithm())) {
            initAES();
            return aesEncrypt(request.getInputText());
        } else if ("DES".equals(request.getAlgorithm())) {
            initDES();
            return desEncrypt(request.getInputText());
        } else if ("RSA".equals(request.getAlgorithm())) {

        } else
            return null;
        throw new RuntimeException("mda");
    }

    private TextResponse aesEncrypt(String inputText) throws Exception {
        TextResponse response = new TextResponse();
        response.setOutputText(encryptAES(inputText, key));
        response.setKey(Base64.getEncoder().encodeToString(key.getEncoded()));
        response.setPrivateKey(null);
        response.setPublicKey(null);
        return response;
    }

    private TextResponse aesDecrypt(String inputText, SecretKey key) throws Exception {
        TextResponse response = new TextResponse();
        response.setOutputText(decryptAES(inputText, key));
        response.setKey(Base64.getEncoder().encodeToString(key.getEncoded()));
        response.setPrivateKey(null);
        response.setPublicKey(null);
        return response;
    }

    public String encryptAES(String data, SecretKey key) throws Exception {
        byte[] dataInBytes = data.getBytes();
        encryptionCipherAES = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipherAES.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipherAES.doFinal(dataInBytes);
        return encode(encryptedBytes);
    }

    public String decryptAES(String encryptedData, SecretKey key) throws Exception {
        byte[] dataInBytes = decode(encryptedData);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(KEY_SIZE_AES, encryptionCipherAES.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes);
        return new String(decryptedBytes);
    }

    private TextResponse desEncrypt(String inputText) throws Exception {
        TextResponse response = new TextResponse();
        response.setOutputText(encryptDES(inputText, key));
        response.setKey(Base64.getEncoder().encodeToString(key.getEncoded()));
        response.setPrivateKey(null);
        response.setPublicKey(null);
        return response;
    }

    public String encryptDES(String data, SecretKey key) throws Exception {
        byte[] dataInBytes = data.getBytes();
        encryptionCipherDES = Cipher.getInstance("DES/CBC/PKCS5Padding");
        encryptionCipherDES.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipherDES.doFinal(dataInBytes);
        return encode(encryptedBytes);
    }

    private TextResponse desDecrypt(String inputText, SecretKey key) throws Exception {
        TextResponse response = new TextResponse();
        response.setOutputText(decryptDES(inputText, key));
        response.setKey(Base64.getEncoder().encodeToString(key.getEncoded()));
        response.setPrivateKey(null);
        response.setPublicKey(null);
        return response;
    }

    public String decryptDES(String encryptedData, SecretKey key) throws Exception {
        byte[] dataInBytes = decode(encryptedData);
        Cipher decryptionCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(encryptionCipherDES.getIV()));
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes);
        return new String(decryptedBytes);
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public void initAES() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(KEY_SIZE_AES);
        this.key = keyGenerator.generateKey();
    }

    public void initDES() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(KEY_SIZE_DES);
        this.key = keyGenerator.generateKey();
    }


}
