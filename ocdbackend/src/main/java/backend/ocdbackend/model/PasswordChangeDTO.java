package backend.ocdbackend.model;
import lombok.Data;


@Data
public class PasswordChangeDTO {

    private String email;
    private String oldPassword;
    private String newPassword;

}
