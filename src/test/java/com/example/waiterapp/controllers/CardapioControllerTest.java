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
import org.springframework.http.ResponseEntity;

import com.example.waiterapp.models.Cardapio;
import com.example.waiterapp.dto.CardapioDTO;
import com.example.waiterapp.services.CardapioService;
import com.example.waiterapp.exceptions.ObjectNotFoundException;
import com.example.waiterapp.utils.MockForServletUriComponentsBuilderHelper;

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
      List<Cardapio> cardapios = cardapioController.listaCardapios().getBody();
      if(cardapios != null) {
        assertAll(
          () -> assertEquals(cardapios.get(0), cardapio1),
          () -> assertEquals(cardapios.get(1), cardapio2)
        );
      } else {
        fail("Cardapios is null");
      }
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
  // @Disabled("Disabled until ServletUriComponentsBuilder mock be possible")
  class InsereCardapioTest {
    CardapioDTO cardapioDTO;
    ResponseEntity<Object> response;

    @BeforeEach
    public void mockCardapioServiceInsereCardapio() {
      cardapioDTO = mock(CardapioDTO.class);

      when(cardapioService.transformarDTO(any(CardapioDTO.class))).thenReturn(cardapio1);
      when(cardapioService.insereCardapio(any(Cardapio.class))).thenReturn(cardapio1);
      response = MockForServletUriComponentsBuilderHelper.mockRequestResponse(cardapio1);
    }

    @Test
    @DisplayName("should return 201")
    public void statusCode201() {
      assertEquals(cardapioController.insereCardapio(cardapioDTO), response);
    }
  }

  @Nested
  @DisplayName("CardapioController#atualizaCardapio")
  class AtualizaCardapioTest {
    CardapioDTO cardapioDTO = mock(CardapioDTO.class);

    @BeforeEach
    public void mockCardapioServiceAtualizaCardapio() {
      when(cardapioService.transformarDTO(any(CardapioDTO.class))).thenReturn(cardapio1);
    }

    @Test
    @DisplayName("should return 200 when cardapio exists")
    public void statusCode200_WhenCardapiosExists() {
      when(cardapioService.atualizaCardapio(any(Cardapio.class))).thenReturn(cardapio1);
      assertEquals(cardapioController.atualizaCardapio(cardapioDTO, 1L).getStatusCode().value(), 200);
    }

    @Test
    @DisplayName("should return the cardapio")
    public void returnCardapio_WhenCardapiosExists() {
      when(cardapioService.atualizaCardapio(any(Cardapio.class))).thenReturn(cardapio1);
      assertEquals(cardapioController.atualizaCardapio(cardapioDTO, 1L).getBody(), cardapio1);
    }

    @Test
    @DisplayName("should return 404")
    public void statusCode404_WhenCardapiosDoesntExists() {
      when(cardapioService.atualizaCardapio(any(Cardapio.class))).thenThrow(ObjectNotFoundException.class);
      assertEquals(cardapioController.atualizaCardapio(cardapioDTO, 1L).getStatusCode().value(), 404);
    }
  }

  @Nested
  @DisplayName("CardapioController#deletaCardapio")
  class DeleteCardapioTest {
    @Test
    @DisplayName("should return 204 when cardapio exists")
    public void statusCode204_WhenCardapioExists() {
      doNothing().when(cardapioService).apagaCardapio(any(long.class));
      assertEquals(cardapioController.deleteCardapio(1L).getStatusCode().value(), 204);
    }

    @Test
    @DisplayName("should return 404 when cardapio doesn't exists")
    public void statusCode204_WhenCardapioDoesntExists() {
      doThrow(ObjectNotFoundException.class).when(cardapioService).apagaCardapio(any(long.class));
      assertEquals(cardapioController.deleteCardapio(1L).getStatusCode().value(), 404);
    }
  }
}
