package backend.ocdbackend.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import backend.ocdbackend.model.LoginResponseDTO;
import backend.ocdbackend.model.ApplicationUser;
import backend.ocdbackend.model.Name;
import backend.ocdbackend.model.Role;
import backend.ocdbackend.repository.RoleRepository;
import backend.ocdbackend.repository.UserRepository;
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
    public TokenService tokenService;

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }


    public String registerUser(String email, String password, Name name, Integer patient_number, Date dob, Date day_of_enrollment, String gender, String education, String occupation, Integer therapist_id, String profile_image) {

        if (!isValidEmail(email)) {
            return "Invalid email format";
        }

        Optional<ApplicationUser> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            return "User already exists and cannot register";
        }

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER")
                .orElseThrow(() -> new RuntimeException("User role not found"));
        System.out.println(userRole.getId());
        Set<ObjectId> authorities = new HashSet<>();
        authorities.add(userRole.getId()); // Assuming Role has an ObjectId getter

        ApplicationUser user = new ApplicationUser(
                email, // email
                encodedPassword, // encoded password
                authorities, // authorities
                name, // name
                patient_number, // patient_number
                dob, // dob
                day_of_enrollment, // day_of_enrollment
                gender, // gender
                education, // education
                occupation, // occupation
                therapist_id, // therapist_id
                profile_image // profile_image
        );

        userRepository.save(user);
        return "User successfully registered";
    }


    public LoginResponseDTO loginUser(String email, String password){

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            System.out.println("Check");
            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found")), token);

        } catch(AuthenticationException e){
            return new LoginResponseDTO(null, "");
        }
    }

}