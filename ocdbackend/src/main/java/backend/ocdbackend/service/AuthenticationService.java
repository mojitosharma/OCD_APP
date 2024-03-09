package backend.ocdbackend.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import backend.ocdbackend.model.*;
import backend.ocdbackend.repository.RoleRepository;
import backend.ocdbackend.repository.UserRepository;
import backend.ocdbackend.utils.EmailUtil;
import backend.ocdbackend.utils.EmailValidator;
import backend.ocdbackend.utils.OtpUtil;
import org.bson.types.ObjectId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private OtpUtil otpUtil;
    @Autowired
    private EmailUtil emailUtil;



    public String registerUser(RegistrationDTO registerDto) {

        if (!emailValidator.test(registerDto.getEmail())) {
            return "email not valid";
        }

        Optional<ApplicationUser> existingUser = userRepository.findByEmail(registerDto.getEmail());
        if (existingUser.isPresent()) {
            return "User already exists and cannot register";
        }

        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(registerDto.getEmail(), otp);
        } catch (Exception e) { //Review Exception @MohitSharma (MessagingException e Not working here)
            throw new RuntimeException("Unable to send otp please try again");
        }

        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        Role userRole = roleRepository.findByAuthority("USER")
                .orElseThrow(() -> new RuntimeException("User role not found"));
        System.out.println(userRole.getId());
        Set<ObjectId> authorities = new HashSet<>();
        authorities.add(userRole.getId()); // Assuming Role has an ObjectId getter

        ApplicationUser user = new ApplicationUser(
                registerDto.getEmail(), // email
                encodedPassword, // encoded password
                authorities, // authorities
                registerDto.getName(), // name
                registerDto.getPatientNumber(), // patient_number
                registerDto.getDob(), // dob
                registerDto.getDayOfEnrollment(), // day_of_enrollment
                registerDto.getGender(), // gender
                registerDto.getEducation(), // education
                registerDto.getOccupation(), // occupation
                registerDto.getTherapistId(), // therapist_id
                registerDto.getProfileImage() // profile_image
        );
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "User successfully registered";
    }

    public String verifyAccount(String email, String otp) {
        ApplicationUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (1 * 60)) {
            user.setEnabled(true);
            userRepository.save(user);
            return "OTP verified you can login";
        }
        return "Please regenerate otp and try again";
    }

    public String regenerateOtp(String email) {
        ApplicationUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (Exception e) { //MessagingException Exception pro
            throw new RuntimeException("Unable to send otp please try again");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent... please verify account within 1 minute";
    }

    public LoginResponseDTO loginUser(String email, String password){

        try{
            ApplicationUser user = userRepository.findByEmail(email)
                    .orElseThrow(
                            () -> new RuntimeException("User not found with this email: " + email));
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
//            if (password.equals(user.getPassword())) {
//                return "Password is incorrect";
//            } else if (!user.isActive()) {
//                return "your account is not verified";
//            }
            String token = tokenService.generateJwt(auth);
            return new LoginResponseDTO(userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found")), token);

        } catch(AuthenticationException e){
            return new LoginResponseDTO(null, "");
        }
    }

}