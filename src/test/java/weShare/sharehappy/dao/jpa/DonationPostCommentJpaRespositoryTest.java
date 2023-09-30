package weShare.sharehappy.dao.jpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import weShare.sharehappy.dao.jpa.springdata.SpringDataDonationPostCommentRepository;
import weShare.sharehappy.entity.DonationPostComment;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DonationPostCommentJpaRespositoryTest {

    @Autowired
    SpringDataDonationPostCommentRepository repository;

    /*
    페이징 테스트는 정렬 기준 속성 값이 다 다른 데이터로 테스트함, 2차로 정렬하는 쿼리는 하지 않아서 같으면 순서가 살짝 달라질수 있어서 테스트하기 애매함
    */
    @Test
    void 페이징확인테스트날짜오름차순으로() {
        //전체 개수 가져오기
        List<DonationPostComment> allList= repository.findAllByPostIdSortDateAscWithUser(32L,
                        PageRequest.of(0,100, Sort.by(Sort.Direction.ASC,"date")))
                .getContent();
        //특정 페이지 가져옴, commentId만 뽑아서 리스트에 넣음
        List<Long> result = repository.findAllByPostIdSortDateAscWithUser(32L,
                PageRequest.of(1,5, Sort.by(Sort.Direction.ASC,"date")))
                .getContent().stream().map(c -> c.getId()).collect(Collectors.toList());
        //직접 페이징하고 commentId만 뽑아서 리스트에 넣음
        List<Long> expect = allList.subList(5,10).stream().map(c -> c.getId()).collect(Collectors.toList());
        //비교
        Assertions.assertThat(result).containsExactlyElementsOf(expect);
    }

    @Test
    void 페이지초과(){
        Assertions.assertThat(repository.findAllByPostIdSortDateAscWithUser(32L, PageRequest.of(10,100, Sort.by(Sort.Direction.ASC,"date")))
                .getContent().size()).isEqualTo(0);
    }

    @Test
    void 자식댓글테스트해당댓글을부모로가진게없을경우(){
        Assertions.assertThat(repository.findChildCommentsWithUser(50L).size()).isEqualTo(0);
    }
}