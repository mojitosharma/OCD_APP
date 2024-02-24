package backend.ocdbackend.service;

import backend.ocdbackend.model.Name;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final Name name;
    private final String email;
    private final String password;

}
