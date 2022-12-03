package com.example.waiterapp.Cliente;

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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.example.waiterapp.cliente.Cliente;
import com.example.waiterapp.cliente.ClienteController;
import com.example.waiterapp.cliente.ClienteDTO;
import com.example.waiterapp.cliente.ClienteService;
import com.example.waiterapp.exceptions.ObjectNotFoundException;

@DisplayName("ClienteController's tests")
public class ClienteControllerTest {
  static ClienteController clienteController;

  @Mock
  Cliente cliente1 = mock(Cliente.class);
  Cliente cliente2 = mock(Cliente.class);
  static ClienteService clienteService = mock(ClienteService.class);

  @BeforeAll
  public static void inicializaClienteController() {
    clienteController = new ClienteController(clienteService);
  }

  @Nested
  @DisplayName("ClienteController#listaClientes")
  class ListaClientesTest {
    @BeforeEach
    public void mockClienteServiceListaClientes() {
      List<Cliente> clients = new ArrayList<Cliente>(List.of(cliente1, cliente2));
      when(clienteService.listaClientes()).thenReturn(clients);
    }

    @Test
    @DisplayName("should return 200")
    public void statusCode200() {
      assertEquals(clienteController.listaClientes().getStatusCode().value(), 200);
    }

    @Test
    @DisplayName("should return a list of clients")
    public void returnListOfClientes() {
      List<Cliente> clients = clienteController.listaClientes().getBody();
      if(clients != null) {
        assertAll(
          () -> assertEquals(clients.get(0), cliente1),
          () -> assertEquals(clients.get(1), cliente2)
        );
      } else {
        fail("Clientes is null");
      }
    }
  }

  @Nested
  @DisplayName("ClienteController#retornaClienteById")
  class RetornaClienteByIdTest {
    @Test
    @DisplayName("should return 200 when cliente exists")
    public void statusCode200_WhenClientesExists() {
      when(clienteService.retornaClienteById(1L)).thenReturn(cliente1);
      assertEquals(clienteController.retornaClienteById(1L).getStatusCode().value(), 200);
    }

    @Test
    @DisplayName("should return the cliente when cliente exists")
    public void returnCliente_WhenClientesExists() {
      when(clienteService.retornaClienteById(any(long.class))).thenReturn(cliente1);
      assertEquals(clienteController.retornaClienteById(1L).getBody(), cliente1);
    }
  }

  @Nested
  @DisplayName("ClienteController#retornaClienteByCpf")
  class RetornaClienteByCpfTest {
    @Mock
    ClienteDTO clienteDTO = mock(ClienteDTO.class);

    @BeforeEach
    public void mockClienteServiceRetornaClienteByCpf() {
      when(clienteService.retornaClienteByCpf(any(String.class))).thenReturn(cliente1);
    }

    @Test
    @DisplayName("should return 200 when cliente exists")
    public void statusCode200() {
      assertEquals(clienteController.retornaClienteByCpf(clienteDTO).getStatusCode().value(), 200);
    }

    @Test
    @Disabled("Must be fixed")
    @DisplayName("should return the cliente when cliente exists")
    public void returnCliente() {
      assertEquals(clienteController.retornaClienteByCpf(clienteDTO).getBody(), cliente1);
    }
  }

  @Nested
  @DisplayName("ClienteController#insereCliente")
  @Disabled("Disabled until ServletUriComponentsBuilder mock be possible")
  class InsereClienteTest {
    @Mock
    ClienteDTO clienteDTO = mock(ClienteDTO.class);

    @BeforeEach
    public void mockClienteServiceInsereCliente() {
      when(clienteService.insereCliente(any(Cliente.class))).thenReturn(cliente1);
      // when(ServletUriComponentsBuilder.fromCurrentRequest()).thenReturn(mock(ServletUriComponentsBuilder.class));
    }

    @Test
    @DisplayName("should return 200")
    public void statusCode200_WhenAlreadyExists() {
      when(clienteService.retornaClienteByCpf(any(String.class))).thenReturn(cliente1);
      assertEquals(clienteController.insereCliente(clienteDTO).getStatusCode().value(), 201);
    }

    @Test
    @DisplayName("should return cliente when already exists")
    public void returnCliente_WhenAlreadyExists() {
      when(clienteService.retornaClienteByCpf(any(String.class))).thenReturn(cliente1);
      assertEquals(clienteController.insereCliente(clienteDTO).getBody(), cliente1);
    }

    @Test
    @DisplayName("should return 201")
    public void statusCode201_WhenDoesNotExists() {
      assertEquals(clienteController.insereCliente(clienteDTO).getStatusCode().value(), 201);
    }

    @Test
    @DisplayName("should return cliente")
    public void returnCliente_WhenDoesNotExists() {
      assertEquals(clienteController.insereCliente(clienteDTO).getBody(), cliente1);
    }
  }

  @Nested
  @DisplayName("ClienteController#atualizaCliente")
  class AtualizaClienteTest {
    ClienteDTO clienteDTO = mock(ClienteDTO.class);

    @BeforeEach
    public void mockClienteServiceAtualizaCliente() {
      when(clienteService.transformarDTO(any(ClienteDTO.class))).thenReturn(cliente1);
    }

    @Test
    @DisplayName("should return 200 when cliente exists")
    public void statusCode200_WhenClientesExists() {
      when(clienteService.atualizaCliente(any(Cliente.class))).thenReturn(cliente1);
      assertEquals(clienteController.atualizaCliente(clienteDTO, 1L).getStatusCode().value(), 200);
    }

    @Test
    @DisplayName("should return the cliente")
    public void returnCliente_WhenClientesExists() {
      when(clienteService.atualizaCliente(any(Cliente.class))).thenReturn(cliente1);
      assertEquals(clienteController.atualizaCliente(clienteDTO, 1L).getBody(), cliente1);
    }
  }

  @Nested
  @DisplayName("ClienteController#deletaCliente")
  class DeleteClienteTest {
    @Test
    @DisplayName("should return 204 when cliente exists")
    public void statusCode204_WhenClienteExists() {
      doNothing().when(clienteService).apagaCliente(any(long.class));
      assertEquals(clienteController.deleteCliente(1L).getStatusCode().value(), 204);
    }

    @Test
    @DisplayName("should return 404 when cliente doesn't exists")
    public void statusCode204_WhenClienteDoesntExists() {
      doThrow(ObjectNotFoundException.class).when(clienteService).apagaCliente(any(long.class));
      assertEquals(clienteController.deleteCliente(1L).getStatusCode().value(), 404);
    }
  }
}
