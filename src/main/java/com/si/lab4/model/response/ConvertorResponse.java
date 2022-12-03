package com.si.lab4.model.response;

import lombok.Setter;

@Setter
public class ConvertorResponse {

    public String key;

    public String outputText;

    public String error;

    public boolean hide;

    public boolean hideKey;

    public ConvertorResponse() {
        this.key = "";
        this.error = "";
        this.outputText = "";
        this.hide = true;
        this.hideKey = true;

    }

    public ConvertorResponse(String key, String outputText, boolean hide, boolean hideKey) {
        this.key = key;
        this.error = "";
        this.outputText = outputText;
        this.hide = hide;
        this.hideKey = hideKey;
    }

    public ConvertorResponse(String error) {
        this.error = error;

        this.key = "";
        this.outputText = "";
        this.hide = true;
        this.hideKey = true;
    }
}
