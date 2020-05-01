package nl.quintor.dpanis.productapi.controllers.e2e;

import nl.quintor.dpanis.productapi.entities.Role;
import nl.quintor.dpanis.productapi.entities.User;
import nl.quintor.dpanis.productapi.models.RegistrationModel;
import nl.quintor.dpanis.productapi.repositories.PrivilegeRepository;
import nl.quintor.dpanis.productapi.repositories.RoleRepository;
import nl.quintor.dpanis.productapi.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthenticationControllerE2ETest extends E2ETest {

    @Autowired
    AuthenticationControllerE2ETest(UserRepository userRepo, RoleRepository roleRepo, PrivilegeRepository privilegeRepo) {
        super(userRepo, roleRepo, privilegeRepo, "none");
    }

    @Test
    void testRegisterSuccess() {
        // Have to create default role
        Role role = roleRepository.save(new Role("test_default", true, null, null));
        RegistrationModel model = new RegistrationModel("Test", "User",
                "test@user.com", "password1!", "password1!");
        givenJson().body(model)
                .when().put(url("authentication/register"))
                .then().statusCode(is(HttpStatus.CREATED.value()))
                .body(
                        startsWith("{"),
                        containsString(model.getFirstName()),
                        containsString(model.getLastName()),
                        containsString(model.getEmail()),
                        not(containsString("\"password\"")),
                        endsWith("}")
                );
        User user = userRepository.findByEmail(model.getEmail());
        assertNotNull(user);
        userRepository.delete(user);
        roleRepository.delete(role);
    }

    @Test
    void testRegisterEmailExists() {

    }
}
