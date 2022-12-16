package com.example.waiterapp.models;

import com.example.waiterapp.enums.Estado;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Pedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    private Estado estado;
    private Double precoTotal;
    private Integer notaAtendimento;
    private Integer notaPedido;
    private String opcoesExtras;

    @OneToMany(mappedBy = "id.pedido", cascade = CascadeType.REMOVE)
    private Set<ItemPedido> items = new HashSet<>();

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Garcom garcom;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pagamento_id", referencedColumnName = "id")
    private Pagamento pagamento;

    public Pedido() {
    }

    public Pedido(Long id, LocalDateTime dataCriacao, Estado estado, Double precoTotal, Integer notaAtendimento, Integer notaPedido, String opcoesExtras) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.estado = estado;
        this.precoTotal = precoTotal;
        this.notaAtendimento = notaAtendimento;
        this.notaPedido = notaPedido;
        this.opcoesExtras = opcoesExtras;
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

    public void adicionarItemExtra(Item item){
        new ItemPedido(this, item, 1);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", dataCriacao=" + dataCriacao +
                ", estado=" + estado +
                ", precoTotal=" + precoTotal +
                ", notaAtendimento=" + notaAtendimento +
                ", notaPedido=" + notaPedido +
                ", opcoesExtras='" + opcoesExtras + '\'' +
                '}';
    }
}
