package com.trove.service;

import com.trove.exceptions.UserAlreadyExistsException;
import com.trove.model.SignUp;
import com.trove.repository.SignUpRepository;
import com.trove.request.LoginRequest;
import com.trove.request.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private SignUpRepository signUpRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

//  Old Logic written for the checking the user is already present or not in the Database

//    private boolean isUserPresentBasedOnEmail(String email){
//        Optional<SignUp> userRetrievedFromDB = signUpRepository.findByEmail(email);
//        if (userRetrievedFromDB.isEmpty()) {
//            return false;
//        }
//        return true;
//    }

// This Logic is written for the checking the user is already present or not in the Database with the help of the email id.
//    Created the User Already Exists Exception which is a Custom Exception to handle the case when the user is already present in the Database.


    public SignUp doSignUp(SignUpRequest signUpRequest){

        if (signUpRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email " + signUpRequest.getEmail() + " is already in use.");
        }
        SignUp signupuser = new SignUp();
        signupuser.setFirstName(signUpRequest.getFirstName());
        signupuser.setLastName(signUpRequest.getLastName());
        signupuser.setEmail(signUpRequest.getEmail());
        signupuser.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
        signupuser.setPhoneNumber(signUpRequest.getPhoneNumber());
        signupuser.setGender(signUpRequest.getGender());
        signupuser.setDob(signUpRequest.getDob());
        signupuser.setCountry(signUpRequest.getCountry());
        signupuser.setState(signUpRequest.getState());
        signupuser.setCity(signUpRequest.getCity());
        signupuser.setAddress(signUpRequest.getAddress());
        signupuser.setPincode(signUpRequest.getPincode());
        signupuser.setPhotoUrl(signUpRequest.getPhotoUrl());

//        // HANDLE PHOTO
//        if (signUpRequest.getPhotoUrl() != null) {
//            signupuser.setPhotoUrl(signUpRequest.getPhotoUrl());
//        } else {
//            // Optional: Set a default placeholder image if none provided
//            signupuser.setPhotoUrl("http://localhost:8081/uploads/default-avatar.png");
//        }
         signUpRepository.save(signupuser);

        Optional<SignUp> userOptional = signUpRepository.findByEmail(signUpRequest.getEmail());
        SignUp signUpUser = userOptional.get();

        return signUpUser;

    }

//    Here we are getting the user credentials and checking if the user is present in the Database or not.
//    If the user is present then we are checking the password is correct or not.
//    If the password is correct then we are returning the user object.
//    If the password is not correct then we are returning null.

    public SignUp doLogin(LoginRequest loginRequest){

        Optional<SignUp> userOptional = signUpRepository.findByEmail(loginRequest.getEmail());

        if(userOptional.isPresent()){
            SignUp loginUser = userOptional.get();

            if(bCryptPasswordEncoder.matches(loginRequest.getPassword(),loginUser.getPassword())){
                return loginUser;
            }
        }
        return null;
    }



// This is a Service Class I have Written for the Firebase Setup. Since it is not free, I am not Configuring it there.
//    Whenever we want to use it, We can remove the comment and use it for the application.

//    public void updatePhoto(PhotoUpdateRequest photoUpdateRequest){
//        SignUp user = signUpRepository.findByEmail(photoUpdateRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found with email: " + photoUpdateRequest.getEmail()));
//        user.setPhotoUrl(photoUpdateRequest.getPhotoUrl());
//        signUpRepository.save(user);
//    }

}
