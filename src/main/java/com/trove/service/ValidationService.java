package com.trove.service;

import com.trove.repository.SignUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    @Autowired
    private SignUpRepository signUpRepository;

    public boolean checkEmailExists(String email){
        return signUpRepository.existsByEmail(email);
    }

    public boolean checkPhoneNumberExists(String phoneNumber){
        return signUpRepository.existsByPhoneNumber(phoneNumber);
    }
}
