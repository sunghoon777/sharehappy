package weShare.sharehappy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import weShare.sharehappy.dto.comment.DonationPostChildCommentSummary;
import weShare.sharehappy.dto.comment.DonationPostCommentSummary;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private Long postId; //DonationPost는 간접적으로 참조함, comment들은 항상 DonationPost 엔티티를 조회한 다음에 연관된 comment들을 조회하기 떄문이다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    //부모 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "comment_id")
    private DonationPostComment parentComment;
    //자식 댓글
    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
    @BatchSize(size = 100) //계산할수는 없지만 댓글을 10개씩 가져오고 댓글이 평균적으로 대댓글을 10개씩 가진다 가졍하여 배치 사이즈 100으로 설정하였음
    private List<DonationPostComment> childComments;

    public DonationPostComment(LocalDateTime date, String content, Long postId, User user) {
        this.date = date;
        this.content = content;
        this.postId = postId;
        this.user = user;
    }

    public DonationPostComment(LocalDateTime date, String content, Long postId, User user, DonationPostComment parentComment) {
        this.date = date;
        this.content = content;
        this.postId = postId;
        this.user = user;
        this.parentComment = parentComment;
    }

    public void changeContent(String newContent){
        this.content = newContent;
    }


    public DonationPostCommentSummary changeToSummary(){
        String name = "";
        if(user instanceof Organization){
            name = ((Organization) user).getName();
        }
        else if(user instanceof Donor){
            name = ((Donor) user).getNickname();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return new DonationPostCommentSummary(id,name,date.format(formatter),content,childComments.size());
    }

    public DonationPostChildCommentSummary changeToChildSummary(){
        String name = "";
        if(user instanceof Organization){
            name = ((Organization) user).getName();
        }
        else if(user instanceof Donor){
            name = ((Donor) user).getNickname();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return new DonationPostChildCommentSummary(id,name,date.format(formatter),content);
    }

}
