package com.trove.controller;

import com.trove.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class ValidationController {

    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @GetMapping("/checkemail")
    public ResponseEntity<String> checkEmail(@RequestParam("email") String email){

        boolean exists = validationService.checkEmailExists(email);

        if(exists){
            return ResponseEntity.ok("Email exists in the database.");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
        }
    }

    @GetMapping("/checkphonenumber")
    public ResponseEntity<String> checkPhoneNumber(@RequestParam("phoneNumber") String phoneNumber){

        boolean exists = validationService.checkPhoneNumberExists(phoneNumber);

        if(exists){
            return ResponseEntity.ok("Phone number exists in the database.");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Phone number not found.");
        }
    }
}
