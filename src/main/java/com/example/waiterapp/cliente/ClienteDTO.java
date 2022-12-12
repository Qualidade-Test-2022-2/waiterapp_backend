package com.example.waiterapp.cliente;
import com.example.waiterapp.pedido.Pedido;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotEmpty
    @Size(min=3, message="The field must be at least {min} characters")
    @Size(max=100, message="The field must be maximium {max} characters")
    private String nome;
    @NotEmpty
    @Size(min=5, message="The field must be at least {min} characters")
    @Size(max=100, message="The field must be maximium {max} characters")
	@Email(message = "email invalid")
    private String email;
    @Size(min=11, message="The field must be at least {min} characters")
    @Size(max=11, message="The field must be maximium {max} characters")
    private String cpf;
    private LocalDateTime dataCriacao;
    private List<Pedido> pedidos = new ArrayList<>();

    public ClienteDTO(){
    }

    public ClienteDTO(Cliente cliente){
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.cpf = cliente.getCpf();
        this.dataCriacao = cliente.getDataCriacao();
        this.pedidos = cliente.getPedidos();
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setdataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

}
