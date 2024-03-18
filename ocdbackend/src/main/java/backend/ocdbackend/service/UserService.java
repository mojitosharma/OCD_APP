package backend.ocdbackend.service;

import backend.ocdbackend.configuration.SecurityConfiguration;
import backend.ocdbackend.model.ApplicationUser;
import backend.ocdbackend.model.PasswordChangeDTO;
import backend.ocdbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    final static String USER_NOT_FOUND = "user with email %s not found";
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfig;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND,email)));
    }

    public ResponseEntity<?> changePassword(PasswordChangeDTO passwordChangeDto, String token) {

        // Extract the username from the JWT token
        String email = securityConfig.jwtDecoder().decode(token).getClaim("email");

        // Find the user by username
        ApplicationUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));

        // Validate the old password
        if (!passwordEncoder.matches(passwordChangeDto.getOldPassword(), user.getPassword())) {
            return new ResponseEntity<>("Error: Old password is incorrect", HttpStatus.BAD_REQUEST);
        }

        // Update the password
        String encodedNewPassword = passwordEncoder.encode(passwordChangeDto.getNewPassword());
        user.setPassword(encodedNewPassword);
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}