
package com.example.waiterapp.models;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;

@DisplayName("Testes dos métodos da classe Cardapio")
class CardapioTest {
  private static Cardapio cardapio;

  @Mock
  static Item batata = mock(Item.class);
  static Item tomate = mock(Item.class);
  static Item guarana = mock(Item.class);

  static Cardapio criaCardapio(int id, String titulo, String descricao) {
    LocalDateTime dataCriacao = LocalDateTime.now();
    return new Cardapio((long) id, dataCriacao, titulo, descricao);
  }

  @BeforeAll
  static void inicializaCardapioComItems() {
    cardapio = criaCardapio(1, "Cardapio com itens 1", "Cardapio de teste");
    List<Item> items = new ArrayList<>(Arrays.asList(batata, tomate));
    cardapio.setItems(items);
  }

  @DisplayName("Cardapio#equals deve ser falso com cardapios diferentes")
  @Test
  void equals_CardapiosDiferentes_False() {
    Cardapio cardapio2 = criaCardapio(2, "Cardápio 2", "Cardapio diferente");

    assertNotEquals(cardapio, cardapio2);
  }
}
