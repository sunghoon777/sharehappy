package weShare.sharehappy.dao.jpa;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import weShare.sharehappy.dao.DonationPostCommentRepository;
import weShare.sharehappy.dao.jpa.springdata.SpringDataDonationPostCommentRepository;
import weShare.sharehappy.entity.DonationPostComment;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class DonationPostCommentJpaRespository implements DonationPostCommentRepository {

    private final SpringDataDonationPostCommentRepository repository;

    @Override
    public DonationPostComment save(DonationPostComment donationPostComment) {
        return repository.save(donationPostComment);
    }

    @Override
    public void delete(DonationPostComment donationPostComment) {
        repository.delete(donationPostComment);
    }

    @Override
    public Page<DonationPostComment> findAllByPostIdSortRecentCommentWithUserAndChildComments(int page, int size, Long postId) {
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by(Sort.Direction.ASC,"date"));
        Page<DonationPostComment> result = repository.findAllByPostIdSortDateAscWithUser(postId,pageRequest);
        //자식 댓글 수를 위한 자식 댓글 로딩, 배치 사이즈를 설정하여 n+1 문제 방지
        result.getContent().stream().map(c->c.getChildComments());
        return result;
    }

    @Override
    public Optional<DonationPostComment> findById(Long commentId) {
        return repository.findById(commentId);
    }

    @Override
    public Optional<DonationPostComment> findByIdWithUser(Long commentId) {
        return repository.findByIdWithUser(commentId);
    }


    @Override
    public List<DonationPostComment> findChildCommentsWithUser(Long commentId) {
        return repository.findChildCommentsWithUser(commentId);
    }

    @Override
    public Long countDonationPostComments(Long postId) {
        return repository.countDonationPostComments(postId);
    }
}
