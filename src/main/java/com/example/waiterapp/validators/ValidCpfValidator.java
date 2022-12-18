package com.example.waiterapp.validators;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.waiterapp.interfaces.CpfValidatorContraint;
import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

public class ValidCpfValidator implements ConstraintValidator<CpfValidatorContraint, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
      CPFValidator cpfValidator = new CPFValidator();
      List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf);
      if(!erros.isEmpty()) return false;
      return true;
    }
}
