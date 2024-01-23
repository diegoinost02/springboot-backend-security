package com.diego.springboot.app.springbootcrud.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diego.springboot.app.springbootcrud.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistByUsernameValidator implements ConstraintValidator<ExistByUsername, String>{

    @Autowired
    private UserService service;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if(service == null) { // si no se inyecta el servicio, no se valida
            return true;
        }
        return !this.service.ExistByUsername(username);
    }

}
