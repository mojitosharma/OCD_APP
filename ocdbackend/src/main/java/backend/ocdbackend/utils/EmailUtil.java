package backend.ocdbackend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email, String otp){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Verify OTP");
        simpleMailMessage.setText("Hello, User your OTP is : " + otp + " Please verify within 1 minute");
        javaMailSender.send(simpleMailMessage);
    }
}