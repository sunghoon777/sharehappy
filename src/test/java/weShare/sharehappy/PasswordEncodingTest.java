package weShare.sharehappy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncodingTest {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void 암호화후입력성공(){
        String password = "test1234!@";
        String digest = passwordEncoder.encode(password);
        String inputPassword = "test1234!@";
        Assertions.assertThat(passwordEncoder.matches(inputPassword,digest)).isTrue();
    }


    @Test
    public void 암호화후입력실패(){
        String password = "test1234!@";
        String digest = passwordEncoder.encode(password);
        String inputPassword = "test12!@";
        System.out.println(digest);
        Assertions.assertThat(passwordEncoder.matches(inputPassword,digest)).isFalse();
    }
}
