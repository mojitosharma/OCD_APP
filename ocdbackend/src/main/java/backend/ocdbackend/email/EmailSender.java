package backend.ocdbackend.email;

public interface EmailSender {
    void send(String to, String email);
}