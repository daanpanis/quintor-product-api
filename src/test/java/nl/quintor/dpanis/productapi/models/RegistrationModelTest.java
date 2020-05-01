package nl.quintor.dpanis.productapi.models;

import nl.quintor.dpanis.productapi.validation.FieldsValueMatch;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static nl.quintor.dpanis.productapi.TestUtils.assertHasViolation;
import static nl.quintor.dpanis.productapi.TestUtils.assertNoViolation;

/**
 * Unit test
 */
class RegistrationModelTest extends ModelTest<RegistrationModel> {

    @Test
    public void testSuccess() {
        assertNoViolation(validator, defaultModel());
    }

    @Test
    void testFirstNameConstraints() {
        testField(model -> model.setFirstName(null), "firstName", NotNull.class);
        sizeTest("firstName", RegistrationModel::setFirstName, 2, 50);
    }

    @Test
    void testLastNameConstraints() {
        testField(model -> model.setLastName(null), "lastName", NotNull.class);
        sizeTest("lastName", RegistrationModel::setLastName, 2, 50);
    }

    @Test
    void testEmailConstraints() {
        testField(model -> model.setEmail(null), "email", NotNull.class);
        testField(model -> model.setEmail(null), "email", NotBlank.class);
        assertIncorrectEmail("test@");
        assertIncorrectEmail("test");
        assertIncorrectEmail("test@test.");
    }

    @Test
    void testPasswordConstraints() {
        testField(model -> model.setPassword(null), "password", NotNull.class);
        testField(model -> model.setConfirmPassword(null), "confirmPassword", NotNull.class);
        // No number
        assertIncorrectPassword("testpassword!");
        // No special character
        assertIncorrectPassword("testpassword1");
        // No letters
        assertIncorrectPassword("123456789!");
        // Shorter than 8
        assertIncorrectPassword("a1!");
        // Only letters
        assertIncorrectPassword("abcdefghijk");
        // Only special characters
        assertIncorrectPassword("!@#$%^&*()");
        // Weird characters
        assertIncorrectPassword("Password123!ѰΘ☺♥");

        testField(model -> model.setConfirmPassword("nomatch"), "", FieldsValueMatch.class);
    }

    private void assertIncorrectPassword(String password) {
        RegistrationModel model = defaultModel();
        model.setPassword(password);
        model.setConfirmPassword(password);
        assertHasViolation(validator, model, "password", Pattern.class);
    }

    private void assertIncorrectEmail(String email) {
        RegistrationModel model = defaultModel();
        model.setEmail(email);
        assertHasViolation(validator, model, "email", Email.class);
    }

    @Override
    public RegistrationModel defaultModel() {
        return new RegistrationModel("Test", "User", "test@user.com", "password1!", "password1!");
    }
}