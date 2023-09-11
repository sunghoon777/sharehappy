package weShare.sharehappy.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class SmtpSettingInfo {
    private String host;
    private String admin;
    private String password;
    private String subject;
    private String format;
}
