
package com.example.waiterapp.Cliente;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.waiterapp.cliente.Cliente;

@DisplayName("Testes dos métodos da classe Cliente")
public class ClienteTest {
  private static Cliente cliente;

  public static Cliente criaCliente(int id, String name, String email, String cpf) {
    LocalDateTime dataCriacao = LocalDateTime.now();
    return new Cliente((long) id, name, email, cpf, dataCriacao);
  }

  @BeforeAll
  public static void inicializaCliente() {
    cliente = criaCliente(1, "Rafael", "rafael@email.com", "12345678910");
  }

  @DisplayName("Cliente#equals deve ser falso com clientes diferentes")
  @Test
  public void equals_ClientesDiferentes_False() {
    Cliente cliente2 = criaCliente(2, "Tais", "tais@email.com", "11111111111");

    assertFalse(cliente.equals(cliente2));
  }
}
