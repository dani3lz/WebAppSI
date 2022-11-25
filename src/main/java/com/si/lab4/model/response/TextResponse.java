package com.si.lab4.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TextResponse {

    private String privateKey;

    private String publicKey;

    private String outputText;

    private String key;
}
