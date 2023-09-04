package weShare.sharehappy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Organization {

    @Id
    @Column(name = "organization_id",insertable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String representativeName;
    private String thumbnailUrl;
    private String phonenumber;
    private String address;
    private String businessRegnumber;
    private String introduce;
    private String homepageUrl;

}
