package com.example.waiterapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.waiterapp.models.Cardapio;
import com.example.waiterapp.dto.CardapioDTO;
import com.example.waiterapp.repositories.CardapioRepository;
import com.example.waiterapp.exceptions.ObjectNotFoundException;

@DisplayName("CardapioService's tests")
class CardapioServiceTest {
  static CardapioService cardapioService;

  @Mock
  Cardapio cardapio1 = mock(Cardapio.class);
  Cardapio cardapio2 = mock(Cardapio.class);
  static CardapioRepository cardapioRepository = mock(CardapioRepository.class);

  @BeforeAll
  static void inicializaCardapioService() {
    cardapioService = new CardapioService(cardapioRepository);
  }

  @Nested
  @DisplayName("CardapioService#listaCardapios")
  class ListaCardapiosTest {
    @BeforeEach
    void mockCardapioRepositoryFindAll() {
      ArrayList<Cardapio> cardapios = new ArrayList<Cardapio>(List.of(cardapio1, cardapio2));
      when(cardapioRepository.findAll()).thenReturn(cardapios);
    }

    @Test
    @DisplayName("should return a list of cardapios")
    void returnListOfCardapios() {
      assertEquals(cardapioService.listaCardapios().get(0), cardapio1);
      assertEquals(cardapioService.listaCardapios().get(1), cardapio2);
    }
  }

  @Nested
  @DisplayName("CardapioService#transformarDTO")
  class TransformarDTOTest {
    @Mock
    CardapioDTO cardapioDTO = mock(CardapioDTO.class);

    @BeforeEach
    void mockCardapioDTO() {
      when(cardapioDTO.getId()).thenReturn(1L);
      when(cardapioDTO.getTitulo()).thenReturn("Cardapio 1");
      when(cardapioDTO.getDataCriacao()).thenReturn(LocalDateTime.of(2022, 01, 01, 00, 00));
      when(cardapioDTO.getDescricao()).thenReturn("Cardapio 1 muito bom");
      when(cardapioDTO.getItems()).thenReturn(new ArrayList<>());
    }

    @Test
    @DisplayName("should transform a CardapioDTO into a Cardapio")
    void transformCardapioDTOIntoCardapio() {
      Cardapio cardapioTransformado = cardapioService.transformarDTO(cardapioDTO);

      assertEquals(cardapioTransformado.getId(), cardapioDTO.getId());
      assertEquals(cardapioTransformado.getTitulo(), cardapioDTO.getTitulo());
      assertEquals(cardapioTransformado.getDataCriacao(), cardapioDTO.getDataCriacao());
      assertEquals(cardapioTransformado.getDescricao(), cardapioDTO.getDescricao());
      assertEquals(cardapioTransformado.getItems(), cardapioDTO.getItems());
    }
  }

  @Nested
  @DisplayName("CardapioService#returnCardapioById")
  class RetornaCardapioByIdTest {
    @Test
    @DisplayName("should return a cardapio by id when it exists")
    void returnCardapioWhenCardapioExist() {
      when(cardapioRepository.findById(anyLong())).thenReturn(java.util.Optional.of(cardapio1));
      assertEquals(cardapioService.retornaCardapioById(1L), cardapio1);
    }

    @Test
    @DisplayName("should throw an exception when the cardapio does not exist")
    void throwExceptionWhenCardapioDoesNotExist() {
      when(cardapioRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());
      assertThrows(ObjectNotFoundException.class, () -> cardapioService.retornaCardapioById(1L));
    }
  }

  @Nested
  @DisplayName("CardapioService#insereCardapio")
  class InsereCardapioTest {
    @Test
    @DisplayName("should insert a cardapio")
    void insertCardapio() {
      when(cardapioRepository.save(cardapio1)).thenReturn(cardapio1);
      assertEquals(cardapioService.insereCardapio(cardapio1), cardapio1);
    }
  }

  @Nested
  @DisplayName("CardapioService#atualizaCardapio")
  class AtualizaCardapioTest {
    @BeforeEach
    void mockCardapioRepositoryFindById() {
      when(cardapioRepository.findById(anyLong())).thenReturn(java.util.Optional.of(cardapio1));
      when(cardapioRepository.save(cardapio1)).thenReturn(cardapio1);
    }

    @Test
    @DisplayName("should update a cardapio")
    void updateCardapio() {
      assertEquals(cardapioService.atualizaCardapio(cardapio1), cardapio1);
    }
  }

  @Nested
  @DisplayName("CardapioService#apagaCardapio")
  class ApagaCardapioTest {
    @BeforeEach
    void mockCardapioRepositoryFindById() {
      when(cardapioRepository.findById(anyLong())).thenReturn(java.util.Optional.of(cardapio1));
    }

    @Test
    @DisplayName("should delete a cardapio")
    void deleteCardapio() {
      doNothing().when(cardapioRepository).deleteById(anyLong());
      cardapioService.apagaCardapio(1L);
      assertTrue(true);
    }

    @Test
    @DisplayName("should delete a cardapio")
    void throwExceptionWhenCouldNotDeleteCardapio() {
      doThrow(DataIntegrityViolationException.class).when(cardapioRepository).deleteById(anyLong());

      assertThrows(DataIntegrityViolationException.class, () -> cardapioService.apagaCardapio(1L));
    }
  }
}
