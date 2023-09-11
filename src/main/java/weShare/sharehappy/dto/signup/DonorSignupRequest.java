package weShare.sharehappy.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import weShare.sharehappy.validation.annotation.PasswordConfirm;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PasswordConfirm
public class DonorSignupRequest {

    @Email
    @NotEmpty
    private String email;
    //비밀번호(숫자,문자,특수문자(!,@) 포함 8~15 자리)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@])[0-9a-zA-Z!@]{8,15}$")
    private String password;
    private String passwordConfirm;
    //닉네임 2자부터 16자까지 숫자, 영어, 한국어
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,16}$")
    private String nickname;

}
