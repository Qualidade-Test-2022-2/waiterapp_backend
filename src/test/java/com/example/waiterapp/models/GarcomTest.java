
package com.example.waiterapp.models;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes dos métodos da classe Garcom")
class GarcomTest {
  private static Garcom garcom;

  static Garcom criaGarcom(int id, String name, String cpf, String email) {
    LocalDateTime dataCriacao = LocalDateTime.now();
    return new Garcom((long) id, name, dataCriacao, cpf, email);
  }

  @BeforeAll
  static void inicializaGarcom() {
    garcom = criaGarcom(1, "Rafael", "12345678910", "rafael@email.com");
  }

  @DisplayName("Garcom#equals deve ser falso com garçons diferentes")
  @Test
  void equals_GarconsDiferentes_False() {
    Garcom garcom2 = criaGarcom(2, "Tais", "11111111111", "tais@email.com");

    assertNotEquals(garcom, garcom2);
  }
}
