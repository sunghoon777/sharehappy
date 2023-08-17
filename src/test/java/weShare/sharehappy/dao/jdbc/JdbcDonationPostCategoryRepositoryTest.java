package weShare.sharehappy.dao.jdbc;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import weShare.sharehappy.entity.DonationPostCategory;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
class JdbcDonationPostCategoryRepositoryTest {


    @Autowired
    private JdbcDonationPostCategoryRepository repository;

    @Test
    void selectAll() {
        List<String> list = repository.selectAll().stream().map(entity -> entity.getName()).collect(Collectors.toList());
        Assertions.assertThat(list).containsOnly("가족여성","기타","노인","다문화","동물","아동청소년","장애인","전체","지구촌","환경");
        Assertions.assertThat(list.size()).isEqualTo(10);
    }

    @Test
    void selectCategorySuccess() {
        Optional<DonationPostCategory> result = repository.selectCategory("동물");
        Assertions.assertThat(result.get().getName()).isEqualTo("동물");
    }

    @Test
    void selectCategoryFail(){
        Optional<DonationPostCategory> result = repository.selectCategory("라라라라라");
        Assertions.assertThatThrownBy(()->{
            result.get();
        }).isInstanceOf(NoSuchElementException.class);
    }
}