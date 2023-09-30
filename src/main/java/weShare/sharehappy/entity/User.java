package weShare.sharehappy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import weShare.sharehappy.dto.user.UserSummary;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @Column(name = "user_id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    private String email; //이메일 변경은 불가임
    private String password;
    @Column(updatable = false)
    private LocalDateTime regdate; // 등록일은 변경될일은 없다

    public User(String email, String password, LocalDateTime regdate) {
        this.email = email;
        this.password = password;
        this.regdate = regdate;
    }

    public void changePassword(String newPassword){
        this.password = newPassword;
    }

}
