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
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO registerDto){
        return authenticationService.registerUser(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO body){
        return authenticationService.loginUser(body.getEmail(), body.getPassword());
    }

    @PutMapping("/verify-account")
    public ResponseEntity<?> verifyAccount(@RequestParam String email,
                                                @RequestParam String otp) {
        return authenticationService.verifyAccount(email, otp);
        //http://localhost:8080/verify-account?email=%s&otp=%s use this for sending request
    }
    @PutMapping("/regenerate-otp")
    public ResponseEntity<?> regenerateOtp(@RequestParam String email) {
        return new ResponseEntity<String>(authenticationService.regenerateOtp(email), HttpStatus.OK);
    }
}