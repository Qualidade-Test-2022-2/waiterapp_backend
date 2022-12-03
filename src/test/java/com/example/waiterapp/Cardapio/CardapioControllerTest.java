package com.example.waiterapp.Cardapio;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.waiterapp.cardapio.Cardapio;
import com.example.waiterapp.cardapio.CardapioController;
import com.example.waiterapp.cardapio.CardapioDTO;
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
  class ListaCardapiosTest {
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
  class RetornaCardapioByIdTest {
    @Test
    @DisplayName("should return 200 when cardapio exists")
    public void statusCode200_WhenCardapiosExists() {
      when(cardapioService.retornaCardapioById(1L)).thenReturn(cardapio1);
      assertEquals(cardapioController.retornaCardapioById(1L).getStatusCode().value(), 200);
    }

    @Test
    @DisplayName("should return 404 when cardapio doesn't exists")
    public void statusCode404_WhenCardapiosDoesntExists() {
      when(cardapioService.retornaCardapioById(1L)).thenThrow(ObjectNotFoundException.class);
      assertEquals(cardapioController.retornaCardapioById(1L).getStatusCode().value(), 404);
    }

    @Test
    @DisplayName("should return the cardapio when cardapio exists")
    public void returnCardapio_WhenCardapiosExists() {
      when(cardapioService.retornaCardapioById(any(long.class))).thenReturn(cardapio1);
      assertEquals(cardapioController.retornaCardapioById(1L).getBody(), cardapio1);
    }
  }

  @Nested
  @DisplayName("CardapioController#insereCardapio")
  @Disabled("Disabled until ServletUriComponentsBuilder mock be possible")
  class InsereCardapioTest {
    CardapioDTO cardapioDTO;

    @BeforeEach
    public void mockCardapioServiceInsereCardapio() {
      cardapioDTO = mock(CardapioDTO.class);
      when(cardapioService.insereCardapio(any(Cardapio.class))).thenReturn(cardapio1);
      // when(ServletUriComponentsBuilder.fromCurrentRequest()).thenReturn(mock(ServletUriComponentsBuilder.class));
    }

    @Test
    @DisplayName("should return 201")
    public void statusCode201() {
      assertEquals(cardapioController.insereCardapio(cardapioDTO).getStatusCode().value(), 201);
    }

    @Test
    @DisplayName("should return the cardapio - method must be refactored to pass this test")
    public void returnCardapio() {
      assertEquals(cardapioController.insereCardapio(cardapioDTO).getBody(), cardapio1);
    }
  }

  @Nested
  @DisplayName("CardapioController#atualizaCardapio")
  class AtualizaCardapioTest {
    CardapioDTO cardapioDTO = mock(CardapioDTO.class);

    @Nested
    @DisplayName("when cardapio exists")
    class WhenCardapiosExists {
      @BeforeEach
      public void mockCardapioServiceAtualizaCardapio() {
        when(cardapioService.transformarDTO(any(CardapioDTO.class))).thenReturn(cardapio1);
        when(cardapioService.atualizaCardapio(any(Cardapio.class))).thenReturn(cardapio1);
      }

      @Test
      @DisplayName("should return 200")
      public void statusCode200() {
        assertEquals(cardapioController.atualizaCardapio(cardapioDTO, 1L).getStatusCode().value(), 200);
      }

      @Test
      @DisplayName("should return the cardapio - method must be refactored to pass this test")
      public void returnCardapio() {
        assertEquals(cardapioController.atualizaCardapio(cardapioDTO, 1L).getBody(), cardapio1);
      }
    }

    @Nested
    @DisplayName("when cardapio doesn't exists")
    class WhenCardapiosDoesntExists {
      @BeforeEach
      public void mockCardapioServiceAtualizaCardapio() {
        when(cardapioService.transformarDTO(any(CardapioDTO.class))).thenReturn(cardapio1);
        when(cardapioService.atualizaCardapio(any(Cardapio.class))).thenThrow(ObjectNotFoundException.class);
      }

      @Test
      @DisplayName("should return 404")
      public void statusCode404() {
        assertEquals(cardapioController.atualizaCardapio(cardapioDTO, 1L).getStatusCode().value(), 404);
      }
    }
  }
}
