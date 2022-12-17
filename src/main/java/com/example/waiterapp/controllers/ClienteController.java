package com.example.waiterapp.controllers;

import com.example.waiterapp.models.Cliente;
import com.example.waiterapp.dto.ClienteDTO;
import com.example.waiterapp.services.ClienteService;
import com.example.waiterapp.config.RequireAuthentication;
import com.example.waiterapp.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping({"/api/clientes"})
public class ClienteController {

    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Cliente>> listaClientes() {
        List<Cliente> clientes = clienteService.listaClientes();
        return ResponseEntity.ok().body(clientes);
    }

    @GetMapping(value = "/{idCliente}", produces = "application/json")
    public ResponseEntity<Cliente> retornaClienteById(@PathVariable long idCliente){
        Cliente cliente = clienteService.retornaClienteById(idCliente);

        return ResponseEntity.ok().body(cliente);
    }

    @PostMapping(value = "/cpf",consumes = "application/json", produces = "application/json")
    public ResponseEntity<Cliente> retornaClienteByCpf(@Valid @RequestBody ClienteDTO clienteDTO){
        Cliente cliente = clienteService.retornaClienteByCpf(clienteDTO.getCpf());

        return ResponseEntity.ok().body(cliente);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Cliente> insereCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.retornaClienteByCpf(clienteDTO.getCpf());
        if(cliente != null) {
            return ResponseEntity.ok().body(cliente);
        }

        cliente = clienteService.transformarDTO(clienteDTO);
        cliente = clienteService.insereCliente(cliente);

        return ResponseEntity.created(null).body(cliente);
    }

    @RequireAuthentication
    @PostMapping(value = "/auth")
    public ResponseEntity<Cliente> authenticate(@RequestHeader("Authorization") String basicAuth) {
        byte[] decodedBytes = Base64.getDecoder().decode(basicAuth);
        String decodedString = new String(decodedBytes);

        String cpf = decodedString.split(":")[0];
        String password = decodedString.split(":")[1];

        Cliente cliente = clienteService.retornaClienteByCpf(cpf);
        if(cliente == null) {
            return ResponseEntity.notFound().build();
        }

        if(clienteService.isClientAuthorized(cliente, password, cpf)) {
            return ResponseEntity.ok().body(cliente);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{idCliente}", consumes = "application/json")
    public ResponseEntity<Cliente> atualizaCliente(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Long idCliente){
        Cliente cliente = clienteService.transformarDTO(clienteDTO);

        cliente.setId(idCliente);
        clienteService.atualizaCliente(cliente);

        return ResponseEntity.ok().body(cliente);
    }

    @DeleteMapping(value = "/{idCliente}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long idCliente){
        try{
            clienteService.apagaCliente(idCliente);
            return ResponseEntity.noContent().build();

        }catch (DataIntegrityViolationException | ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
