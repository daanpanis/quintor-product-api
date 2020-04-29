package nl.quintor.dpanis.productapi.models;

import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static nl.quintor.dpanis.productapi.TestUtils.assertNoViolation;

class CategoryModelTest extends ModelTest<CategoryModel> {

    @Test
    void testSuccess() {
        assertNoViolation(validator, defaultModel());
    }

    @Test
    void testNameConstraints() {
        testField(model -> model.setName(null), "name", NotNull.class);
        testField(model -> model.setName(""), "name", NotBlank.class);
        sizeTest("name", CategoryModel::setName, 2, 100);
    }

    @Test
    void testDescriptionConstraints() {
        testField(model -> model.setDescription(null), "description", NotNull.class);
        testField(model -> model.setDescription(""), "description", NotBlank.class);
        sizeTest("description", CategoryModel::setDescription, 5, 3000);
    }

    @Override
    CategoryModel defaultModel() {
        return new CategoryModel("Test Category", "Test category description");
    }
}