package nl.quintor.dpanis.productapi.controllers.e2e;

import nl.quintor.dpanis.productapi.controllers.CategoryController;
import nl.quintor.dpanis.productapi.entities.Category;
import nl.quintor.dpanis.productapi.models.CategoryModel;
import nl.quintor.dpanis.productapi.repositories.CategoryRepository;
import nl.quintor.dpanis.productapi.repositories.PrivilegeRepository;
import nl.quintor.dpanis.productapi.repositories.RoleRepository;
import nl.quintor.dpanis.productapi.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.when;
import static nl.quintor.dpanis.productapi.constants.AuthenticationConstants.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryControllerE2ETest extends E2ETest {

    @Autowired
    CategoryController controller;
    @Autowired
    CategoryRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Autowired
    public CategoryControllerE2ETest(UserRepository userRepository, RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
        super(
                userRepository,
                roleRepository,
                privilegeRepository,
                PRIVILEGE_CATEGORY_LIST,
                PRIVILEGE_CATEGORY_GET,
                PRIVILEGE_CATEGORY_CREATE,
                PRIVILEGE_CATEGORY_UPDATE,
                PRIVILEGE_CATEGORY_DELETE
        );
    }

    @Test
    void testListNotLoggedIn() {
        when().get(url("category"))
                .then().statusCode(is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    void testListUnauthorized() {
        givenUnauthorized()
                .when().get(url("category"))
                .then().statusCode(is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    void testListEmpty() {
        givenAuthorized()
                .when().get(url("category"))
                .then().statusCode(is(HttpStatus.OK.value()))
                .body(equalTo("[]"));
    }

    @Test
    void testListOne() {
        Category category = repository.save(defaultCategory());

        givenAuthorized()
                .when().get(url("category"))
                .then().statusCode(is(HttpStatus.OK.value()))
                .body(
                        startsWith("[{"),
                        containsString(category.getName()),
                        containsString(category.getDescription()),
                        endsWith("}]")
                );
    }

    @Test
    void testGetNotLoggedIn() {
        when().get(url("category/1"))
                .then().statusCode(is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    void testGetUnauthorized() {
        givenUnauthorized()
                .when().get(url("category/1"))
                .then().statusCode(is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    void testGetNotExists() {
        givenAuthorized()
                .when().get(url("category/1"))
                .then().statusCode(is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGetSuccess() {
        Category category = repository.save(defaultCategory());
        givenAuthorized()
                .when().get(url("category/" + category.getId()))
                .then().statusCode(is(HttpStatus.OK.value()))
                .body(
                        startsWith("{"),
                        containsString(category.getId() + ""),
                        containsString(category.getName()),
                        containsString(category.getDescription()),
                        endsWith("}")
                );
    }


    @Test
    void testCreateNotLoggedIn() {
        when().put(url("category"))
                .then().statusCode(is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    void testCreateUnauthorized() {
        givenUnauthorized()
                .body(new CategoryModel("test category", "test category description"))
                .when().put(url("category"))
                .then().statusCode(is(HttpStatus.FORBIDDEN.value()));
        assertFalse(repository.findAll().iterator().hasNext());
    }

    @Test
    void testCreateSuccess() {
        CategoryModel model = new CategoryModel("test_category", "test description");
        assertFalse(repository.findAll().iterator().hasNext());
        givenAuthorized().body(model)
                .when().put(url("category"))
                .then().statusCode(is(HttpStatus.CREATED.value()))
                .body(
                        startsWith("{"),
                        containsString("\"id\":"),
                        containsString(model.getName()),
                        containsString(model.getDescription()),
                        endsWith("}")
                );
        assertTrue(repository.findAll().iterator().hasNext());
    }

    @Test
    void testCreateInvalidModel() {
        /*
         We only test this once, not with all the the constraints cause that is meant for model validation tests.
         This is purely to test that the response for an invalid model return 400 Bad Request.
         */
        CategoryModel model = new CategoryModel();
        givenAuthorized().body(model)
                .when().put(url("category"))
                .then().statusCode(is(HttpStatus.BAD_REQUEST.value()))
                .header("Content-Type", containsString("json"));
        assertFalse(repository.findAll().iterator().hasNext());
    }

    private Category defaultCategory() {
        Category category = new Category();
        category.setName("test_category");
        category.setDescription("test description");
        return category;
    }
}