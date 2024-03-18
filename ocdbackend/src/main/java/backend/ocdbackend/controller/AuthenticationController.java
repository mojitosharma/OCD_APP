package backend.ocdbackend.controller;

import backend.ocdbackend.model.*;
import backend.ocdbackend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
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

    @PostMapping("/sms-send")
    public ResponseEntity<String> sendMessage(@RequestBody TwilioRequest twilioRequest) {

        // Check if RequestBody has valid data or NOT
        if (twilioRequest == null || twilioRequest.getFromPhoneNumber() == null
                || twilioRequest.getToPhoneNumber() == null || twilioRequest.getMessage() == null) {
            return ResponseEntity.badRequest().body("Invalid request data");
        }

        // Extract Request Data
        String fromNumber = twilioRequest.getFromPhoneNumber();
        String toNumber = twilioRequest.getToPhoneNumber();
        String msg = twilioRequest.getMessage();

        // Create Message to be sent
        Message.creator(new PhoneNumber(toNumber), new PhoneNumber(fromNumber),
                msg).create();

        return ResponseEntity.ok("SMS sent Succesfully !");
    }
}