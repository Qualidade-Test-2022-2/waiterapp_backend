package com.example.waiterapp.Cardapio;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
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

import com.example.waiterapp.cardapio.Cardapio;
import com.example.waiterapp.cardapio.CardapioController;
import com.example.waiterapp.cardapio.CardapioRepository;
import com.example.waiterapp.cardapio.CardapioService;
import com.example.waiterapp.exceptions.ObjectNotFoundException;

@DisplayName("CardapioController's tests")
public class CardapioControllerTest {
  static CardapioController cardapioController;

  @Mock
  Cardapio cardapio1 = mock(Cardapio.class);
  Cardapio cardapio2 = mock(Cardapio.class);
  static CardapioService cardapioService = mock(CardapioService.class);

  @BeforeAll
  public static void inicializaCardapioController() {
    cardapioController = new CardapioController(cardapioService);
  }

  @Nested
  @DisplayName("CardapioController#listaCardapios")
  class CardapioControllerListaCardapiosTest {
    @BeforeEach
    public void mockCardapioServiceListaCardapios() {
      List<Cardapio> cardapios = new ArrayList<Cardapio>(List.of(cardapio1, cardapio2));
      when(cardapioService.listaCardapios()).thenReturn(cardapios);
    }

    @Test
    @DisplayName("should return 200")
    public void statusCode200() {
      assertEquals(cardapioController.listaCardapios().getStatusCode().value(), 200);
    }

    @Test
    @DisplayName("should return a list of cardapios")
    public void returnListOfCardapios() {
      assertAll(
        () -> assertEquals(cardapioController.listaCardapios().getBody().get(0), cardapio1),
        () -> assertEquals(cardapioController.listaCardapios().getBody().get(1), cardapio2)
      );
    }
  }

  @Nested
  @DisplayName("CardapioController#retornaCardapioById")
  class CardapioControllerRetornaCardapioByIdTest {
    @Test
    @DisplayName("should return 200 when cardapio exists")
    public void statusCode200_WhenCardapiosExists() {
      when(cardapioService.retornaCardapioById((long) 1)).thenReturn(cardapio1);
      assertEquals(cardapioController.retornaCardapioById((long) 1).getStatusCode().value(), 200);
    }

    @Test
    @DisplayName("should return 404 when cardapio doesn't exists")
    public void statusCode404_WhenCardapiosDoesntExists() {
      when(cardapioService.retornaCardapioById((long) 1)).thenThrow(ObjectNotFoundException.class);
      assertEquals(cardapioController.retornaCardapioById((long) 1).getStatusCode().value(), 404);
    }

    @Test
    @DisplayName("should return the cardapio when cardapio exists")
    public void returnCardapio_WhenCardapiosExists() {
      when(cardapioService.retornaCardapioById((long) 1)).thenReturn(cardapio1);
      assertEquals(cardapioController.retornaCardapioById((long) 1).getBody(), cardapio1);
    }
  }
}
