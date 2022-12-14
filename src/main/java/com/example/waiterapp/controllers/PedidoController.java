package com.example.waiterapp.controllers;

import com.example.waiterapp.exceptions.ObjectNotFoundException;
import com.example.waiterapp.models.Pedido;
import com.example.waiterapp.dto.PedidoDTO;
import com.example.waiterapp.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = {"/api/pedidos"})
public class PedidoController {

    private PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Pedido>> listaPedidos() {
        List<Pedido> pedidos = pedidoService.listaPedidos();
        return ResponseEntity.ok().body(pedidos);
    }

    @GetMapping(value = "{idPedido}", produces = "application/json")
    public ResponseEntity<Pedido> retornaPedidoById(@PathVariable Long idPedido) {
        Pedido pedido = pedidoService.retornaPedidoById(idPedido);
        return ResponseEntity.ok().body(pedido);
    }

    @GetMapping(value = "clientes/{idCliente}", produces = "application/json")
    public ResponseEntity<List<Pedido>> retornaPedidoByIdCliente(@PathVariable Long idCliente){
        List<Pedido> pedidos = pedidoService.listaPedidosByIdCliente(idCliente);
        return ResponseEntity.ok().body(pedidos);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Pedido> inserePedido(@Valid @RequestBody PedidoDTO pedidoDTO){
        Pedido pedido = pedidoService.transformarDTO(pedidoDTO);
        pedido = pedidoService.inserePedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @PutMapping(value = "/{idPedido}", consumes = "application/json")
    public ResponseEntity<Pedido> atualizaPedido(@Valid @RequestBody PedidoDTO pedidoDTO, @PathVariable Long idPedido){
        Pedido pedido = pedidoService.transformarDTO(pedidoDTO);
        pedidoService.atualizaPedido(pedido);

        return ResponseEntity.ok().body(pedido);
    }

    @DeleteMapping(value = "/{idPedido}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long idPedido){
        try{
            pedidoService.apagaPedido(idPedido);

            return ResponseEntity.noContent().build();

        }catch (DataIntegrityViolationException | ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
