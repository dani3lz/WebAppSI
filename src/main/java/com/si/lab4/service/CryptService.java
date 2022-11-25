package com.si.lab4.service;

import com.si.lab4.model.requests.TextRequest;
import com.si.lab4.model.response.TextResponse;

public interface CryptService {

    TextResponse doOperation(TextRequest request) throws Exception;
}
