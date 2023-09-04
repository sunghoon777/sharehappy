package weShare.sharehappy.dao.jpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import weShare.sharehappy.dao.DonationPostCategoryRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
class DonationPostCategoryJpaRepositoryTest {

    @Autowired
    DonationPostCategoryRepository repository;

    @Test
    void 카테고리이름으로찾기성공() {
        Assertions.assertThat(repository.findByCategoryName("동물").getName()).isEqualTo("동물");
    }

    @Test
    void 카테고리이름으로찾기실패() {
        Assertions.assertThatThrownBy(()->{
            Optional.of(repository.findByCategoryName("ss"));
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void 카테고리다가져오기() {
        List<String> categoryNameList = Arrays.asList("전체","아동청소년","노인","장애인","다문화","지구촌","가족여성","환경","동물","기타");
        List<String> result = repository.findAllSortByCategoryOrderAsc().stream().map(entity -> entity.getKrName()).collect(Collectors.toList());
        Assertions.assertThat(result).containsExactlyElementsOf(categoryNameList);
    }
}