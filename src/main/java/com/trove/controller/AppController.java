package com.trove.controller;

import com.trove.request.SignUpRequest;
import com.trove.service.AppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    private AppService appService;


    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) {
        return appService.signUp(signUpRequest);
    }
}
