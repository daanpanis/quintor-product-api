package nl.quintor.dpanis.productapi.models;

import com.google.common.base.Preconditions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static nl.quintor.dpanis.productapi.TestUtils.*;

public abstract class ModelTest<T> {

    protected static ValidatorFactory validatorFactory;
    protected static Validator validator;

    @BeforeAll
    static void setupClass() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void destroyClass() {
        validatorFactory.close();
    }

    abstract T defaultModel();

    protected void sizeTest(String field, BiConsumer<T, String> setter, int min, int max) {
        Preconditions.checkArgument(min >= 0, "Min can't be negative");
        Preconditions.checkArgument(max >= min, "Max can't be lower than min");
        T model = defaultModel();
        if (min != 0) {
            // Test that empty string produces violation
            setter.accept(model, "");
            assertHasViolation(validator, model, field, Size.class);
        }
        if (min > 1) {
            setter.accept(model, repeat('a', min - 1));
            assertHasViolation(validator, model, field, Size.class);
        }
        // Test that string with size min doesn't produce violation
        setter.accept(model, repeat('a', min));
        assertNoViolation(validator, model);

        // Test that string with size in between min and max doesn't produce violation
        setter.accept(model, repeat('a', min + (max - min)));
        assertNoViolation(validator, model);

        // Test that string with size max doesn't produce violation
        setter.accept(model, repeat('a', max));
        assertNoViolation(validator, model);

        // Test that string with size 1 more than max produces violation
        setter.accept(model, repeat('a', max + 1));
        assertHasViolation(validator, model, field, Size.class);
    }

    protected void testField(Consumer<T> valueSetter, String fieldName, Class<? extends Annotation> constraintAnnotation) {
        T model = defaultModel();
        valueSetter.accept(model);
        assertHasViolation(validator, model, fieldName, constraintAnnotation);
    }

    protected void testFieldCorrect(Consumer<T> valueSetter) {
        T model = defaultModel();
        valueSetter.accept(model);
        assertNoViolation(validator, model);
    }
}
