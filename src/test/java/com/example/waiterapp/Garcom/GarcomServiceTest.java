package com.example.waiterapp.Garcom;

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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.waiterapp.models.Garcom;
import com.example.waiterapp.dto.GarcomDTO;
import com.example.waiterapp.repositories.GarcomRepository;
import com.example.waiterapp.services.GarcomService;
import com.example.waiterapp.exceptions.ObjectNotFoundException;

@DisplayName("GarcomService's tests")
class GarcomServiceTest {
  static GarcomService garcomService;

  @Mock
  Garcom garcom1 = mock(Garcom.class);
  Garcom garcom2 = mock(Garcom.class);
  static GarcomRepository garcomRepository = mock(GarcomRepository.class);

  @BeforeAll
  public static void inicializaGarcomService() {
    garcomService = new GarcomService(garcomRepository);
  }

  @Nested
  @DisplayName("GarcomService#listaGarcons")
  class ListaGarconsTest {
    @BeforeEach
    public void mockGarcomRepositoryFindAll() {
      ArrayList<Garcom> garcons = new ArrayList<Garcom>(List.of(garcom1, garcom2));
      when(garcomRepository.findAll()).thenReturn(garcons);
    }

    @Test
    @DisplayName("should return a list of garcons")
    public void returnListOfGarcons() {
      assertEquals(garcomService.listaGarcons().get(0), garcom1);
      assertEquals(garcomService.listaGarcons().get(1), garcom2);
    }
  }

  @Nested
  @DisplayName("GarcomService#transformarDTO")
  class TransformarDTOTest {
    @Mock
    GarcomDTO garcomDTO = mock(GarcomDTO.class);

    @Test
    @DisplayName("should transform a GarcomDTO into a Garcom")
    public void transformGarcomDTOIntoGarcom() {
      Garcom garcomTransformado = garcomService.transformarDTO(garcomDTO);

      assertAll(
        () -> assertEquals(garcomTransformado.getId(), garcomDTO.getId()),
        () -> assertEquals(garcomTransformado.getNome(), garcomDTO.getNome()),
        () -> assertEquals(garcomTransformado.getDataCriacao(), garcomDTO.getDataCriacao()),
        () -> assertEquals(garcomTransformado.getCpf(), garcomDTO.getCpf())
      );
    }
  }

  @Nested
  @DisplayName("GarcomService#returnGarcomById")
  class RetornaGarcomByIdTest {
    @Test
    @DisplayName("should return a garcom by id when it exists")
    public void returnGarcomWhenGarcomExist() {
      when(garcomRepository.findById(anyLong())).thenReturn(java.util.Optional.of(garcom1));
      assertEquals(garcomService.retornaGarcomById(1L), garcom1);
    }

    @Test
    @DisplayName("should throw an exception when the garcom does not exist")
    public void throwExceptionWhenGarcomDoesNotExist() {
      when(garcomRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());
      assertThrows(ObjectNotFoundException.class, () -> garcomService.retornaGarcomById(1L));
    }
  }

  @Nested
  @DisplayName("GarcomService#insereGarcom")
  class InsereGarcomTest {
    @Test
    @DisplayName("should insert a garcom")
    public void insertGarcom() {
      when(garcomRepository.save(garcom1)).thenReturn(garcom1);
      assertEquals(garcomService.insereGarcom(garcom1), garcom1);
    }
  }

  @Nested
  @DisplayName("GarcomService#atualizaGarcom")
  class AtualizaGarcomTest {
    @BeforeEach
    public void mockGarcomRepositoryFindById() {
      when(garcomRepository.findById(anyLong())).thenReturn(java.util.Optional.of(garcom1));
      when(garcomRepository.save(garcom1)).thenReturn(garcom1);
    }

    @Test
    @DisplayName("should update a garcom")
    public void updateGarcom() {
      assertEquals(garcomService.atualizaGarcom(garcom1), garcom1);
    }
  }

  @Nested
  @DisplayName("GarcomService#apagaGarcom")
  class ApagaGarcomTest {
    @BeforeEach
    public void mockGarcomRepositoryFindById() {
      when(garcomRepository.findById(anyLong())).thenReturn(java.util.Optional.of(garcom1));
    }

    @Test
    @DisplayName("should delete a garcom")
    public void deleteGarcom() {
      doNothing().when(garcomRepository).deleteById(anyLong());
      garcomService.apagaGarcom(1L);
    }

    @Test
    @DisplayName("should delete a garcom")
    public void throwExceptionWhenCouldNotDeleteGarcom() {
      doThrow(DataIntegrityViolationException.class).when(garcomRepository).deleteById(anyLong());

      assertThrows(DataIntegrityViolationException.class, () -> garcomService.apagaGarcom(1L));
    }
  }
}
