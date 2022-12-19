package com.example.waiterapp.services;

import static org.junit.jupiter.api.Assertions.*;
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

import com.example.waiterapp.models.Item;
import com.example.waiterapp.dto.ItemDTO;
import com.example.waiterapp.repositories.ItemRepository;
import com.example.waiterapp.exceptions.ObjectNotFoundException;

@DisplayName("ItemService's tests")
class ItemServiceTest {
  static ItemService itemService;

  @Mock
  Item item1 = mock(Item.class);
  Item item2 = mock(Item.class);
  static ItemRepository itemRepository = mock(ItemRepository.class);

  @BeforeAll
  static void inicializaItemService() {
    itemService = new ItemService(itemRepository);
  }

  @Nested
  @DisplayName("ItemService#listaItens")
  class ListaItensTest {
    @BeforeEach
    void mockItemRepositoryFindAll() {
      ArrayList<Item> items = new ArrayList<Item>(List.of(item1, item2));
      when(itemRepository.findAll()).thenReturn(items);
    }

    @Test
    @DisplayName("should return a list of Itens")
    void returnListOfItens() {
      assertEquals(itemService.listaItens().get(0), item1);
      assertEquals(itemService.listaItens().get(1), item2);
    }
  }

  @Nested
  @DisplayName("ItemService#transformarDTO")
  class TransformarDTOTest {
    @Mock
    ItemDTO itemDTO = mock(ItemDTO.class);

    @Test
    @DisplayName("should transform a ItemDTO into a Item")
    void transformItemDTOIntoItem() {
      Item itemTransformado = itemService.transformarDTO(itemDTO);

      assertAll(
        () -> assertEquals(itemTransformado.getId(), itemDTO.getId()),
        () -> assertEquals(itemTransformado.getNome(), itemDTO.getNome()),
        () -> assertEquals(itemTransformado.getDataCriacao(), itemDTO.getDataCriacao()),
        () -> assertEquals(itemTransformado.getPreco(), itemDTO.getPreco()),
        () -> assertEquals(itemTransformado.getDescricao(), itemDTO.getDescricao())
      );
    }
  }

  @Nested
  @DisplayName("ItemService#returnItemById")
  class RetornaItemByIdTest {
    @Test
    @DisplayName("should return a item by id when it exists")
    void returnItemWhenItemExist() {
      when(itemRepository.findById(anyLong())).thenReturn(java.util.Optional.of(item1));
      assertEquals(itemService.retornaItemById(1L), item1);
    }

    @Test
    @DisplayName("should throw an exception when the item does not exist")
    void throwExceptionWhenItemDoesNotExist() {
      when(itemRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());
      assertThrows(ObjectNotFoundException.class, () -> itemService.retornaItemById(1L));
    }
  }

  @Nested
  @DisplayName("ItemService#insereItem")
  class InsereItemTest {
    @Test
    @DisplayName("should insert a item")
    void insertItem() {
      when(itemRepository.save(item1)).thenReturn(item1);
      assertEquals(itemService.insereItem(item1), item1);
    }
  }

  @Nested
  @DisplayName("ItemService#atualizaItem")
  class AtualizaItemTest {
    @BeforeEach
    void mockItemRepositoryFindById() {
      when(itemRepository.findById(anyLong())).thenReturn(java.util.Optional.of(item1));
      when(itemRepository.save(item1)).thenReturn(item1);
    }

    @Test
    @DisplayName("should update a item")
    void updateItem() {
      assertEquals(itemService.atualizaItem(item1), item1);
    }
  }

  @Nested
  @DisplayName("ItemService#apagaItem")
  class ApagaItemTest {
    @BeforeEach
    void mockItemRepositoryFindById() {
      when(itemRepository.findById(anyLong())).thenReturn(java.util.Optional.of(item1));
    }

    @Test
    @DisplayName("should delete a item")
    void deleteItem() {
      doNothing().when(itemRepository).deleteById(anyLong());
      itemService.apagaItem(1L);
      assertTrue(true);
    }

    @Test
    @DisplayName("should delete a item")
    void throwExceptionWhenCouldNotDeleteItem() {
      doThrow(DataIntegrityViolationException.class).when(itemRepository).deleteById(anyLong());

      assertThrows(DataIntegrityViolationException.class, () -> itemService.apagaItem(1L));
    }
  }
}
