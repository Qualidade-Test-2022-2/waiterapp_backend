package com.example.waiterapp.ItemPedido;

import com.example.waiterapp.Item.Item;
import com.example.waiterapp.Pedido.Pedido;

public class ItemPedido {
    private ItemPedidoPK id = new ItemPedidoPK();
    private Integer quantidade;
    private Double preco;

    public ItemPedido() {
    }

    public ItemPedido(Pedido pedido, Item item, Integer quantidade, Double preco) {
        id.setItem(item);
        id.setPedido(pedido);
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public Double getPrecoItemPedido(){
        return quantidade * preco;
    }

    public Pedido getPedido(){
        return id.getPedido();
    }

    public Item getItem(){
        return id.getItem();
    }

    public ItemPedidoPK getId() {
        return id;
    }

    public void setId(ItemPedidoPK id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "quantidade=" + quantidade +
                ", preco=" + preco +
                '}';
    }
}
