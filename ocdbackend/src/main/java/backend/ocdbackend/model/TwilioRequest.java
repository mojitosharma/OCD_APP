package backend.ocdbackend.model;

import lombok.Data;

@Data
public class TwilioRequest {
    private final String toPhoneNumber;
    private final String fromPhoneNumber;
    private final String message;
}

