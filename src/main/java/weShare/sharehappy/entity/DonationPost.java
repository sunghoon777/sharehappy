package weShare.sharehappy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;
import weShare.sharehappy.dto.post.DonationPostDetail;
import weShare.sharehappy.dto.post.DonationPostSummary;

import javax.persistence.*;
import java.time.LocalDate;
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
    private LocalDate regdate;
    private LocalDate enddate;
    private String categoryName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;
    @OneToMany(mappedBy = "donationPost" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 80) // post를 8개씩가져오고 post는 최대 image를 10개씩 보유할 수 있어서 배치 사이즈 80으로 설정함
    private List<DonationPostImage> images;
    @Formula("current_amount*100/target_amount") //purcentage 계산을 위한 것
    private Double fundPercentage;

    public DonationPost(String title, String content, Long targetAmount, Long currentAmount, LocalDate regdate, LocalDate enddate, String categoryName,Organization organization) {
        this.title = title;
        this.content = content;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.regdate = regdate;
        this.enddate = enddate;
        this.categoryName = categoryName;
        this.organization = organization;
    }

    public DonationPostSummary changeToLisInfo(){
        String thumNailImageUrl = images.stream().filter(image -> image.getIsThumbnail()).findFirst().map(image -> image.getImageUrl()).get();
        return new DonationPostSummary(id,organization.getName(),title,thumNailImageUrl,currentAmount,fundPercentage);
    }

    public DonationPostDetail changeToDonationPostDetail(){
        String thumbnailUrl = images.stream().filter(image -> image.getIsThumbnail()).map(f -> f.getImageUrl()).findFirst().get();
        return new DonationPostDetail(id,title,content,targetAmount,currentAmount,regdate,enddate,categoryName,thumbnailUrl,organization.getName(),organization.getIntroduce());
    }

    public void changeContent(String updateContent){
        this.content = updateContent;
    }

}
