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
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
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
    private PrivateKey keyRSAprivate = null;
    private PublicKey keyRSApublic = null;
    private static final int KEY_SIZE_AES = 128;
    private static final int KEY_SIZE_DES = 56;
    private static final int KEY_SIZE_RSA = 2048;

    private final Cipher encryptionCipherAES = Cipher.getInstance("AES/GCM/NoPadding");
    private final Cipher encryptionCipherDES = Cipher.getInstance("DES/CBC/PKCS5Padding");
    private final Cipher encryptionCipherRSA = Cipher.getInstance("RSA");
    private final Cipher decryptionCipherDES = Cipher.getInstance("DES/CBC/PKCS5Padding");
    private final Cipher decryptionCipherAES = Cipher.getInstance("AES/GCM/NoPadding");
    private final Cipher decryptionCipherRSA = Cipher.getInstance("RSA");

    @Override
    public TextResponse doOperation(TextRequest request) throws Exception {
        TextResponse response = new TextResponse();
        initAES();
        initDES();
        initRSA();
        log.info("Starting to do something...");
        if (request.getMode().equals(Mode.ENCRYPT.toString())) {
            response = encryptText(request);
        } else if (request.getMode().equals(Mode.DECRYPT.toString())) {
            response = decryptText(request);
        }
        log.info("Finish");
        return response;
    }

    private TextResponse decryptText(TextRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (Algorithm.AES.toString().equals(request.getAlgorithm())) {
            byte[] decodedKey = Base64.getDecoder().decode(request.getKey());
            SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, Algorithm.AES.toString());
            return aesDecrypt(request.getInputText(), originalKey);
        } else if (Algorithm.DES.toString().equals(request.getAlgorithm())) {
            byte[] decodedKey = Base64.getDecoder().decode(request.getKey());
            SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, Algorithm.DES.toString());
            return desDecrypt(request.getInputText(), originalKey);
        } else if (Algorithm.RSA.toString().equals(request.getAlgorithm())) {
            byte[] decodedKey = Base64.getDecoder().decode(request.getKey());
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey originalKey = kf.generatePrivate(new PKCS8EncodedKeySpec(decodedKey));
            return rsaDecrypt(request.getInputText(), originalKey);
        } else
            throw new SomethingIsWrongException();
    }

    private TextResponse encryptText(TextRequest request) {

        if (Algorithm.AES.toString().equals(request.getAlgorithm())) {
            return aesEncrypt(request.getInputText());
        } else if (Algorithm.DES.toString().equals(request.getAlgorithm())) {
            return desEncrypt(request.getInputText());
        } else if (Algorithm.RSA.toString().equals(request.getAlgorithm())) {
            return rsaEncrypt(request.getInputText());
        } else
            throw new SomethingIsWrongException();
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

    private TextResponse rsaEncrypt(String inputText) {
        try {
            TextResponse response = new TextResponse();
            response.setOutputText(encryptRSA(inputText, keyRSApublic));
            response.setKey(Base64.getEncoder().encodeToString(keyRSAprivate.getEncoded()));
            return response;
        } catch (Exception e) {
            throw new SomethingIsWrongException();
        }
    }

    public String encryptRSA(String data, PublicKey key) {
        try {
            byte[] dataInBytes = data.getBytes();
            encryptionCipherRSA.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = encryptionCipherRSA.doFinal(dataInBytes);
            return encode(encryptedBytes);
        } catch (Exception e) {
            throw new SomethingIsWrongException();
        }
    }

    private TextResponse rsaDecrypt(String inputText, PrivateKey key) {
        try {
            TextResponse response = new TextResponse();
            response.setOutputText(decryptRSA(inputText, key));
            return response;
        } catch (Exception e) {
            throw new SomethingIsWrongException();
        }
    }

    public String decryptRSA(String encryptedData, PrivateKey key) {
        try {
            byte[] dataInBytes = decode(encryptedData);
            decryptionCipherRSA.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = decryptionCipherRSA.doFinal(dataInBytes);
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

    public void initAES() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(Algorithm.AES.toString());
            keyGenerator.init(KEY_SIZE_AES);
            this.keyAES = keyGenerator.generateKey();
        } catch (Exception e) {
            throw new SomethingIsWrongException();
        }
    }

    public void initDES() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(Algorithm.DES.toString());
            keyGenerator.init(KEY_SIZE_DES);
            this.keyDES = keyGenerator.generateKey();
        } catch (Exception e) {
            throw new SomethingIsWrongException();
        }
    }

    public void initRSA() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(KEY_SIZE_RSA);
            KeyPair pair = generator.generateKeyPair();
            this.keyRSAprivate = pair.getPrivate();
            this.keyRSApublic = pair.getPublic();
        } catch (Exception e) {
            throw new SomethingIsWrongException();
        }
    }


}
