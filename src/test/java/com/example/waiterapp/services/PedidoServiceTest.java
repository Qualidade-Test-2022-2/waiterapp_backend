package com.example.waiterapp.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.waiterapp.models.Cliente;
import com.example.waiterapp.models.Item;
import com.example.waiterapp.models.ItemPedido;
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
  static void inicializaPedidoService() {
    pedidoService = new PedidoService(pedidoRepository, pagamentoRepository, itemPedidoRepository, itemService, clienteService);
  }

  @Nested
  @DisplayName("PedidoService#listaPedidos")
  class ListaPedidosTest {
    @BeforeEach
    void mockPedidoRepositoryFindAll() {
      ArrayList<Pedido> pedidos = new ArrayList<Pedido>(List.of(pedido1, pedido2));
      when(pedidoRepository.findAll()).thenReturn(pedidos);
    }

    @Test
    @DisplayName("should return a list of pedidos")
    void returnListOfPedidos() {
      assertEquals(pedidoService.listaPedidos().get(0), pedido1);
      assertEquals(pedidoService.listaPedidos().get(1), pedido2);
    }
  }

  @Nested
  @DisplayName("PedidoService#listaPedidosByIdCliente")
  class ListaPedidoByIdClienteTest {
    @Test
    @DisplayName("should return a pedido by id when it exists")
    void returnPedidoWhenPedidoExist() {
      when(pedidoRepository.findallByIdCliente(anyLong())).thenReturn(List.of(pedido1, pedido2));
      assertEquals(pedidoService.listaPedidosByIdCliente(1L).get(0), pedido1);
      assertEquals(pedidoService.listaPedidosByIdCliente(1L).get(1), pedido2);
    }
  }

  @Nested
  @DisplayName("PedidoService#transformarDTO")
  class TransformarDTOTest {
    @Mock
    PedidoDTO pedidoDTO = mock(PedidoDTO.class);

    @Test
    @DisplayName("should transform a PedidoDTO into a Pedido")
    void transformPedidoDTOIntoPedido() {
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
    void returnPedidoWhenPedidoExist() {
      when(pedidoRepository.findById(anyLong())).thenReturn(java.util.Optional.of(pedido1));
      assertEquals(pedidoService.retornaPedidoById(1L), pedido1);
    }

    @Test
    @DisplayName("should return null when does not exists")
    void returnPedidoWhenPedidoDoesNotExist() {
      when(pedidoRepository.findById(anyLong())).thenReturn(Optional.empty());
      assertEquals(null, pedidoService.retornaPedidoById(1L));
    }
  }

  @Nested
  @DisplayName("PedidoService#inserePedido")
  class InserePedidoTest {
    @Mock
    Cliente cliente1 = mock(Cliente.class);
    Item item1 = mock(Item.class);
    Item item2 = mock(Item.class);
    ItemPedido itemPedido1 = mock(ItemPedido.class);
    ItemPedido itemPedido2 = mock(ItemPedido.class);
    Set<ItemPedido> items = new HashSet<>(List.of(itemPedido1, itemPedido2));

    @BeforeEach
    void mockPedidoRepositorySave() {
      when(pedido1.getCliente()).thenReturn(cliente1);
      when(pedido1.getItems()).thenReturn(items);
      when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido1);
      when(itemPedido1.getItem()).thenReturn(item1);
      when(itemPedido2.getItem()).thenReturn(item2);
      when(itemPedido1.getQuantidade()).thenReturn(1);
      when(itemPedido2.getQuantidade()).thenReturn(1);
      when(item1.getPreco()).thenReturn(10.0);
    }

    @Test
    @DisplayName("should insert a pedido")
    void insertPedido() {
      assertEquals(pedidoService.inserePedido(pedido1), pedido1);
    }
  }

  @Nested
  @DisplayName("PedidoService#atualizaPedido")
  class AtualizaPedidoTest {
    @BeforeEach
    void mockPedidoRepositoryFindById() {
      when(pedidoRepository.findById(anyLong())).thenReturn(java.util.Optional.of(pedido1));
      when(pedidoRepository.save(pedido1)).thenReturn(pedido1);
    }

    @Test
    @DisplayName("should update a pedido")
    void updatePedido() {
      assertEquals(pedidoService.atualizaPedido(pedido1), pedido1);
    }
  }

  @Nested
  @DisplayName("PedidoService#apagaPedido")
  class ApagaPedidoTest {
    @BeforeEach
    void mockPedidoRepositoryFindById() {
      when(pedidoRepository.findById(anyLong())).thenReturn(java.util.Optional.of(pedido1));
    }

    @Test
    @DisplayName("should delete a pedido")
    void deletePedido() {
      doNothing().when(pedidoRepository).deleteById(anyLong());
      pedidoService.apagaPedido(1L);
      assertTrue(true);
    }

    @Test
    @DisplayName("should delete a pedido")
    void throwExceptionWhenCouldNotDeletePedido() {
      doThrow(DataIntegrityViolationException.class).when(pedidoRepository).deleteById(anyLong());

      assertThrows(DataIntegrityViolationException.class, () -> pedidoService.apagaPedido(1L));
    }
  }
}
