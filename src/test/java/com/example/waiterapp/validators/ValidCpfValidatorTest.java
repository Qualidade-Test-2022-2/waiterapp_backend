package com.example.waiterapp.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ValidCpfValidatorTest's tests")
class ValidCpfValidatorTest {
  ValidCpfValidator validCpfValidator = new ValidCpfValidator();

  @Test
  void isValid_True_ValidCpf() {
    String cpf = "71827073063";
    assertTrue(validCpfValidator.isValid(cpf, null));
  }

  @Test
  void isValid_False_InvalidCpf() {
    String cpf = "11111111112";
    assertFalse(validCpfValidator.isValid(cpf, null));
  }
}
