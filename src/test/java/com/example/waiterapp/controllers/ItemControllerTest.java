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

import com.example.waiterapp.models.Item;
import com.example.waiterapp.dto.ItemDTO;
import com.example.waiterapp.services.ItemService;
import com.example.waiterapp.exceptions.ObjectNotFoundException;

@DisplayName("ItemController's tests")
class ItemControllerTest {
  static ItemController itemController;

  @Mock
  Item item1 = mock(Item.class);
  Item item2 = mock(Item.class);
  static ItemService itemService = mock(ItemService.class);

  @BeforeAll
  static void inicializaItemController() {
    itemController = new ItemController(itemService);
  }

  @Nested
  @DisplayName("ItemController#listaItens")
  class ListaItensTest {
    @BeforeEach
    void mockItemServiceListaItens() {
      List<Item> items = new ArrayList<Item>(List.of(item1, item2));
      when(itemService.listaItens()).thenReturn(items);
    }

    @Test
    @DisplayName("should return 200")
    void statusCode200() {
      assertEquals(200, itemController.listaItens().getStatusCode().value());
    }

    @Test
    @DisplayName("should return a list of items")
    void returnListOfItens() {
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
    void statusCode200_WhenItensExists() {
      when(itemService.retornaItemById(1L)).thenReturn(item1);
      assertEquals(200, itemController.retornaItemById(1L).getStatusCode().value());
    }

    @Test
    @DisplayName("should return 404 when item doesn't exists")
    void statusCode404_WhenItensDoesntExists() {
      when(itemService.retornaItemById(1L)).thenThrow(ObjectNotFoundException.class);
      assertEquals(404, itemController.retornaItemById(1L).getStatusCode().value());
    }

    @Test
    @DisplayName("should return the item when item exists")
    void returnItem_WhenItensExists() {
      when(itemService.retornaItemById(any(long.class))).thenReturn(item1);
      assertEquals(itemController.retornaItemById(1L).getBody(), item1);
    }
  }

  @Nested
  @DisplayName("ItemController#insereItem")
  class InsereItemTest {
    ItemDTO itemDTO;

    @BeforeEach
    void mockItemServiceInsereItem() {
      itemDTO = mock(ItemDTO.class);
      when(itemService.transformarDTO(any(ItemDTO.class))).thenReturn(item1);
      when(itemService.insereItem(any(Item.class))).thenReturn(item1);
    }

    @Test
    @DisplayName("should return 201")
    void statusCode201() {
      assertEquals(201, itemController.insereItem(itemDTO).getStatusCode().value());
    }

    @Test
    @DisplayName("should return the item")
    void returnItem() {
      assertEquals(item1, itemController.insereItem(itemDTO).getBody());
    }
  }

  @Nested
  @DisplayName("ItemController#atualizaItem")
  class AtualizaItemTest {
    ItemDTO itemDTO = mock(ItemDTO.class);

    @BeforeEach
    void mockItemServiceAtualizaItem() {
      when(itemService.transformarDTO(any(ItemDTO.class))).thenReturn(item1);
    }

    @Test
    @DisplayName("should return 200 when item exists")
    void statusCode200_WhenItensExists() {
      when(itemService.atualizaItem(any(Item.class))).thenReturn(item1);
      assertEquals(200, itemController.atualizaItem(itemDTO, 1L).getStatusCode().value());
    }

    @Test
    @DisplayName("should return the item")
    void returnItem_WhenItensExists() {
      when(itemService.atualizaItem(any(Item.class))).thenReturn(item1);
      assertEquals(itemController.atualizaItem(itemDTO, 1L).getBody(), item1);
    }

    @Test
    @DisplayName("should return 404")
    void statusCode404_WhenItensDoesntExists() {
      when(itemService.atualizaItem(any(Item.class))).thenThrow(ObjectNotFoundException.class);
      assertEquals(404, itemController.atualizaItem(itemDTO, 1L).getStatusCode().value());
    }
  }

  @Nested
  @DisplayName("ItemController#deletaItem")
  class DeleteItemTest {
    @Test
    @DisplayName("should return 204 when item exists")
    void statusCode204_WhenItemExists() {
      doNothing().when(itemService).apagaItem(any(long.class));
      assertEquals(204, itemController.deleteItem(1L).getStatusCode().value());
    }

    @Test
    @DisplayName("should return 404 when item doesn't exists")
    void statusCode204_WhenItemDoesntExists() {
      doThrow(ObjectNotFoundException.class).when(itemService).apagaItem(any(long.class));
      assertEquals(404, itemController.deleteItem(1L).getStatusCode().value());
    }
  }
}
