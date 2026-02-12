package elms.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //To maintain order
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
}