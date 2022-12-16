
package com.example.waiterapp.Item;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.waiterapp.item.Item;

@DisplayName("Testes dos métodos da classe Item")
public class ItemTest {
  private static Item item;

  public static Item criaItem(int id, String name, String descrição, double preco, int qtdItem) {
    LocalDateTime dataCriacao = LocalDateTime.now();
    boolean disponivel = false;

    if(qtdItem > 0 ){
      disponivel = true;
    }

    return new Item((long) id, name, descrição, dataCriacao, preco, qtdItem, disponivel);
  }

  @BeforeAll
  public static void inicializaItem() {
    item = criaItem(1, "Batata", "Batata frita", 10.00, 10);
  }

  @DisplayName("Item#equals deve ser falso com itens diferentes")
  @Test
  public void equals_ItensDiferentes_False() {
    Item item2 = criaItem(2, "Abacaxi", "Abacaxi fatiado", 5.00, 10);

    assertFalse(item.equals(item2));
  }
}
