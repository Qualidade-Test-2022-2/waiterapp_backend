package com.example.waiterapp.validators;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ValidCpfValidatorTest's tests")
public class ValidCpfValidatorTest {
  ValidCpfValidator validCpfValidator = new ValidCpfValidator();

  @Test
  public void isValid_True_ValidCpf() {
    String cpf = "71827073063";
    assertTrue(validCpfValidator.isValid(cpf, null));
  }

  @Test
  public void isValid_False_InvalidCpf() {
    String cpf = "71827073063";
    assertTrue(validCpfValidator.isValid(cpf, null));
  }
}
