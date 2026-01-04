package com.trove.controller;

import com.trove.response.Response;
import com.trove.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.trove.utility.AppConstant.*;

@RestController
@RequestMapping("/api/auth")
public class ValidationController {

    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }


    @GetMapping("/checkEmail")
    public ResponseEntity<Response> checkEmail(@RequestParam("email") String email){

        boolean exists = validationService.checkEmailExists(email);

        if(exists){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(VALIDATION_EMAIL_EXISTS));
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(new Response(VALIDATION_EMAIL_DOES_NOT_EXISTS));
        }
    }


    @GetMapping("/checkphonenumber")
    public ResponseEntity<String> checkPhoneNumber(@RequestParam("phoneNumber") String phoneNumber){

        boolean exists = validationService.checkPhoneNumberExists(phoneNumber);

        if(exists){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(VALIDATION_PHONE_EXISTS);
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(VALIDATION_PHONE_DOES_NOT_EXISTS);
        }
    }
}
