package weShare.sharehappy.service.random;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import weShare.sharehappy.Exception.user.NoExistingUserException;
import weShare.sharehappy.dao.UserRepository;
import weShare.sharehappy.entity.User;
import weShare.sharehappy.vo.SmtpSettingInfo;
import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Optional;
import java.util.Properties;

@Service
public class RandomCodeSender {

    private SmtpSettingInfo smtpInfo;
    private final UserRepository repository;
    private final RandomStringService randomStringService;

    @Autowired
    public RandomCodeSender(UserRepository repository,RandomStringService randomStringService){
        this.repository = repository;
        this.randomStringService = randomStringService;
    }

    @PostConstruct
    private void init() throws IOException{
        Resource resource = new ClassPathResource("setting/smtp.setting");
        ObjectMapper objectMapper = new ObjectMapper();
        smtpInfo = objectMapper.readValue(resource.getFile(),SmtpSettingInfo.class);
    }

    //인증 절차 서비스1, email 존재 확인 후 랜덤 코드 이메일 전송
    public String sendRandomCode(String email) throws MessagingException{
        Optional<User> user = repository.findByEmail(email);
        //세션에 randomCode 저장
        String randomCode = sendCode(user.orElseThrow(()->new NoExistingUserException()));
        return randomCode;
    }

    //랜덤 코드 이메일 전송 기능
    private String sendCode(User user) throws MessagingException {
        String host = smtpInfo.getHost();
        String admin = smtpInfo.getAdmin();
        String password = smtpInfo.getPassword();
        String subject = smtpInfo.getSubject();
        String format = smtpInfo.getFormat();

        //host 설정
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");

        //인증 설정
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(admin, password);
            }
        });

        //메시지 설정
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(admin));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
        message.setSubject(subject);

        //랜덤 서비스 얻어오고 컨텐츠 메시지에 설정
        String randomCode = randomStringService.getRandomCode();
        String content = String.format(format,user.getEmail(),randomCode);
        message.setContent(content,"text/html;charset=utf-8");

        //메시지 전송
        Transport.send(message);
        return randomCode;
    }


}
