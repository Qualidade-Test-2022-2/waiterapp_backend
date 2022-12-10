package com.example.waiterapp.controllers;

import com.example.waiterapp.models.Cardapio;
import com.example.waiterapp.dto.CardapioDTO;
import com.example.waiterapp.services.CardapioService;
import com.example.waiterapp.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping({"/api/cardapios"})
public class CardapioController {

    private CardapioService cardapioService;

    @Autowired
    public CardapioController(CardapioService cardapioService) {
        this.cardapioService = cardapioService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Cardapio>> listaCardapios() {
        List<Cardapio> cardapios = cardapioService.listaCardapios();
        return ResponseEntity.ok().body(cardapios);
    }

    @GetMapping(value = "/{idCardapio}", produces = "application/json")
    public ResponseEntity<Cardapio> retornaCardapioById(@PathVariable Long idCardapio){
        try {
            Cardapio cardapio = cardapioService.retornaCardapioById(idCardapio);

            return ResponseEntity.ok().body(cardapio);
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> insereCardapio(@Valid @RequestBody CardapioDTO cardapioDTO){
        Cardapio cardapio = cardapioService.transformarDTO(cardapioDTO);
        cardapio = cardapioService.insereCardapio(cardapio);

        System.out.println(cardapio);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cardapio.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{idCardapio}", consumes = "application/json")
    public ResponseEntity<Cardapio> atualizaCardapio(@Valid @RequestBody CardapioDTO cardapioDTO, @PathVariable Long idCardapio){
        Cardapio cardapio = cardapioService.transformarDTO(cardapioDTO);

        cardapio.setId(idCardapio);
        try{
            cardapioService.atualizaCardapio(cardapio);
            return ResponseEntity.ok().body(cardapio);
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{idCardapio}")
    public ResponseEntity<Void> deleteCardapio(@PathVariable Long idCardapio){
        try{
            cardapioService.apagaCardapio(idCardapio);

            return ResponseEntity.noContent().build();

        }catch (DataIntegrityViolationException | ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
