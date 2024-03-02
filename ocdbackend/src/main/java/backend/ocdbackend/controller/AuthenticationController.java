package backend.ocdbackend.controller;

import backend.ocdbackend.model.ApplicationUser;
import backend.ocdbackend.model.LoginResponseDTO;
import backend.ocdbackend.model.RegistrationDTO;
import backend.ocdbackend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationDTO body){
        return authenticationService.registerUser(body.getEmail(), body.getPassword(),
                body.getName(), body.getPatientNumber(), body.getDob(), body.getDayOfEnrollment(), body.getGender(),
                body.getEducation(), body.getOccupation(), body.getTherapistId(), body.getProfileImage());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getEmail(), body.getPassword());
    }
}