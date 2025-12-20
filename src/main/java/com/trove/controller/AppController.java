package com.trove.controller;

import com.trove.request.SignUpRequest;
import com.trove.response.ErrorResponse;
import com.trove.response.Response;
import com.trove.service.AppService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {


    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Response> signUp(@RequestBody SignUpRequest signUpRequest, BindingResult result) {
        if (result.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse(result.getFieldError().getDefaultMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } else {
            return ResponseEntity.ok().body(appService.doSignUp(signUpRequest));

        }

    }
}
