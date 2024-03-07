package backend.ocdbackend.email;

import backend.ocdbackend.model.EmailConfirmationToken;

public interface EmailService {
    void sendConfirmationEmail(EmailConfirmationToken emailConfirmationToken);
}