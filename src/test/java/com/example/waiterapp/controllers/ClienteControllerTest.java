package com.example.waiterapp.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.example.waiterapp.models.Cliente;
import com.example.waiterapp.dto.ClienteDTO;
import com.example.waiterapp.services.ClienteService;
import com.example.waiterapp.exceptions.ObjectNotFoundException;
@DisplayName("ClienteController's tests")
class ClienteControllerTest {
  static ClienteController clienteController;

  @Mock
  Cliente cliente1 = mock(Cliente.class);
  Cliente cliente2 = mock(Cliente.class);
  ClienteDTO clienteDTO = mock(ClienteDTO.class);
  static ClienteService clienteService = mock(ClienteService.class);

  @BeforeAll
  static void inicializaClienteController() {
    clienteController = new ClienteController(clienteService);
  }

  @Nested
  @DisplayName("ClienteController#listaClientes")
  class ListaClientesTest {
    @BeforeEach
    void mockClienteServiceListaClientes() {
      List<Cliente> clients = new ArrayList<Cliente>(List.of(cliente1, cliente2));
      when(clienteService.listaClientes()).thenReturn(clients);
    }

    @Test
    @DisplayName("should return 200")
    void statusCode200() {
      assertEquals(200, clienteController.listaClientes().getStatusCode().value());
    }

    @Test
    @DisplayName("should return a list of clients")
    void returnListOfClientes() {
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
  @DisplayName("ClienteController#authenticate")
  class LoginClienteTest {
    @Mock
    Cliente cliente = mock(Cliente.class);

    @Test
    @DisplayName("should return 200 when cliente exists and isClientAuthorized return true")
    void statusCode200_WhenClienteExistsAndisClientAuthorizedReturnTrue() {
      when(clienteService.retornaClienteByCpf(anyString())).thenReturn(cliente);
      when(clienteService.isClientAuthorized(any(Cliente.class), anyString(), anyString())).thenReturn(true);
      assertEquals(200, clienteController.authenticate("MTYyMTM0ODc3NjA6cmFkYQ==").getStatusCode().value());
    }

    @Test
    @DisplayName("should return 400 when cliente exists and isClientAuthorized return false")
    void statusCode404_WhenClienteExistsAndisClientAuthorizedReturnFalse() {
      when(clienteService.retornaClienteByCpf(any(String.class))).thenReturn(cliente1);
      when(clienteService.isClientAuthorized(any(Cliente.class), any(String.class), any(String.class))).thenReturn(false);
      assertEquals(404, clienteController.authenticate("MTYyMTM0ODc3NjA6cmFkYQ==").getStatusCode().value());
    }

    @Test
    @DisplayName("should return 400 when cliente does not exists")
    void statusCode404_WhenClienteDoesNotExists() {
      when(clienteService.retornaClienteByCpf(any(String.class))).thenReturn(null);
      assertEquals(404, clienteController.authenticate("MTYyMTM0ODc3NjA6cmFkYQ==").getStatusCode().value());
    }
  }

  @Nested
  @DisplayName("ClienteController#retornaClienteById")
  class RetornaClienteByIdTest {
    @Test
    @DisplayName("should return 200 when cliente exists")
    void statusCode200_WhenClientesExists() {
      when(clienteService.retornaClienteById(1L)).thenReturn(cliente1);
      assertEquals(200, clienteController.retornaClienteById(1L).getStatusCode().value());
    }

    @Test
    @DisplayName("should return the cliente when cliente exists")
    void returnCliente_WhenClientesExists() {
      when(clienteService.retornaClienteById(any(long.class))).thenReturn(cliente1);
      assertEquals(clienteController.retornaClienteById(1L).getBody(), cliente1);
    }
  }

  @Nested
  @DisplayName("ClienteController#retornaClienteByCpf")
  class RetornaClienteByCpfTest {
    @BeforeEach
    void mockClienteServiceRetornaClienteByCpf() {
      when(clienteDTO.getCpf()).thenReturn("12312312312");
      when(clienteService.retornaClienteByCpf(any(String.class))).thenReturn(cliente1);
    }

    @Test
    @DisplayName("should return 200 when cliente exists")
    void statusCode200() {
      assertEquals(200, clienteController.retornaClienteByCpf(clienteDTO).getStatusCode().value());
    }

    @Test
    @DisplayName("should return the cliente when cliente exists")
    void returnCliente() {
      assertEquals(cliente1, clienteController.retornaClienteByCpf(clienteDTO).getBody());
    }
  }

  @Nested
  @DisplayName("ClienteController#insereCliente")
  class InsereClienteTest {
    @BeforeEach
    void mockClienteServiceInsereCliente() {
      when(clienteService.transformarDTO(any(ClienteDTO.class))).thenReturn(cliente1);
      when(clienteService.insereCliente(any(Cliente.class))).thenReturn(cliente1);
    }

    @Test
    @DisplayName("should return status code 200 when already exists")
    void statusCode200_WhenAlreadyExists() {
      when(clienteDTO.getCpf()).thenReturn("12312312312");
      when(clienteService.retornaClienteByCpf(any(String.class))).thenReturn(cliente1);
      assertEquals(200, clienteController.insereCliente(clienteDTO).getStatusCode().value());
    }

    @Test
    @DisplayName("should return cliente when already exists")
    void returnCliente_WhenAlreadyExists() {
      when(clienteDTO.getCpf()).thenReturn("12312312312");
      when(clienteService.retornaClienteByCpf(any(String.class))).thenReturn(cliente1);
      assertEquals(cliente1, clienteController.insereCliente(clienteDTO).getBody());
    }

    @Test
    @DisplayName("should return 201")
    void statusCode201_WhenDoesNotExists() {
      assertEquals(201, clienteController.insereCliente(clienteDTO).getStatusCode().value());
    }

    @Test
    @DisplayName("should return created cliente")
    void returnCliente_WhenDoesNotExists() {
      assertEquals(cliente1, clienteController.insereCliente(clienteDTO).getBody());
    }
  }

  @Nested
  @DisplayName("ClienteController#atualizaCliente")
  class AtualizaClienteTest {
    @BeforeEach
    void mockClienteServiceAtualizaCliente() {
      when(clienteService.transformarDTO(any(ClienteDTO.class))).thenReturn(cliente1);
    }

    @Test
    @DisplayName("should return 200 when cliente exists")
    void statusCode200_WhenClientesExists() {
      when(clienteService.atualizaCliente(any(Cliente.class))).thenReturn(cliente1);
      assertEquals(200, clienteController.atualizaCliente(clienteDTO, 1L).getStatusCode().value());
    }

    @Test
    @DisplayName("should return the cliente")
    void returnCliente_WhenClientesExists() {
      when(clienteService.atualizaCliente(any(Cliente.class))).thenReturn(cliente1);
      assertEquals(clienteController.atualizaCliente(clienteDTO, 1L).getBody(), cliente1);
    }
  }

  @Nested
  @DisplayName("ClienteController#deletaCliente")
  class DeleteClienteTest {
    @Test
    @DisplayName("should return 204 when cliente exists")
    void statusCode204_WhenClienteExists() {
      doNothing().when(clienteService).apagaCliente(any(long.class));
      assertEquals(204, clienteController.deleteCliente(1L).getStatusCode().value());
    }

    @Test
    @DisplayName("should return 404 when cliente doesn't exists")
    void statusCode204_WhenClienteDoesntExists() {
      doThrow(ObjectNotFoundException.class).when(clienteService).apagaCliente(any(long.class));
      assertEquals(404, clienteController.deleteCliente(1L).getStatusCode().value());
    }
  }
}
