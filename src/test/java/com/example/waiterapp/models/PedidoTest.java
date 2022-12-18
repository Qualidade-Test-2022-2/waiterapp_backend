
package com.example.waiterapp.models;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.waiterapp.enums.Estado;
import org.mockito.Mock;

@DisplayName("Testes dos métodos da classe Pedido")
class PedidoTest {
  private static Pedido pedido;

  @Mock
  static ItemPedido batata = mock(ItemPedido.class);
  static ItemPedido tomate = mock(ItemPedido.class);
  static ItemPedido guarana = mock(ItemPedido.class);

  static Pedido criaPedido(int id, String titulo, String descricao) {
    LocalDateTime dataCriacao = LocalDateTime.now();
    return new Pedido((long) id, dataCriacao, Estado.EM_PREPARACAO, 0.0, 1, 2, null);
  }

  @BeforeAll
  static void inicializaPedidoComItems() {
    pedido = criaPedido(1, "Pedido com itens 1", "Pedido de teste");
    Set<ItemPedido> items = new HashSet<ItemPedido>(Arrays.asList(batata, tomate));
    pedido.setItems(items);
  }

  @DisplayName("Pedido#equals deve ser falso com pedidos diferentes")
  @Test
  void equals_PedidosDiferentes_False() {
    Pedido pedido2 = criaPedido(2, "Cardápio 2", "Pedido diferente");

    assertNotEquals(pedido, pedido2);
  }
}
