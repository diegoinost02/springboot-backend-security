 package com.diego.springboot.app.springbootcrud.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diego.springboot.app.springbootcrud.services.ProductService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class IsExistDbValidation implements ConstraintValidator<IsExistDb, String>{

    @Autowired
    private ProductService service;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(service == null) { // si no se inyecta el servicio, no se valida
            return true;
        }
        return !this.service.existsBySku(value);
    }

}
