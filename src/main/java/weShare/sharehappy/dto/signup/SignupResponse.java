package weShare.sharehappy.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupResponse {
    private String email;
    private String nickname;
    private String successMessage;
}
