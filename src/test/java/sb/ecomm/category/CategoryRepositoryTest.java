package sb.ecomm.category;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(
        properties = {"spring.jpa.properties.javax.persistence.validation.mode=none"}
)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository underTest;

    @Test
    void findsCategoryByName() {
        Category category = new Category();
        category.setName("TestCategory");
        category.setDescription("test description");

        underTest.save(category);

        Optional<Category> optionalCategory = underTest.findByName("TestCategory");

        assertThat(optionalCategory).isPresent().hasValueSatisfying(c -> assertThat(c).isEqualTo(category));
    }


    @Test
    void doesNotFindCategoryIfNameDoesNotExist() {
        Category category = new Category();
        category.setName("TestCategory1");
        category.setDescription("test description");

        underTest.save(category);

        Optional<Category> optionalCategory = underTest.findByName("TestCategory");

        assertThat(optionalCategory).isNotPresent();
    }
}