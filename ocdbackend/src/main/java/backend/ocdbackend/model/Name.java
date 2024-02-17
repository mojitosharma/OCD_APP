package backend.ocdbackend.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Name {
    private String first_name;
    private String last_name;

}

