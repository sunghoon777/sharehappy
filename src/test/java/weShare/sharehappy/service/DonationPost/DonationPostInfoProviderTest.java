package weShare.sharehappy.service.DonationPost;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import weShare.sharehappy.dao.DonationPostCategoryRepository;
import weShare.sharehappy.dao.DonationPostRepository;


@SpringBootTest
class DonationPostInfoProviderTest {

    @Autowired
    private DonationPostCategoryRepository categoryRepository;
    @Autowired
    private DonationPostRepository postRepository;



    @Test
    void groupRangeTestSuccess() {

    }


    @Test
    void groupRangeTestFail() {

    }
}