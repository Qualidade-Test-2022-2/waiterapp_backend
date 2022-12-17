package com.example.waiterapp.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.example.waiterapp.validators.ValidCpfValidator;


@Constraint(validatedBy = ValidCpfValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfValidatorContraint {
  String message() default "Invalid cpf";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
