package weShare.sharehappy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class DonationPostComment {

    @Id
    @Column(name = "comment_id",insertable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String content;
    private String parentId;
    private Boolean deleted;
    private Long postId; //DonationPost는 간접적으로 참조함, comment들은 항상 DonationPost 엔티티를 조회한 다음에 연관된 comment들을 조회하기 떄문이다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    //부모 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "comment_id")
    private DonationPostComment parentComment;
    //자식 댓글
    @OneToMany(mappedBy = "parentComment")
    private List<DonationPostComment> childComments;
}
