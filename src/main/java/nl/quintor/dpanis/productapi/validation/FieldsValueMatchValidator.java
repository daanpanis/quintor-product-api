package nl.quintor.dpanis.productapi.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Credits to: https://www.baeldung.com/spring-mvc-custom-validator
 */
public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch annotation) {
        this.field = annotation.field();
        this.fieldMatch = annotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper wrapper = new BeanWrapperImpl(value);
        Object fieldValue = wrapper.getPropertyValue(field);
        Object fieldMatchValue = wrapper.getPropertyValue(fieldMatch);
        return (fieldValue == null && fieldMatchValue == null) || (fieldValue != null && fieldValue.equals(fieldMatchValue));
    }
}
