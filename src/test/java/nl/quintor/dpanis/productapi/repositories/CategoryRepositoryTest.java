package nl.quintor.dpanis.productapi.repositories;

import nl.quintor.dpanis.productapi.entities.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("classpath:application.test.properties")
@DataJpaTest
// Propagation never or it won't propagate exceptions to the test function
@Transactional(propagation = Propagation.NEVER)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void findByNameExists() {
        Category category = new Category();
        category.setName("test_category");
        category.setDescription("test description");
        repository.save(category);

        Optional<Category> optional = repository.findByName("test_category");
        assertNotNull(optional);
        assertTrue(optional.isPresent());
        assertEquals(category.getName(), optional.get().getName());
        assertEquals(category.getDescription(), optional.get().getDescription());
    }

    @Test
    void findByNameNotFound() {
        Category category = new Category();
        category.setName("test_category");
        category.setDescription("test description");
        repository.save(category);

        Optional<Category> optional = repository.findByName("test");
        assertNotNull(optional);
        assertFalse(optional.isPresent());
    }

    @Test
    void testDoubleName() {
        Category category1 = new Category();
        category1.setName("test_category");
        category1.setDescription("test_description");

        Category category2 = new Category();
        category2.setName("test_category");
        category2.setDescription("test2_description");
        repository.save(category1);
        assertThrows(DataIntegrityViolationException.class, () -> repository.save(category2));

        Optional<Category> after = repository.findById(category1.getId());
        assertTrue(after.isPresent());
        assertEquals(category1.getDescription(), after.get().getDescription());
    }
}