package weShare.sharehappy.dao;


import com.amazonaws.services.apigateway.model.Op;
import org.springframework.data.domain.Page;
import weShare.sharehappy.entity.DonationPostComment;

import java.util.List;
import java.util.Optional;

public interface DonationPostCommentRepository {
    DonationPostComment save(DonationPostComment donationPostComment);
    void delete(DonationPostComment donationPostComment);
    Page<DonationPostComment> findAllByPostIdSortRecentCommentWithUserAndChildComments(int page, int size, Long postId);

    Optional<DonationPostComment> findById(Long commentId);
    Optional<DonationPostComment> findByIdWithUser(Long commentId);
    List<DonationPostComment> findChildCommentsWithUser(Long commentId);

    Long countDonationPostComments(Long postId);
}
