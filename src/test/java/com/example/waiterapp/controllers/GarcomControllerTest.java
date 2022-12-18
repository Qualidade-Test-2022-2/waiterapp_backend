package com.example.waiterapp.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.example.waiterapp.models.Garcom;
import com.example.waiterapp.dto.GarcomDTO;
import com.example.waiterapp.services.GarcomService;
import com.example.waiterapp.exceptions.ObjectNotFoundException;

@DisplayName("GarcomController's tests")
class GarcomControllerTest {
  static GarcomController garcomController;

  @Mock
  Garcom garcom1 = mock(Garcom.class);
  Garcom garcom2 = mock(Garcom.class);
  static GarcomService garcomService = mock(GarcomService.class);

  @BeforeAll
  static void inicializaGarcomController() {
    garcomController = new GarcomController(garcomService);
  }

  @Nested
  @DisplayName("GarcomController#listaGarcons")
  class ListaGarconsTest {
    @BeforeEach
    void mockGarcomServiceListaGarcons() {
      List<Garcom> garcoms = new ArrayList<Garcom>(List.of(garcom1, garcom2));
      when(garcomService.listaGarcons()).thenReturn(garcoms);
    }

    @Test
    @DisplayName("should return 200")
    void statusCode200() {
      assertEquals(garcomController.listaGarcons().getStatusCode().value(), 200);
    }

    @Test
    @DisplayName("should return a list of garcoms")
    void returnListOfGarcons() {
      List<Garcom> garcoms = garcomController.listaGarcons().getBody();
      if(garcoms != null) {
        assertAll(
          () -> assertEquals(garcoms.get(0), garcom1),
          () -> assertEquals(garcoms.get(1), garcom2)
        );
      } else {
        fail("Garcons is null");
      }
    }
  }

  @Nested
  @DisplayName("GarcomController#authenticate")
  class LoginGarcomTest {
    @Test
    @DisplayName("should return 200 when cliente exists and isWaiterAuthorized return true")
    void statusCode200_WhenGarcomExistsAndisWaiterAuthorizedReturnTrue() {
      when(garcomService.retornaGarcomByCpf(anyString())).thenReturn(garcom1);
      when(garcomService.isWaiterAuthorized(any(Garcom.class), anyString(), anyString())).thenReturn(true);
      assertEquals(200, garcomController.authenticate("MTYyMTM0ODc3NjA6cmFkYQ==").getStatusCode().value());
    }

    @Test
    @DisplayName("should return 400 when cliente exists and isWaiterAuthorized return false")
    void statusCode404_WhenGarcomExistsAndisWaiterAuthorizedReturnFalse() {
      when(garcomService.retornaGarcomByCpf(any(String.class))).thenReturn(garcom1);
      when(garcomService.isWaiterAuthorized(any(Garcom.class), anyString(), anyString())).thenReturn(false);
      assertEquals(404, garcomController.authenticate("MTYyMTM0ODc3NjA6cmFkYQ==").getStatusCode().value());
    }
  }

  @Nested
  @DisplayName("GarcomController#retornaGarcomById")
  class RetornaGarcomByIdTest {
    @Test
    @DisplayName("should return 200 when garcom exists")
    void statusCode200_WhenGarconsExists() {
      when(garcomService.retornaGarcomById(anyLong())).thenReturn(garcom1);
      assertEquals(garcomController.retornaGarcomById(1L).getStatusCode().value(), 200);
    }

    @Test
    @DisplayName("should return 404 when garcom doesn't exists")
    void statusCode404_WhenGarconsDoesntExists() {
      when(garcomService.retornaGarcomById(1L)).thenThrow(ObjectNotFoundException.class);
      assertEquals(garcomController.retornaGarcomById(1L).getStatusCode().value(), 404);
    }

    @Test
    @DisplayName("should return the garcom when garcom exists")
    void returnGarcom_WhenGarconsExists() {
      when(garcomService.retornaGarcomById(any(long.class))).thenReturn(garcom1);
      assertEquals(garcomController.retornaGarcomById(1L).getBody(), garcom1);
    }
  }

  @Nested
  @DisplayName("GarcomController#insereGarcom")
  class InsereGarcomTest {
    GarcomDTO garcomDTO;

    @BeforeEach
    void mockGarcomServiceInsereGarcom() {
      garcomDTO = mock(GarcomDTO.class);
      when(garcomService.transformarDTO(any(GarcomDTO.class))).thenReturn(garcom1);
      when(garcomService.insereGarcom(any(Garcom.class))).thenReturn(garcom1);
    }

    @Test
    @DisplayName("should return 201")
    void statusCode201() {
      assertEquals(201, garcomController.insereGarcom(garcomDTO).getStatusCode().value());
    }

    @Test
    @DisplayName("should return the garcom")
    void returnGarcom() {
      assertEquals(garcom1, garcomController.insereGarcom(garcomDTO).getBody());
    }
  }

  @Nested
  @DisplayName("GarcomController#atualizaGarcom")
  class AtualizaGarcomTest {
    GarcomDTO garcomDTO = mock(GarcomDTO.class);

    @BeforeEach
    void mockGarcomServiceAtualizaGarcom() {
      when(garcomService.transformarDTO(any(GarcomDTO.class))).thenReturn(garcom1);
    }

    @Test
    @DisplayName("should return 200 when garcom exists")
    void statusCode200_WhenGarconsExists() {
      when(garcomService.atualizaGarcom(any(Garcom.class))).thenReturn(garcom1);
      assertEquals(garcomController.atualizaGarcom(garcomDTO, 1L).getStatusCode().value(), 200);
    }

    @Test
    @DisplayName("should return the garcom")
    void returnGarcom_WhenGarconsExists() {
      when(garcomService.atualizaGarcom(any(Garcom.class))).thenReturn(garcom1);
      assertEquals(garcomController.atualizaGarcom(garcomDTO, 1L).getBody(), garcom1);
    }

    @Test
    @DisplayName("should return 404")
    void statusCode404_WhenGarconsDoesntExists() {
      when(garcomService.atualizaGarcom(any(Garcom.class))).thenThrow(ObjectNotFoundException.class);
      assertEquals(garcomController.atualizaGarcom(garcomDTO, 1L).getStatusCode().value(), 404);
    }
  }

  @Nested
  @DisplayName("GarcomController#deletaGarcom")
  class DeleteGarcomTest {
    @Test
    @DisplayName("should return 204 when garcom exists")
    void statusCode204_WhenGarcomExists() {
      doNothing().when(garcomService).apagaGarcom(any(long.class));
      assertEquals(garcomController.deleteGarcom(1L).getStatusCode().value(), 204);
    }

    @Test
    @DisplayName("should return 404 when garcom doesn't exists")
    void statusCode204_WhenGarcomDoesntExists() {
      doThrow(ObjectNotFoundException.class).when(garcomService).apagaGarcom(any(long.class));
      assertEquals(garcomController.deleteGarcom(1L).getStatusCode().value(), 404);
    }
  }
}
