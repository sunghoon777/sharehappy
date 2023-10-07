package weShare.sharehappy.dao.jpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import weShare.sharehappy.dao.DonorRepository;
import weShare.sharehappy.entity.Donor;

import java.time.LocalDateTime;

@SpringBootTest
class DonorJpaRepositoryTest {

    @Autowired
    private DonorRepository repository;

    @Test
    void 기부자계정새로저장성공() {
        Assertions.assertThat(repository.save(new Donor("test100@test.com","fasafsq",LocalDateTime.now(),"tester100")).getId()).isNotNull();
    }

    @Test
    void 기부자계정새로저장실패() {
        Assertions.assertThatThrownBy(()->{
            repository.save(new Donor("test100@test.com","fasafsq",LocalDateTime.now(),"tester100"));
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void 유저찾기성공() {
        Assertions.assertThat(repository.findByEmail("test1@test.com").get().getEmail()).isEqualTo("test1@test.com");
    }

    @Test
    void 유저찾기실패() {
        Assertions.assertThat(repository.findByEmail("tes2222@test.com")).isNull();
    }
}