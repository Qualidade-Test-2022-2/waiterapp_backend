package com.example.waiterapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.example.waiterapp.models.Garcom;
import com.example.waiterapp.models.Pedido;
import com.example.waiterapp.dto.GarcomDTO;
import com.example.waiterapp.repositories.GarcomRepository;
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
  @DisplayName("GarcomService#retornaGarcomByCpf")
  class RetornaGarcomByCpfTest {
    @Test
    @DisplayName("should return a garcom by id when it exists")
    public void returnGarcomWhenGarcomExist() {
      when(garcomRepository.findByCpf(any(String.class))).thenReturn(java.util.Optional.of(garcom1));
      assertEquals(garcomService.retornaGarcomByCpf("11111111111"), garcom1);
    }

    @Test
    @DisplayName("should return a null when does not exists")
    public void returnNullWhenGarcomDoesNotExists() {
      when(garcomRepository.findByCpf(any(String.class))).thenReturn(Optional.empty());
      assertEquals(garcomService.retornaGarcomByCpf("11111111111"), null);
    }
  }

  @Nested
  @DisplayName("ClienteService#transformarDTO")
  class TransformarDTOTest {
    @Mock
    GarcomDTO garcomDTO = mock(GarcomDTO.class);

    @BeforeEach
    public void mockGarcomDTO() {
      when(garcomDTO.getId()).thenReturn(1L);
      when(garcomDTO.getNome()).thenReturn("Garcom 1");
      when(garcomDTO.getDataCriacao()).thenReturn(LocalDateTime.of(2022, 01, 01, 00, 00));
      when(garcomDTO.getEmail()).thenReturn("cliente1@email.com");
      when(garcomDTO.getCpf()).thenReturn("11111111111");
      when(garcomDTO.getPedidos()).thenReturn(new ArrayList<Pedido>());
      when(garcomDTO.getPassword()).thenReturn("123456");
    }

    @Test
    @Disabled
    @DisplayName("should transform a GarcomDTO into a Garcom")
    public void transformGarcomDTOIntoGarcom() {
      Garcom garcomTransformado = garcomService.transformarDTO(garcomDTO);
      assertEquals(garcomDTO.getId(), garcomTransformado.getId());
      assertEquals(garcomDTO.getNome(), garcomTransformado.getNome());
      assertEquals(garcomDTO.getDataCriacao(), garcomTransformado.getDataCriacao());
      assertEquals(garcomDTO.getEmail(), garcomTransformado.getEmail());
      assertEquals(garcomDTO.getPedidos(), garcomTransformado.getPedidos());
      assertTrue(BCrypt.checkpw("123456", garcomTransformado.getPassword()));

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
