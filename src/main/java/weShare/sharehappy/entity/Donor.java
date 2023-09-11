package weShare.sharehappy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import weShare.sharehappy.dto.user.DonorSummary;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "donor_user")
public class Donor extends User{
    private String nickname;

    public Donor(String email, String password, LocalDateTime regdate, String nickname) {
        super(email, password, regdate);
        this.nickname = nickname;
    }

    public DonorSummary changeToDonorSummary(){
        return new DonorSummary(getEmail(),nickname,getRegdate());
    }
}
