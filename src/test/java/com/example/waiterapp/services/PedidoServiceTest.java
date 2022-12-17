package com.example.waiterapp.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.waiterapp.models.Pedido;
import com.example.waiterapp.dto.PedidoDTO;
import com.example.waiterapp.repositories.PedidoRepository;
import com.example.waiterapp.repositories.ItemPedidoRepository;
import com.example.waiterapp.repositories.PagamentoRepository;

@DisplayName("PedidoService's tests")
class PedidoServiceTest {

  static PedidoService pedidoService;

  @Mock
  Pedido pedido1 = mock(Pedido.class);
  Pedido pedido2 = mock(Pedido.class);

  static PedidoRepository pedidoRepository = mock(PedidoRepository.class);
  static PagamentoRepository pagamentoRepository = mock(PagamentoRepository.class);
  static ItemPedidoRepository itemPedidoRepository = mock(ItemPedidoRepository.class);
  static ItemService itemService = mock(ItemService.class);
  static ClienteService clienteService = mock(ClienteService.class);

  @BeforeAll
  public static void inicializaPedidoService() {
    pedidoService = new PedidoService(pedidoRepository, pagamentoRepository, itemPedidoRepository, itemService, clienteService);
  }

  @Nested
  @DisplayName("PedidoService#listaPedidos")
  class ListaPedidosTest {
    @BeforeEach
    public void mockPedidoRepositoryFindAll() {
      ArrayList<Pedido> pedidos = new ArrayList<Pedido>(List.of(pedido1, pedido2));
      when(pedidoRepository.findAll()).thenReturn(pedidos);
    }

    @Test
    @DisplayName("should return a list of pedidos")
    public void returnListOfPedidos() {
      assertEquals(pedidoService.listaPedidos().get(0), pedido1);
      assertEquals(pedidoService.listaPedidos().get(1), pedido2);
    }
  }

  @Nested
  @DisplayName("PedidoService#transformarDTO")
  class TransformarDTOTest {
    @Mock
    PedidoDTO pedidoDTO = mock(PedidoDTO.class);

    @Test
    @DisplayName("should transform a PedidoDTO into a Pedido")
    public void transformPedidoDTOIntoPedido() {
      Pedido pedidoTransformado = pedidoService.transformarDTO(pedidoDTO);

      assertAll(
        () -> assertEquals(pedidoTransformado.getId(), pedidoDTO.getId()),
        () -> assertEquals(pedidoTransformado.getEstado(), pedidoDTO.getEstado()),
        () -> assertEquals(pedidoTransformado.getDataCriacao(), pedidoDTO.getDataCriacao()),
        () -> assertEquals(pedidoTransformado.getNotaAtendimento(), pedidoDTO.getNotaAtendimento()),
        () -> assertEquals(pedidoTransformado.getNotaPedido(), pedidoDTO.getNotaPedido())
      );
    }
  }

  @Nested
  @DisplayName("PedidoService#returnPedidoById")
  class RetornaPedidoByIdTest {
    @Test
    @DisplayName("should return a pedido by id when it exists")
    public void returnPedidoWhenPedidoExist() {
      when(pedidoRepository.findById(anyLong())).thenReturn(java.util.Optional.of(pedido1));
      assertEquals(pedidoService.retornaPedidoById(1L), pedido1);
    }
  }

  @Nested
  @Disabled("must be fixed")
  @DisplayName("PedidoService#inserePedido")
  class InserePedidoTest {
    @Test
    @DisplayName("should insert a pedido")
    public void insertPedido() {
      when(pedidoRepository.save(pedido1)).thenReturn(pedido1);
      assertEquals(pedidoService.inserePedido(pedido1), pedido1);
    }
  }

  @Nested
  @DisplayName("PedidoService#atualizaPedido")
  class AtualizaPedidoTest {
    @BeforeEach
    public void mockPedidoRepositoryFindById() {
      when(pedidoRepository.findById(anyLong())).thenReturn(java.util.Optional.of(pedido1));
      when(pedidoRepository.save(pedido1)).thenReturn(pedido1);
    }

    @Test
    @DisplayName("should update a pedido")
    public void updatePedido() {
      assertEquals(pedidoService.atualizaPedido(pedido1), pedido1);
    }
  }

  @Nested
  @DisplayName("PedidoService#apagaPedido")
  class ApagaPedidoTest {
    @BeforeEach
    public void mockPedidoRepositoryFindById() {
      when(pedidoRepository.findById(anyLong())).thenReturn(java.util.Optional.of(pedido1));
    }

    @Test
    @DisplayName("should delete a pedido")
    public void deletePedido() {
      doNothing().when(pedidoRepository).deleteById(anyLong());
      pedidoService.apagaPedido(1L);
    }

    @Test
    @DisplayName("should delete a pedido")
    public void throwExceptionWhenCouldNotDeletePedido() {
      doThrow(DataIntegrityViolationException.class).when(pedidoRepository).deleteById(anyLong());

      assertThrows(DataIntegrityViolationException.class, () -> pedidoService.apagaPedido(1L));
    }
  }
}
