package nl.quintor.dpanis.productapi.repositories;

import nl.quintor.dpanis.productapi.entities.CartItem;
import nl.quintor.dpanis.productapi.entities.Category;
import nl.quintor.dpanis.productapi.entities.Product;
import nl.quintor.dpanis.productapi.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@TestPropertySource("classpath:application.test.properties")
@DataJpaTest
// Propagation never or it won't propagate exceptions to the test function
@Transactional(propagation = Propagation.NEVER)
class CartItemRepositoryTest {


    @Autowired
    CartItemRepository repository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testSearchByUserId() {
        User user = userRepository.save(new User("Test", "User", "test@user.com", "abc", true, true, null, null));
        Category category = categoryRepository.save(new Category("test", "test description", null));
        Product product = productRepository.save(new Product("test product", "test product description", 100D, 100, category, null));

        repository.save(new CartItem(new CartItem.CartItemId(user, product), 1));
        Collection<CartItem> items = repository.findByIdUserId(user.getId());
        assertFalse(items.isEmpty());
        assertEquals(1, items.size());
    }

    @Test
    void testSearchByUserAndProduct() {
        User user = userRepository.save(new User("Test", "User", "test@user.com", "abc", true, true, null, null));
        Category category = categoryRepository.save(new Category("test", "test description", null));
        Product product = productRepository.save(new Product("test product", "test product description", 100D, 100, category, null));

        repository.save(new CartItem(new CartItem.CartItemId(user, product), 1));
        Optional<CartItem> optional = repository.findByIdUserIdAndIdProductId(user.getId(), product.getId());
        assertTrue(optional.isPresent());
        assertEquals(1, optional.get().getAmount());
    }

}