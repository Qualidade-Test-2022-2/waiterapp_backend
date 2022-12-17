
package com.example.waiterapp.models;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes dos métodos da classe Item")
class ItemTest {
  private static Item item;

  static Item criaItem(int id, String name, String descrição, double preco) {
    LocalDateTime dataCriacao = LocalDateTime.now();
    return new Item((long) id, name, descrição, dataCriacao, preco);
  }

  @BeforeAll
  static void inicializaItem() {
    item = criaItem(1, "Batata", "Batata frita", 10.00);
  }

  @DisplayName("Item#equals deve ser falso com itens diferentes")
  @Test
  void equals_ItensDiferentes_False() {
    Item item2 = criaItem(2, "Abacaxi", "Abacaxi fatiado", 5.00);

    assertFalse(item.equals(item2));
  }
}
