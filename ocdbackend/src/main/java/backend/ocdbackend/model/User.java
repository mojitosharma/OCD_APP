package backend.ocdbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document(collection = "Users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private ObjectId user_id;
    private Name name;
    private int patient_number;
    private Date dob;
    private Date day_of_enrollment;
    private String gender;
    private String education;
    private String occupation;
    private String email;
    private String password;
    private int therapist_id;

}
