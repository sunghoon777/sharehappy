package weShare.sharehappy.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class DonationPost {

    private int postId;
    private int organizationId;
    private String title;
    private String content;
    private String imageId;
    private long targetAmount;
    private long currentAmount;
    private String category;
    private LocalDateTime regDate;
    private LocalDateTime endDate;

    public DonationPost(int postId, int organizationId, String title, String content, String imageId, long targetAmount, long currentAmount, String category, LocalDateTime regDate, LocalDateTime endDate) {
        this.postId = postId;
        this.organizationId = organizationId;
        this.title = title;
        this.content = content;
        this.imageId = imageId;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.category = category;
        this.regDate = regDate;
        this.endDate = endDate;
    }
}
