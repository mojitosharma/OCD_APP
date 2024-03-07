package backend.ocdbackend.service;

import backend.ocdbackend.model.ApplicationUser;
import backend.ocdbackend.model.EmailConfirmationToken;
import backend.ocdbackend.repository.EmailConfirmationTokenRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class EmailConfirmationTokenService {

    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;

    public EmailConfirmationTokenService(EmailConfirmationTokenRepository emailConfirmationTokenRepository) {
        this.emailConfirmationTokenRepository = emailConfirmationTokenRepository;
    }
    public String generateAndStoreToken(ApplicationUser user) {
        SecureRandom secureRandom = new SecureRandom();
        int token = secureRandom.nextInt(9000) + 1000; // Generates a 4-digit number
        String tokenValue = String.format("%04d", token); // Formats the number to ensure it's always 4 digits
        EmailConfirmationToken emailConfirmationToken = new EmailConfirmationToken();
        emailConfirmationToken.setToken(tokenValue);
        emailConfirmationToken.setTimeStamp(LocalDateTime.now());
        emailConfirmationToken.setUser(user);
        emailConfirmationTokenRepository.save(emailConfirmationToken);
        return tokenValue;
    }
}
