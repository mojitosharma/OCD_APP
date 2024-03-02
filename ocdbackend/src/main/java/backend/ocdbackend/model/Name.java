package backend.ocdbackend.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Name {
    private String first_name;
    private String last_name;

    public Name(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }
}

