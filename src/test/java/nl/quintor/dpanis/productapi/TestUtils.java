package nl.quintor.dpanis.productapi;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtils {

    public static <T> void assertHasViolation(Validator validator, T model, String field, Class<? extends Annotation> constraintClass) {
        Set<ConstraintViolation<T>> violations = validator.validate(model);
        assertFalse(violations.isEmpty());
        assertTrue(hasViolation(violations, field, constraintClass));
    }

    public static <T> void assertNoViolation(Validator validator, T model) {
        Set<ConstraintViolation<T>> violations = validator.validate(model);
        assertTrue(violations.isEmpty());
    }

    public static <T> boolean hasViolation(Set<ConstraintViolation<T>> violations, String field, Class<? extends Annotation> constraintClass) {
        return violations.stream().anyMatch(violation -> field.equals(violation.getPropertyPath().toString()) &&
                violation.getConstraintDescriptor() != null && violation.getConstraintDescriptor().getAnnotation() != null &&
                constraintClass.equals(violation.getConstraintDescriptor().getAnnotation().annotationType()));
    }

    public static String repeat(char c, int amount) {
        char[] chars = new char[amount];
        Arrays.fill(chars, c);
        return new String(chars);
    }

    private TestUtils() {
    }
}
