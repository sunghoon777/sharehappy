package weShare.sharehappy.service.comment;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weShare.sharehappy.Exception.*;
import weShare.sharehappy.constant.PageSize;
import weShare.sharehappy.dao.DonationPostCommentRepository;
import weShare.sharehappy.dao.DonationPostRepository;
import weShare.sharehappy.dao.UserRepository;
import weShare.sharehappy.dto.comment.*;
import weShare.sharehappy.dto.post.DonationPostSummary;
import weShare.sharehappy.entity.DonationPostComment;
import weShare.sharehappy.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DonationPostCommentManager {

    private final DonationPostRepository postRepository;
    private final DonationPostCommentRepository commentRepository;
    private final UserRepository userRepository;

    public DonationPostCommentsPageInfo getPostComments(DonationPostCommentsRequest request){
        postRepository.findById(request.getPostId()).orElseThrow(()->new NoExistingDonationPost());
        int sizePerPage = PageSize.COMMENT_PAGE_SIZE.getSize();
        Page<DonationPostComment> donationPostCommentPage = commentRepository
                .findAllByPostIdSortRecentCommentWithUserAndChildComments(request.getPage(),sizePerPage,request.getPostId());
        if(donationPostCommentPage.isEmpty()){
            throw new NoExistingCommentsInPage(request.getPage());
        }
        List<DonationPostCommentSummary> donationPostCommentSummaries = donationPostCommentPage.getContent().stream()
                .map(c -> c.changeToSummary())
                .collect(Collectors.toList());
        int totalPage = donationPostCommentPage.getTotalPages();
        int startPage = request.getPage()/sizePerPage*sizePerPage;
        int lastPage = (startPage+4)>(totalPage-1)?(totalPage-1):(startPage+4);
        return new DonationPostCommentsPageInfo(startPage,lastPage,request.getPage(),donationPostCommentSummaries);
    }

    public DonationPostCommentsPageInfo getPostLastPageComments(Long postId){
        postRepository.findById(postId).orElseThrow(()->new NoExistingDonationPost());
        double count = commentRepository.countDonationPostComments(postId);
        int sizePerPage = PageSize.COMMENT_PAGE_SIZE.getSize();
        int pageListSize = PageSize.PAGE_LIST_SIZE.getSize();
        int lastPage = (int)Math.ceil(count/ sizePerPage)-1;
        int startPage = lastPage/pageListSize*pageListSize;
        int currentPage = lastPage;
        List<DonationPostCommentSummary> commentSummaries = commentRepository.findAllByPostIdSortRecentCommentWithUserAndChildComments(lastPage,sizePerPage,postId).getContent().stream()
                .map(c -> c.changeToSummary())
                .collect(Collectors.toList());
        return new DonationPostCommentsPageInfo(startPage,lastPage,currentPage,commentSummaries);
    }

    public List<DonationPostChildCommentSummary> getChildComments(Long id){
        return commentRepository.findChildCommentsWithUser(id).stream().map(c -> c.changeToChildSummary()).collect(Collectors.toList());
    }

    @Transactional
    public DonationPostCommentSummary addComment(DonationPostCommentAddRequest addRequest, String email){
        postRepository.findById(addRequest.getPostId())
                .orElseThrow(()->new NoExistingDonationPost());
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new NoExistingUserException());
        DonationPostComment donationPostComment = new DonationPostComment(LocalDateTime.now(),addRequest.getContent(),addRequest.getPostId(),user);
        commentRepository.save(donationPostComment);
        return donationPostComment.changeToSummary();
    }

    @Transactional
    public void removeComment(Long commentId,String email){
        DonationPostComment comment = commentRepository.findByIdWithUser(commentId)
                .orElseThrow(()->new NoExistingDonationPostComment());
        if(comment.getUser().getEmail().equals(email)){
            throw new WrongAccessDonationPostCommentException();
        }
        commentRepository.delete(comment);
    }

    @Transactional
    public DonationPostCommentSummary updateComment(DonationPostCommentUpdateRequest updateRequest, String email){
        DonationPostComment comment = commentRepository.findByIdWithUser(updateRequest.getCommentId())
                .orElseThrow(()->new NoExistingDonationPostComment());
        if(comment.getUser().getEmail().equals(email)){
            throw new WrongAccessDonationPostCommentException();
        }
        comment.changeContent(updateRequest.getContent());
        return comment.changeToSummary();
    }

    @Transactional
    public DonationPostChildCommentSummary addChildComment(DonationPostCommentReplyAddRequest addRequest,String email){
        postRepository.findById(addRequest.getPostId())
                .orElseThrow(()->new NoExistingDonationPost());
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new NoExistingUserException());
        DonationPostComment parentComment = commentRepository.findByIdWithUser(addRequest.getParentId())
                .orElseThrow(()->new NoExistingDonationPostParentComment());
        DonationPostComment comment = new DonationPostComment(LocalDateTime.now(),addRequest.getContent(),addRequest.getPostId(),user,parentComment);
        commentRepository.save(comment);
        return comment.changeToChildSummary();
    }

}
