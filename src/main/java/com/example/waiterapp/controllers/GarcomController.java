package com.example.waiterapp.controllers;

import com.example.waiterapp.exceptions.ObjectNotFoundException;
import com.example.waiterapp.models.Garcom;
import com.example.waiterapp.config.RequireAuthentication;
import com.example.waiterapp.dto.GarcomDTO;
import com.example.waiterapp.services.GarcomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Base64;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping({"/api/garcons"})
public class GarcomController {

    private GarcomService garcomService;

    @Autowired
    public GarcomController(GarcomService garcomService) {
        this.garcomService = garcomService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Garcom>> listaGarcons() {
        List<Garcom> garcons = garcomService.listaGarcons();
        return ResponseEntity.ok().body(garcons);
    }

    @GetMapping(value = "/{idGarcom}", produces = "application/json")
    public ResponseEntity<Garcom> retornaGarcomById(@PathVariable Long idGarcom){
        try {
            Garcom garcom = garcomService.retornaGarcomById(idGarcom);

            return ResponseEntity.ok().body(garcom);
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    // @RequireAuthentication
    @PostMapping(value = "/auth")
    public ResponseEntity<Garcom> authenticate(@RequestHeader("Authorization") String basicAuth) {
        byte[] decodedBytes = Base64.getDecoder().decode(basicAuth);
        String decodedString = new String(decodedBytes);

        String cpf = decodedString.split(":")[0];
        String password = decodedString.split(":")[1];

        Garcom garcom = garcomService.retornaGarcomByCpf(cpf);

        if(garcomService.isWaiterAuthorized(garcom, password, cpf)) {
            return ResponseEntity.ok().body(garcom);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> insereGarcom(@Valid @RequestBody GarcomDTO garcomDTO){
        Garcom garcom = garcomService.transformarDTO(garcomDTO);
        garcom = garcomService.insereGarcom(garcom);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(garcom.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{idGarcom}", consumes = "application/json")
    public ResponseEntity<Void> atualizaGarcom(@Valid @RequestBody GarcomDTO garcomDTO, @PathVariable Long idGarcom){
        Garcom garcom = garcomService.transformarDTO(garcomDTO);

        garcom.setId(idGarcom);
        try{
            garcomService.atualizaGarcom(garcom);
            return ResponseEntity.ok().build();
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{idGarcom}")
    public ResponseEntity<Void> deleteGarcom(@PathVariable Long idGarcom){
        try{
            garcomService.apagaGarcom(idGarcom);
            return ResponseEntity.noContent().build();
        }catch (DataIntegrityViolationException | ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
