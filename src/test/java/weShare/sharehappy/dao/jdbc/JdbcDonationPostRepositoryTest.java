package weShare.sharehappy.dao.jdbc;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JdbcDonationPostRepositoryTest {

    @Autowired
    private JdbcDonationPostRepository repository;

    //post 개수 세기, 0이상이면 됨
    @Test
    void countDonationPost() {
        Assertions.assertThat(repository.countDonationPost() >= 0).isTrue();
    }
}