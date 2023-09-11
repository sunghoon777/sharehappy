package weShare.sharehappy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "organization_user")
public class Organization extends User{

    private String name;
    private String representativeName;
    private String thumbnailUrl;
    private String phonenumber;
    private String address;
    private String businessRegnumber;
    private String introduce;
    private String homepageUrl;

    public Organization(String email, String password, LocalDateTime regdate, String name, String representativeName, String thumbnailUrl, String phonenumber, String address, String businessRegnumber, String introduce, String homepageUrl) {
        super(email, password, regdate);
        this.name = name;
        this.representativeName = representativeName;
        this.thumbnailUrl = thumbnailUrl;
        this.phonenumber = phonenumber;
        this.address = address;
        this.businessRegnumber = businessRegnumber;
        this.introduce = introduce;
        this.homepageUrl = homepageUrl;
    }
}
