package weShare.sharehappy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;
import weShare.sharehappy.dto.post.DonationPostDetail;
import weShare.sharehappy.dto.post.DonationPostSummary;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
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
    private Boolean deleted;

    public DonationPost(String title, String content, BigDecimal targetAmount, BigDecimal currentAmount, LocalDate regdate, LocalDate enddate, String categoryName,Organization organization) {
        this.title = title;
        this.content = content;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.regdate = regdate;
        this.enddate = enddate;
        this.categoryName = categoryName;
        this.organization = organization;
        deleted = false;
    }

    public DonationPostSummary changeToDonationPostSummary(){
        String thumNailImageUrl = images.stream().filter(image -> image.getIsThumbnail()).findFirst().map(image -> image.getImageUrl()).get();
        return new DonationPostSummary(id,organization.getName(),title,thumNailImageUrl,currentAmount,fundPercentage);
    }

    public DonationPostDetail changeToDonationPostDetail(String categoryKrName){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new DonationPostDetail(id,title,content,targetAmount,currentAmount,fundPercentage,regdate.format(formatter),enddate.format(formatter),categoryName,categoryKrName,organization.getName(),organization.getIntroduce());
    }

    public void changeContent(String updateContent){
        this.content = updateContent;
    }

    public void plusCurrentAmount(BigDecimal amount){
        currentAmount = currentAmount.add(amount);

    }

    public void minusCurrentAmount(BigDecimal amount){
        currentAmount = currentAmount.subtract(amount);
    }

    public void chnagePostDeletedStatus(){
        deleted = true;
    }
}
