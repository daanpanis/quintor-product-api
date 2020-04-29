package nl.quintor.dpanis.productapi.models;

import org.junit.jupiter.api.Test;

import javax.validation.constraints.*;

import static nl.quintor.dpanis.productapi.TestUtils.assertNoViolation;

class ProductModelTest extends ModelTest<ProductModel> {

    @Test
    void testSuccess() {
        assertNoViolation(validator, defaultModel());
    }

    @Test
    void testNameConstraints() {
        testField(model -> model.setName(null), "name", NotNull.class);
        testField(model -> model.setName(""), "name", NotBlank.class);
        sizeTest("name", ProductModel::setName, 2, 100);
    }

    @Test
    void testDescriptionConstraints() {
        testField(model -> model.setDescription(null), "description", NotNull.class);
        testField(model -> model.setDescription(""), "description", NotBlank.class);
        sizeTest("description", ProductModel::setDescription, 5, 3000);
    }

    @Test
    void testPriceConstraints() {
        testField(model -> model.setPrice(null), "price", NotNull.class);
        testField(model -> model.setPrice(0D), "price", DecimalMin.class);
        testField(model -> model.setPrice(-100D), "price", DecimalMin.class);
        testFieldCorrect(model -> model.setPrice(0.01));
        testField(model -> model.setPrice(Double.NEGATIVE_INFINITY), "price", DecimalMin.class);
        testField(model -> model.setPrice(Double.POSITIVE_INFINITY), "price", DecimalMax.class);
        testField(model -> model.setPrice(Double.MIN_VALUE), "price", DecimalMin.class);
        testField(model -> model.setPrice(Double.MAX_VALUE), "price", DecimalMax.class);
    }

    @Test
    void testStockConstraints() {
        testField(model -> model.setStock(-100), "stock", Min.class);
        testField(model -> model.setStock(Integer.MIN_VALUE), "stock", Min.class);
        testFieldCorrect(model -> model.setStock(0));
        testFieldCorrect(model -> model.setStock(Integer.MAX_VALUE));
        testField(model -> model.setStock(Integer.MAX_VALUE + 1), "stock", Min.class);
        testFieldCorrect(model -> model.setStock(Integer.MIN_VALUE - 1));
    }

    @Override
    ProductModel defaultModel() {
        return new ProductModel("test product", "test product description", 100D, 10, 1L);
    }
}