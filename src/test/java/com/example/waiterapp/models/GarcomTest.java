
package com.example.waiterapp.models;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes dos métodos da classe Garcom")
public class GarcomTest {
  private static Garcom garcom;

  public static Garcom criaGarcom(int id, String name, String cpf) {
    LocalDateTime dataCriacao = LocalDateTime.now();
    return new Garcom((long) id, name, dataCriacao, cpf);
  }

  @BeforeAll
  public static void inicializaGarcom() {
    garcom = criaGarcom(1, "Rafael", "12345678910");
  }

  @DisplayName("Garcom#equals deve ser falso com garçons diferentes")
  @Test
  public void equals_GarconsDiferentes_False() {
    Garcom garcom2 = criaGarcom(2, "Tais", "11111111111");

    assertFalse(garcom.equals(garcom2));
  }
}
