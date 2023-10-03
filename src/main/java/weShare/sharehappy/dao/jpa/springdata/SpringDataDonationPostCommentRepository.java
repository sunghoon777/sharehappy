package weShare.sharehappy.dao.jpa.springdata;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import weShare.sharehappy.entity.DonationPostComment;

import java.util.List;
import java.util.Optional;

public interface SpringDataDonationPostCommentRepository extends JpaRepository<DonationPostComment,Long> {

    @Query(value = "select c from DonationPostComment c join fetch c.user where c.postId = :postId and c.parentComment is null",
    countQuery = "select count(c) from DonationPostComment c where c.postId = :postId and c.parentComment is null")
    Page<DonationPostComment> findAllByPostIdSortDateAscWithUser(Long postId, Pageable pageable);

    @Query(value = "select count(c) from DonationPostComment c where c.postId = :postId and c.parentComment is null")
    Long countDonationPostComments(Long postId);

    Optional<DonationPostComment> findById(Long commentId);
    @Query(value = "select c from DonationPostComment c join fetch c.user where c.id = :commentId")
    Optional<DonationPostComment> findByIdWithUser(Long commentId);

    @Query(value = "select c from DonationPostComment c join fetch c.user where c.parentComment.id = :commentId")
    List<DonationPostComment> findChildCommentsWithUser(Long commentId);



}
