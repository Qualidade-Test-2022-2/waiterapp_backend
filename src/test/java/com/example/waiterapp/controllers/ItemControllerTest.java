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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.example.waiterapp.models.Item;
import com.example.waiterapp.dto.ItemDTO;
import com.example.waiterapp.services.ItemService;
import com.example.waiterapp.exceptions.ObjectNotFoundException;

@DisplayName("ItemController's tests")
public class ItemControllerTest {
  static ItemController itemController;

  @Mock
  Item item1 = mock(Item.class);
  Item item2 = mock(Item.class);
  static ItemService itemService = mock(ItemService.class);

  @BeforeAll
  public static void inicializaItemController() {
    itemController = new ItemController(itemService);
  }

  @Nested
  @DisplayName("ItemController#listaItens")
  class ListaItensTest {
    @BeforeEach
    public void mockItemServiceListaItens() {
      List<Item> items = new ArrayList<Item>(List.of(item1, item2));
      when(itemService.listaItens()).thenReturn(items);
    }

    @Test
    @DisplayName("should return 200")
    public void statusCode200() {
      assertEquals(itemController.listaItens().getStatusCode().value(), 200);
    }

    @Test
    @DisplayName("should return a list of items")
    public void returnListOfItens() {
      List<Item> items = itemController.listaItens().getBody();
      if(items != null) {
        assertAll(
          () -> assertEquals(items.get(0), item1),
          () -> assertEquals(items.get(1), item2)
        );
      } else {
        fail("Itens is null");
      }
    }
  }

  @Nested
  @DisplayName("ItemController#retornaItemById")
  class RetornaItemByIdTest {
    @Test
    @DisplayName("should return 200 when item exists")
    public void statusCode200_WhenItensExists() {
      when(itemService.retornaItemById(1L)).thenReturn(item1);
      assertEquals(itemController.retornaItemById(1L).getStatusCode().value(), 200);
    }

    @Test
    @DisplayName("should return 404 when item doesn't exists")
    public void statusCode404_WhenItensDoesntExists() {
      when(itemService.retornaItemById(1L)).thenThrow(ObjectNotFoundException.class);
      assertEquals(itemController.retornaItemById(1L).getStatusCode().value(), 404);
    }

    @Test
    @DisplayName("should return the item when item exists")
    public void returnItem_WhenItensExists() {
      when(itemService.retornaItemById(any(long.class))).thenReturn(item1);
      assertEquals(itemController.retornaItemById(1L).getBody(), item1);
    }
  }

  @Nested
  @DisplayName("ItemController#insereItem")
  @Disabled("Disabled until ServletUriComponentsBuilder mock be possible")
  class InsereItemTest {
    ItemDTO itemDTO;

    @BeforeEach
    public void mockItemServiceInsereItem() {
      itemDTO = mock(ItemDTO.class);
      when(itemService.insereItem(any(Item.class))).thenReturn(item1);
    }

    @Test
    @DisplayName("should return 201")
    public void statusCode201() {
      assertEquals(itemController.insereItem(itemDTO).getStatusCode().value(), 201);
    }

    @Test
    @DisplayName("should return the item")
    public void returnItem() {
      assertEquals(itemController.insereItem(itemDTO).getBody(), item1);
    }
  }

  @Nested
  @DisplayName("ItemController#atualizaItem")
  class AtualizaItemTest {
    ItemDTO itemDTO = mock(ItemDTO.class);

    @BeforeEach
    public void mockItemServiceAtualizaItem() {
      when(itemService.transformarDTO(any(ItemDTO.class))).thenReturn(item1);
    }

    @Test
    @DisplayName("should return 200 when item exists")
    public void statusCode200_WhenItensExists() {
      when(itemService.atualizaItem(any(Item.class))).thenReturn(item1);
      assertEquals(itemController.atualizaItem(itemDTO, 1L).getStatusCode().value(), 200);
    }

    @Test
    @Disabled("Disabled until item be returned on update")
    @DisplayName("should return the item")
    public void returnItem_WhenItensExists() {
      when(itemService.atualizaItem(any(Item.class))).thenReturn(item1);
      assertEquals(itemController.atualizaItem(itemDTO, 1L).getBody(), item1);
    }

    @Test
    @DisplayName("should return 404")
    public void statusCode404_WhenItensDoesntExists() {
      when(itemService.atualizaItem(any(Item.class))).thenThrow(ObjectNotFoundException.class);
      assertEquals(itemController.atualizaItem(itemDTO, 1L).getStatusCode().value(), 404);
    }
  }

  @Nested
  @DisplayName("ItemController#deletaItem")
  class DeleteItemTest {
    @Test
    @DisplayName("should return 204 when item exists")
    public void statusCode204_WhenItemExists() {
      doNothing().when(itemService).apagaItem(any(long.class));
      assertEquals(itemController.deleteItem(1L).getStatusCode().value(), 204);
    }

    @Test
    @DisplayName("should return 404 when item doesn't exists")
    public void statusCode204_WhenItemDoesntExists() {
      doThrow(ObjectNotFoundException.class).when(itemService).apagaItem(any(long.class));
      assertEquals(itemController.deleteItem(1L).getStatusCode().value(), 404);
    }
  }
}
