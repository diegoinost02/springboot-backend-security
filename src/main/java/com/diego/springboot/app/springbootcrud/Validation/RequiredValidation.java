package com.diego.springboot.app.springbootcrud.Validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class RequiredValidation implements ConstraintValidator<IsRequired, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // return (value != null && !value.isBlank());
        return StringUtils.hasText(value);
    }

}
