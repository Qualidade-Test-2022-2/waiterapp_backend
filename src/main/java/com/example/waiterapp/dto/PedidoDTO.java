package com.example.waiterapp.dto;

import com.example.waiterapp.models.Cliente;
import com.example.waiterapp.models.Garcom;
import com.example.waiterapp.models.ItemPedido;
import com.example.waiterapp.models.Pagamento;
import com.example.waiterapp.enums.Estado;
import com.example.waiterapp.models.Pedido;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class PedidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDateTime dataCriacao;

    private Estado estado;
    private Double precoTotal;
    private Integer notaAtendimento;
    private Integer notaPedido;
    private String opcoesExtras;

    private Set<ItemPedido> items = new HashSet<>();

    private Cliente cliente;

    private Garcom garcom;

    private Pagamento pagamento;

    public PedidoDTO() {
    }

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.dataCriacao = pedido.getDataCriacao();
        this.estado = pedido.getEstado();
        this.precoTotal = pedido.getPrecoTotal();
        this.notaAtendimento = pedido.getNotaAtendimento();
        this.notaPedido = pedido.getNotaPedido();
        this.opcoesExtras = pedido.getOpcoesExtras();
    }

    public Set<ItemPedido> getItems() {
        return items;
    }

    public void setItems(Set<ItemPedido> items) {
        this.items = items;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Garcom getGarcom() {
        return garcom;
    }

    public void setGarcom(Garcom garcom) {
        this.garcom = garcom;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(Double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public void setPrecoTotal() {
        this.precoTotal = this.items.stream().map(ItemPedido::getSubTotal).reduce(0D, Double::sum);
    }

    public Integer getNotaAtendimento() {
        return notaAtendimento;
    }

    public void setNotaAtendimento(Integer notaAtendimento) {
        this.notaAtendimento = notaAtendimento;
    }

    public Integer getNotaPedido() {
        return notaPedido;
    }

    public void setNotaPedido(Integer notaPedido) {
        this.notaPedido = notaPedido;
    }

    public String getOpcoesExtras() {
        return opcoesExtras;
    }

    public void setOpcoesExtras(String opcoesExtras) {
        this.opcoesExtras = opcoesExtras;
    }

    public void fecharPedido(){
        this.estado = Estado.FECHADO;
    }

}

