package com.si.lab4.model.requests;


import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class TextRequest {

    private String algorithm;

    private String mode;

    private String privateKey;

    private String inputText;

    private String key;
}
