package com.si.lab4.controllers.common;

import com.si.lab4.model.requests.TextRequest;
import com.si.lab4.model.response.TextResponse;
import com.si.lab4.service.CryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CryptController {

    private final CryptService cryptService;

    @PostMapping("/convertor")
    @ResponseStatus(HttpStatus.OK)
    public TextResponse doSomething(@RequestBody @Valid TextRequest request) throws Exception {
        return cryptService.doOperation(request);
    }

}
