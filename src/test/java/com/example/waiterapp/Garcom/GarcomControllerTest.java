package com.example.waiterapp.Garcom;

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

import com.example.waiterapp.garcom.Garcom;
import com.example.waiterapp.garcom.GarcomController;
import com.example.waiterapp.garcom.GarcomDTO;
import com.example.waiterapp.garcom.GarcomService;
import com.example.waiterapp.exceptions.ObjectNotFoundException;

@DisplayName("GarcomController's tests")
public class GarcomControllerTest {
  static GarcomController garcomController;

  @Mock
  Garcom garcom1 = mock(Garcom.class);
  Garcom garcom2 = mock(Garcom.class);
  static GarcomService garcomService = mock(GarcomService.class);

  @BeforeAll
  public static void inicializaGarcomController() {
    garcomController = new GarcomController(garcomService);
  }

  @Nested
  @DisplayName("GarcomController#listaGarcons")
  class ListaGarconsTest {
    @BeforeEach
    public void mockGarcomServiceListaGarcons() {
      List<Garcom> garcoms = new ArrayList<Garcom>(List.of(garcom1, garcom2));
      when(garcomService.listaGarcons()).thenReturn(garcoms);
    }

    @Test
    @DisplayName("should return 200")
    public void statusCode200() {
      assertEquals(garcomController.listaGarcons().getStatusCode().value(), 200);
    }

    @Test
    @DisplayName("should return a list of garcoms")
    public void returnListOfGarcons() {
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
  @DisplayName("GarcomController#retornaGarcomById")
  class RetornaGarcomByIdTest {
    @Test
    @Disabled("must be fixed")
    @DisplayName("should return 200 when garcom exists")
    public void statusCode200_WhenGarconsExists() {
      when(garcomService.retornaGarcomById(1L)).thenReturn(garcom1);
      assertEquals(garcomController.retornaGarcomById(1L).getStatusCode().value(), 200);
    }

    @Test
    @DisplayName("should return 404 when garcom doesn't exists")
    public void statusCode404_WhenGarconsDoesntExists() {
      when(garcomService.retornaGarcomById(1L)).thenThrow(ObjectNotFoundException.class);
      assertEquals(garcomController.retornaGarcomById(1L).getStatusCode().value(), 404);
    }

    @Test
    @DisplayName("should return the garcom when garcom exists")
    public void returnGarcom_WhenGarconsExists() {
      when(garcomService.retornaGarcomById(any(long.class))).thenReturn(garcom1);
      assertEquals(garcomController.retornaGarcomById(1L).getBody(), garcom1);
    }
  }

  @Nested
  @DisplayName("GarcomController#insereGarcom")
  @Disabled("Disabled until ServletUriComponentsBuilder mock be possible")
  class InsereGarcomTest {
    GarcomDTO garcomDTO;

    @BeforeEach
    public void mockGarcomServiceInsereGarcom() {
      garcomDTO = mock(GarcomDTO.class);
      when(garcomService.insereGarcom(any(Garcom.class))).thenReturn(garcom1);
      // when(ServletUriComponentsBuilder.fromCurrentRequest()).thenReturn(mock(ServletUriComponentsBuilder.class));
    }

    @Test
    @DisplayName("should return 201")
    public void statusCode201() {
      assertEquals(garcomController.insereGarcom(garcomDTO).getStatusCode().value(), 201);
    }

    @Test
    @DisplayName("should return the garcom - method must be refactored to pass this test")
    public void returnGarcom() {
      assertEquals(garcomController.insereGarcom(garcomDTO).getBody(), garcom1);
    }
  }

  @Nested
  @DisplayName("GarcomController#atualizaGarcom")
  class AtualizaGarcomTest {
    GarcomDTO garcomDTO = mock(GarcomDTO.class);

    @BeforeEach
    public void mockGarcomServiceAtualizaGarcom() {
      when(garcomService.transformarDTO(any(GarcomDTO.class))).thenReturn(garcom1);
    }

    @Test
    @DisplayName("should return 200 when garcom exists")
    public void statusCode200_WhenGarconsExists() {
      when(garcomService.atualizaGarcom(any(Garcom.class))).thenReturn(garcom1);
      assertEquals(garcomController.atualizaGarcom(garcomDTO, 1L).getStatusCode().value(), 200);
    }

    @Test
    @Disabled("Disabled until garcom be returned on update")
    @DisplayName("should return the garcom")
    public void returnGarcom_WhenGarconsExists() {
      when(garcomService.atualizaGarcom(any(Garcom.class))).thenReturn(garcom1);
      assertEquals(garcomController.atualizaGarcom(garcomDTO, 1L).getBody(), garcom1);
    }

    @Test
    @DisplayName("should return 404")
    public void statusCode404_WhenGarconsDoesntExists() {
      when(garcomService.atualizaGarcom(any(Garcom.class))).thenThrow(ObjectNotFoundException.class);
      assertEquals(garcomController.atualizaGarcom(garcomDTO, 1L).getStatusCode().value(), 404);
    }
  }

  @Nested
  @DisplayName("GarcomController#deletaGarcom")
  class DeleteGarcomTest {
    @Test
    @DisplayName("should return 204 when garcom exists")
    public void statusCode204_WhenGarcomExists() {
      doNothing().when(garcomService).apagaGarcom(any(long.class));
      assertEquals(garcomController.deleteGarcom(1L).getStatusCode().value(), 204);
    }

    @Test
    @DisplayName("should return 404 when garcom doesn't exists")
    public void statusCode204_WhenGarcomDoesntExists() {
      doThrow(ObjectNotFoundException.class).when(garcomService).apagaGarcom(any(long.class));
      assertEquals(garcomController.deleteGarcom(1L).getStatusCode().value(), 404);
    }
  }
}
