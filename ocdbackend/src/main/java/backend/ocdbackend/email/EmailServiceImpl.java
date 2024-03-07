package backend.ocdbackend.email;

import backend.ocdbackend.model.EmailConfirmationToken;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender sender;
//    https://dev.to/amailath/streamlining-email-verification-a-step-by-step-guide-with-spring-boot-and-angular-eff

    public EmailServiceImpl(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendConfirmationEmail(EmailConfirmationToken emailConfirmationToken) throws MessagingException {
        //MIME - HTML message
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(emailConfirmationToken.getUser().getUsername());
        helper.setSubject("Confirm you E-Mail - MFA Application Registration");
        helper.setText("<html>" +
                        "<body>" +
                        "<h2>Dear "+ emailConfirmationToken.getUser().getName() + ",</h2>"
                        + "<br/> We're excited to have you get started. " +
                        "Please click on below link to confirm your account."
                        + "<br/> "  + generateConfirmationLink(emailConfirmationToken.getToken())+"" +
                        "<br/> Regards,<br/>" +
                        "MFA Registration team" +
                        "</body>" +
                        "</html>"
                , true);

        sender.send(message);
    }

    private String generateConfirmationLink(String token){
        return "<a href=http://localhost:8080/confirm-email?token="+token+">Confirm Email</a>";
    }
}