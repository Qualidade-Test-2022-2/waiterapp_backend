
package com.example.waiterapp.models;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.waiterapp.enums.Estado;
import org.mockito.Mock;

@DisplayName("Testes dos métodos da classe Pedido")
public class PedidoTest {
  private static Pedido pedido;

  @Mock
  static ItemPedido batata = mock(ItemPedido.class);
  static ItemPedido tomate = mock(ItemPedido.class);
  static ItemPedido guarana = mock(ItemPedido.class);

  public static Pedido criaPedido(int id, String titulo, String descricao) {
    LocalDateTime dataCriacao = LocalDateTime.now();
    return new Pedido((long) id, dataCriacao, Estado.EM_PREPARACAO, 0.0, 1, 2, null);
  }

  @BeforeAll
  public static void inicializaPedidoComItems() {
    pedido = criaPedido(1, "Pedido com itens 1", "Pedido de teste");
    Set<ItemPedido> items = new HashSet<ItemPedido>(Arrays.asList(batata, tomate));
    pedido.setItems(items);
  }

  @DisplayName("Pedido#setPrecoTotal deve calcular o preço total do pedido")
  @Disabled("must be implemented")
  @Test
  public void setPrecoTotalSomatoriaDosItens() {}

  @DisplayName("Pedido#equals deve ser falso com pedidos diferentes")
  @Test
  public void equals_PedidosDiferentes_False() {
    Pedido pedido2 = criaPedido(2, "Cardápio 2", "Pedido diferente");

    assertFalse(pedido.equals(pedido2));
  }
}
