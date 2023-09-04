package weShare.sharehappy.dao.jpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import weShare.sharehappy.constant.PostSortCriteria;
import weShare.sharehappy.dao.DonationPostRepository;
import weShare.sharehappy.entity.DonationPost;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DonationPostJpaRepositoryTest {

    @Autowired
    DonationPostRepository repository;
    private String category = "child";

    @Test
    void save() {
    }

    @Test
    void findById() {
    }

    @Test
    void 카운트테스트(){
        System.out.println(repository.counDonationPost());
    }

    /*
    정렬 테스트는 값이 다 다른 데이터로 테스트함, 같으면 순서가 살짝 달라질수 있어서 테스트하기 애매했음
     */
    
    @Test
    void 적정페이지퍼센테이지높은순으로() {
        //전체 개수를 다가져옴
        List<DonationPost> allList = repository.findByCategorySortPostSortCriteria(PostSortCriteria.ASCENDING_FUND_PERCENTAGE,0,100,category);
        //특정 페이지 가져옴 postId만 담는 리스트로 변환
        List<Long> result = repository.findByCategorySortPostSortCriteria(PostSortCriteria.ASCENDING_FUND_PERCENTAGE,1,8,category).stream().map(post -> post.getId()).collect(Collectors.toList());
        //직접 페이징을 해서 리스트 뽑아옴 postId만 다믄 리스트로 변환
        List<Long> expect = allList.subList(8,16).stream().map(post -> post.getId()).collect(Collectors.toList());;
        //비교
        Assertions.assertThat(result).containsExactlyElementsOf(expect);
    }

    @Test
    void 적정페이지퍼센티이지낮은순으로() {
        //전체 개수를 다가져옴
        List<DonationPost> allList = repository.findByCategorySortPostSortCriteria(PostSortCriteria.DESCENDING_FUND_PERCENTAGE,0,100,category);
        //특정 페이지 가져옴 postId만 담는 리스트로 변환
        List<Long> result = repository.findByCategorySortPostSortCriteria(PostSortCriteria.DESCENDING_FUND_PERCENTAGE,1,8,category).stream().map(post -> post.getId()).collect(Collectors.toList());
        //직접 페이징을 해서 리스트 뽑아옴 postId만 다믄 리스트로 변환
        List<Long> expect = allList.subList(8,16).stream().map(post -> post.getId()).collect(Collectors.toList());;
        //비교
        Assertions.assertThat(result).containsExactlyElementsOf(expect);
    }

    @Test
    void 적정페이지최신글순() {
        //전체 개수를 다가져옴
        List<DonationPost> allList = repository.findByCategorySortPostSortCriteria(PostSortCriteria.DESCENDING_REG_DATE,0,100,category);
        //특정 페이지 가져옴 postId만 담는 리스트로 변환
        List<Long> result = repository.findByCategorySortPostSortCriteria(PostSortCriteria.DESCENDING_REG_DATE,1,8,category).stream().map(post -> post.getId()).collect(Collectors.toList());
        //직접 페이징을 해서 리스트 뽑아옴 postId만 다믄 리스트로 변환
        List<Long> expect = allList.subList(8,16).stream().map(post -> post.getId()).collect(Collectors.toList());;
        //비교
        Assertions.assertThat(result).containsExactlyElementsOf(expect);
    }

    @Test
    void 적정페이지종료임박순() {
        //전체 개수를 다가져옴
        List<DonationPost> allList = repository.findByCategorySortPostSortCriteria(PostSortCriteria.ASCENDING_END_DATE,0,100,category);
        //특정 페이지 가져옴 postId만 담는 리스트로 변환
        List<Long> result = repository.findByCategorySortPostSortCriteria(PostSortCriteria.ASCENDING_END_DATE,1,8,category).stream().map(post -> post.getId()).collect(Collectors.toList());
        //직접 페이징을 해서 리스트 뽑아옴 postId만 다믄 리스트로 변환
        List<Long> expect = allList.subList(8,16).stream().map(post -> post.getId()).collect(Collectors.toList());;
        //비교
        Assertions.assertThat(result).containsExactlyElementsOf(expect);
    }

    @Test
    void 적정페이지모금액많은순() {
        //전체 개수를 다가져옴
        List<DonationPost> allList = repository.findByCategorySortPostSortCriteria(PostSortCriteria.DESCENDING_CURRENT_AMOUNT,0,100,category);
        //특정 페이지 가져옴 postId만 담는 리스트로 변환
        List<Long> result = repository.findByCategorySortPostSortCriteria(PostSortCriteria.DESCENDING_CURRENT_AMOUNT,1,8,category).stream().map(post -> post.getId()).collect(Collectors.toList());
        //직접 페이징을 해서 리스트 뽑아옴 postId만 다믄 리스트로 변환
        List<Long> expect = allList.subList(8,16).stream().map(post -> post.getId()).collect(Collectors.toList());;
        //비교
        Assertions.assertThat(result).containsExactlyElementsOf(expect);
    }

    @Test
    void 적정페이지모금액적은순() {
        //전체 개수를 다가져옴
        List<DonationPost> allList = repository.findByCategorySortPostSortCriteria(PostSortCriteria.ASCENDING_CURRENT_AMOUNT,0,100,category);
        //특정 페이지 가져옴 postId만 담는 리스트로 변환
        List<Long> result = repository.findByCategorySortPostSortCriteria(PostSortCriteria.ASCENDING_CURRENT_AMOUNT,1,8,category).stream().map(post -> post.getId()).collect(Collectors.toList());
        //직접 페이징을 해서 리스트 뽑아옴 postId만 다믄 리스트로 변환
        List<Long> expect = allList.subList(8,16).stream().map(post -> post.getId()).collect(Collectors.toList());;
        //비교
        Assertions.assertThat(result).containsExactlyElementsOf(expect);
    }
    
    @Test
    void 페이지초과(){
        Assertions.assertThat(repository.findByCategorySortPostSortCriteria(PostSortCriteria.DESCENDING_FUND_PERCENTAGE,4,8,category).size()).isEqualTo(0);
    }
}