package weShare.sharehappy.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotNull
    private Boolean rememberEmail;
}
