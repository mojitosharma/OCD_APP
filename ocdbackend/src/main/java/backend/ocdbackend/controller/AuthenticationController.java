package backend.ocdbackend.controller;

import backend.ocdbackend.model.ApplicationUser;
import backend.ocdbackend.model.LoginDTO;
import backend.ocdbackend.model.LoginResponseDTO;
import backend.ocdbackend.model.RegistrationDTO;
import backend.ocdbackend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")

public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegistrationDTO registerDto){
        return authenticationService.registerUser(registerDto);
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody LoginDTO body){
        return authenticationService.loginUser(body.getEmail(), body.getPassword());
    }

    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String email,
                                                @RequestParam String otp) {
        return new ResponseEntity<>(authenticationService.verifyAccount(email, otp), HttpStatus.OK);
        //http://localhost:8080/verify-account?email=%s&otp=%s use this for sending request
    }
    @PutMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email) {
        return new ResponseEntity<>(authenticationService.regenerateOtp(email), HttpStatus.OK);
    }
}