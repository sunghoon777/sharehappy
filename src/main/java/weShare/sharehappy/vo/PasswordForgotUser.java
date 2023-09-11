package weShare.sharehappy.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordForgotUser {
    private String email;
    private String encodingRandomcode;
}
