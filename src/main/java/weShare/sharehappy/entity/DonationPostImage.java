package weShare.sharehappy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class DonationPostImage {

    @Id
    @Column(name = "image_id",insertable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    @Column(updatable = false)
    private LocalDateTime regdate;
    private Boolean isThumbnail;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donation_post_id")
    private DonationPost donationPost;

    public DonationPostImage(String imageUrl, LocalDateTime regdate, Boolean isThumbnail, DonationPost donationPost) {
        this.imageUrl = imageUrl;
        this.regdate = regdate;
        this.isThumbnail = isThumbnail;
        this.donationPost = donationPost;
    }
}
