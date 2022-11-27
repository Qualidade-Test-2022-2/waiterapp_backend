package com.example.waiterapp.pedido;

import com.example.waiterapp.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

@CrossOrigin
@RestController
@RequestMapping(value = {"/api/pedidos"})
public class PedidoController {
    
    private PedidoService pedidoService;
    private Logger logger = Logger.getLogger(PedidoController.class.getName());

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
    public ResponseEntity<Pedido> inserePedido(@Valid @RequestBody Pedido pedido){
        logger.log(Level.FINE,"Pedido enviado pelo request-{}", pedido);
        pedido = pedidoService.inserePedido(pedido);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pedido.getId())
                .toUri();

        return ResponseEntity.created(uri).body(pedido);
    }

    @PutMapping(value = "/{idPedido}", consumes = "application/json")
    public ResponseEntity<Void> atualizaPedido(@Valid @RequestBody Pedido pedido, @PathVariable Long idPedido){
        pedido.setId(idPedido);
        pedidoService.atualizaPedido(pedido);

        return ResponseEntity.ok().build();
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
