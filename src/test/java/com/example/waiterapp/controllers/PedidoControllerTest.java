package com.example.waiterapp.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.example.waiterapp.models.Pedido;
import com.example.waiterapp.dto.PedidoDTO;
import com.example.waiterapp.services.PedidoService;
import com.example.waiterapp.exceptions.ObjectNotFoundException;

@DisplayName("PedidoController's tests")
class PedidoControllerTest {
  static PedidoController pedidoController;

  @Mock
  Pedido pedido1 = mock(Pedido.class);
  Pedido pedido2 = mock(Pedido.class);
  static PedidoService pedidoService = mock(PedidoService.class);

  @BeforeAll
  static void inicializaPedidoController() {
    pedidoController = new PedidoController(pedidoService);
  }

  @Nested
  @DisplayName("PedidoController#listaPedidos")
  class ListaPedidosTest {
    @BeforeEach
    void mockPedidoServiceListaPedidos() {
      List<Pedido> pedidos = new ArrayList<Pedido>(List.of(pedido1, pedido2));
      when(pedidoService.listaPedidos()).thenReturn(pedidos);
    }

    @Test
    @DisplayName("should return 200")
    void statusCode200() {
      assertEquals(200, pedidoController.listaPedidos().getStatusCode().value());
    }

    @Test
    @DisplayName("should return a list of pedidos")
    void returnListOfPedidos() {
      List<Pedido> pedidos = pedidoController.listaPedidos().getBody();
      if(pedidos != null) {
        assertAll(
                () -> assertEquals(pedidos.get(0), pedido1),
                () -> assertEquals(pedidos.get(1), pedido2)
        );
      } else {
        fail("Pedidos is null");
      }
    }
  }
  @Nested
  @DisplayName("PedidoController#retornaPedidoByIdCliente")
  class RetornaPedidoByIdClienteTest {

    @BeforeEach
    void mockPedidoServiceRetornaPedidoByIdCliente() {
      when(pedidoService.listaPedidosByIdCliente(1L)).thenReturn(List.of(pedido1, pedido2));
    }

    @Test
    @DisplayName("should return 200 when pedido exists")
    void statusCode200_WhenPedidosExists() {
      assertEquals(200, pedidoController.retornaPedidoByIdCliente(1L).getStatusCode().value());
    }

    @Test
    @DisplayName("should return the pedido when pedido exists")
    void returnPedido_WhenPedidosExists() {
      List<Pedido> pedidos = pedidoController.retornaPedidoByIdCliente(1L).getBody();
      if (pedidos != null) {
        assertEquals(pedidos.get(0), pedido1);
        assertEquals(pedidos.get(1), pedido2);
      }else {
        fail("Pedidos is null");
      }
    }
  }

  @Nested
  @DisplayName("PedidoController#retornaPedidoById")
  class RetornaPedidoByIdTest {
    @Test
    @DisplayName("should return 200 when pedido exists")
    void statusCode200_WhenPedidosExists() {
      when(pedidoService.retornaPedidoById(1L)).thenReturn(pedido1);
      assertEquals(200, pedidoController.retornaPedidoById(1L).getStatusCode().value());
    }

    @Test
    @DisplayName("should return the pedido when pedido exists")
    void returnPedido_WhenPedidosExists() {
      when(pedidoService.retornaPedidoById(any(long.class))).thenReturn(pedido1);
      assertEquals(pedidoController.retornaPedidoById(1L).getBody(), pedido1);
    }
  }

  @Nested
  @DisplayName("PedidoController#inserePedido")
  class InserePedidoTest {
    PedidoDTO pedidoDTO;

    @BeforeEach
    void mockPedidoServiceInserePedido() {
      pedidoDTO = mock(PedidoDTO.class);
      when(pedidoService.transformarDTO(any(PedidoDTO.class))).thenReturn(pedido1);
      when(pedidoService.inserePedido(any(Pedido.class))).thenReturn(pedido1);
    }

    @Test
    @DisplayName("should return 201")
    void statusCode201() {
      assertEquals(201, pedidoController.inserePedido(pedidoDTO).getStatusCode().value());
    }

    @Test
    @DisplayName("should return the pedido")
    void returnPedido() {
      assertEquals(pedidoController.inserePedido(pedidoDTO).getBody(), pedido1);
    }
  }

  @Nested
  @DisplayName("PedidoController#atualizaPedido")
  class AtualizaPedidoTest {
    PedidoDTO pedidoDTO = mock(PedidoDTO.class);

    @BeforeEach
    void mockPedidoServiceAtualizaPedido() {
      when(pedidoService.transformarDTO(any(PedidoDTO.class))).thenReturn(pedido1);
    }

    @Test
    @DisplayName("should return 200 when pedido exists")
    void statusCode200_WhenPedidosExists() {
      when(pedidoService.atualizaPedido(any(Pedido.class))).thenReturn(pedido1);
      assertEquals(200, pedidoController.atualizaPedido(pedidoDTO, 1L).getStatusCode().value());
    }

    @Test
    @DisplayName("should return the pedido")
    void returnPedido_WhenPedidosExists() {
      when(pedidoService.atualizaPedido(any(Pedido.class))).thenReturn(pedido1);
      assertEquals(pedidoController.atualizaPedido(pedidoDTO, 1L).getBody(), pedido1);
    }
  }

  @Nested
  @DisplayName("PedidoController#deletaPedido")
  class DeletePedidoTest {
    @Test
    @DisplayName("should return 204 when pedido exists")
    void statusCode204_WhenPedidoExists() {
      doNothing().when(pedidoService).apagaPedido(any(long.class));
      assertEquals(204, pedidoController.deletePedido(1L).getStatusCode().value());
    }

    @Test
    @DisplayName("should return 404 when pedido doesn't exist")
    void statusCode204_WhenPedidoDoesntExists() {
      doThrow(ObjectNotFoundException.class).when(pedidoService).apagaPedido(any(long.class));
      assertEquals(404, pedidoController.deletePedido(1L).getStatusCode().value());
    }
  }
}
