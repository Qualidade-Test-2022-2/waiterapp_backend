package com.example.waiterapp.Cliente;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.waiterapp.cliente.Cliente;
import com.example.waiterapp.cliente.ClienteDTO;
import com.example.waiterapp.cliente.ClienteRepository;
import com.example.waiterapp.cliente.ClienteService;
import com.example.waiterapp.pedido.Pedido;

@DisplayName("ClienteService's tests")
class ClienteServiceTest {
  static ClienteService clienteService;

  @Mock
  Cliente cliente1 = mock(Cliente.class);
  Cliente cliente2 = mock(Cliente.class);
  static ClienteRepository clienteRepository = mock(ClienteRepository.class);

  @BeforeAll
  public static void inicializaClienteService() {
    clienteService = new ClienteService(clienteRepository);
  }

  @Nested
  @DisplayName("ClienteService#listaClientes")
  class ListaClientesTest {
    @BeforeEach
    public void mockClienteRepositoryFindAll() {
      ArrayList<Cliente> clientes = new ArrayList<Cliente>(List.of(cliente1, cliente2));
      when(clienteRepository.findAll()).thenReturn(clientes);
    }

    @Test
    @DisplayName("should return a list of clientes")
    public void returnListOfClientes() {
      assertEquals(clienteService.listaClientes().get(0), cliente1);
      assertEquals(clienteService.listaClientes().get(1), cliente2);
    }
  }

  @Nested
  @DisplayName("ClienteService#transformarDTO")
  class TransformarDTOTest {
    @Mock
    ClienteDTO clienteDTO = mock(ClienteDTO.class);

    @Test
    @DisplayName("should transform a ClienteDTO into a Cliente")
    public void transformClienteDTOIntoCliente() {
      Cliente clienteTransformado = clienteService.transformarDTO(clienteDTO);

      assertAll(
        () -> assertEquals(clienteTransformado.getId(), clienteDTO.getId()),
        () -> assertEquals(clienteTransformado.getNome(), clienteDTO.getNome()),
        () -> assertEquals(clienteTransformado.getDataCriacao(), clienteDTO.getDataCriacao()),
        () -> assertEquals(clienteTransformado.getEmail(), clienteDTO.getEmail()),
        () -> assertEquals(clienteTransformado.getCpf(), clienteDTO.getCpf())
      );
    }
  }

  @Nested
  @DisplayName("ClienteService#retornaClienteById")
  class RetornaClienteByIdTest {
    @Test
    @DisplayName("should return a cliente by id when it exists")
    public void returnClienteWhenClienteExist() {
      when(clienteRepository.findById(anyLong())).thenReturn(java.util.Optional.of(cliente1));
      assertEquals(clienteService.retornaClienteById(1L), cliente1);
    }
  }

  @Nested
  @DisplayName("ClienteService#retornaClienteByCpf")
  class RetornaClienteByCpfTest {
    @Test
    @DisplayName("should return a cliente by id when it exists")
    public void returnClienteWhenClienteExist() {
      when(clienteRepository.findByCpf(any(String.class))).thenReturn(java.util.Optional.of(cliente1));
      assertEquals(clienteService.retornaClienteByCpf("11111111111"), cliente1);
    }
  }

  @Nested
  @DisplayName("ClienteService#insereCliente")
  class InsereClienteTest {
    @Test
    @DisplayName("should insert a cliente")
    public void insertCliente() {
      when(clienteRepository.save(cliente1)).thenReturn(cliente1);
      assertEquals(clienteService.insereCliente(cliente1, "123123213"), cliente1);
    }
  }

  @Nested
  @DisplayName("ClienteService#atualizaCliente")
  class AtualizaClienteTest {
    @BeforeEach
    public void mockClienteRepositoryFindById() {
      when(clienteRepository.findById(anyLong())).thenReturn(java.util.Optional.of(cliente1));
      when(clienteRepository.save(cliente1)).thenReturn(cliente1);
    }

    @Test
    @DisplayName("should update a cliente")
    public void updateCliente() {
      assertEquals(clienteService.atualizaCliente(cliente1), cliente1);
    }
  }

  @Nested
  @DisplayName("ClienteService#apagaCliente")
  class ApagaClienteTest {
    @BeforeEach
    public void mockClienteRepositoryFindById() {
      when(clienteRepository.findById(anyLong())).thenReturn(java.util.Optional.of(cliente1));
    }

    @Test
    @DisplayName("should delete a cliente")
    public void deleteCliente() {
      doNothing().when(clienteRepository).deleteById(anyLong());
      clienteService.apagaCliente(1L);
    }

    @Test
    @DisplayName("throws exception when cliente cannot be deleted")
    public void throwExceptionWhenCouldNotDeleteCliente() {
      doThrow(DataIntegrityViolationException.class).when(clienteRepository).deleteById(anyLong());
      assertThrows(DataIntegrityViolationException.class, () -> clienteService.apagaCliente(1L));
    }
  }

  @Nested
  @DisplayName("ClienteService#retornaPedidosCliente")
  class RetornaPedidosClienteTest {
    @BeforeEach
    public void mockClientePedidos() {
      Pedido pedido1 = mock(Pedido.class);
      Pedido pedido2 = mock(Pedido.class);
      cliente1.setPedidos(new ArrayList<>(List.of(pedido1, pedido2)));
    }

    @Test
    @DisplayName("should return the cliente's pedidos")
    public void returnPedidosOfTheCliente() {
      when(clienteRepository.findById(anyLong())).thenReturn(java.util.Optional.of(cliente1));
      assertEquals(clienteService.retornaPedidosCliente(1L), cliente1.getPedidos());
    }
  }

  @Nested
  @DisplayName("ClienteService#inserePedidosCliente")
  class InserePedidosClienteTest {
    @Mock
    ArrayList<Pedido> pedidos = new ArrayList<>(List.of(mock(Pedido.class), mock(Pedido.class)));

    @Test
    @Disabled("must be fixed")
    @DisplayName("should update the cliente's pedidos list")
    public void updateClientePedidos() {
      when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente1));
      when(clienteService.retornaClienteById(anyLong())).thenReturn(cliente1);

      clienteService.inserePedidosCliente(1L, pedidos);
      assertEquals(pedidos, cliente1.getPedidos());
    }
  }
}
