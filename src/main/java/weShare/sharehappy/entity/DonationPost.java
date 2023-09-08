package weShare.sharehappy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;
import weShare.sharehappy.dto.post.DonationPostSummary;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class DonationPost {

    @Id
    @Column(name = "post_id",insertable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Long targetAmount;
    private Long currentAmount;
    @Column(updatable = false)
    private LocalDateTime regdate;
    private LocalDateTime enddate;
    private String categoryName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;
    @OneToMany(mappedBy = "donationPost" ,cascade = CascadeType.ALL, fetch = FetchType.EAGER) //post는 항상 이미지를 가져와서 eager로 설정함
    @BatchSize(size = 80) // post를 8개씩가져오고 post는 최대 image를 10개씩 보유할 수 있어서 배치 사이즈 80으로 설정함
    private List<DonationPostImage> images;
    @Formula("current_amount*100/target_amount") //purcentage 계산을 위한 것
    private Double fundPercentage;

    public DonationPost(Long id, String title, String content, Long targetAmount, Long currentAmount, LocalDateTime regdate, LocalDateTime enddate, String categoryName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.regdate = regdate;
        this.enddate = enddate;
        this.categoryName = categoryName;
    }

    public DonationPostSummary changeToLisInfo(){
        String thumNailImageUrl = images.stream().filter(image -> image.getIsThumbnail()).findFirst().map(image -> image.getImageUrl()).get();
        return new DonationPostSummary(id,organization.getName(),title,thumNailImageUrl,currentAmount,fundPercentage);
    }


}
