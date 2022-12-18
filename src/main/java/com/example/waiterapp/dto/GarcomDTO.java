package com.example.waiterapp.dto;

import com.example.waiterapp.interfaces.CpfValidatorContraint;
import com.example.waiterapp.models.Garcom;
import com.example.waiterapp.models.Pedido;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GarcomDTO implements Serializable {

    private Long id;
    @NotBlank
    @Size(min=3, message="Name must be at least {min} characters")
    @Size(max=100, message="Name must be maximium {max} characters")
    private String nome;
    private String email;
    private LocalDateTime dataCriacao;

    private String cpf;
    private String password;
    private List<Pedido> pedidos = new ArrayList<>();

    public GarcomDTO() {
    }

    public GarcomDTO(Garcom garcom) {
        this.id = garcom.getId();
        this.nome = garcom.getNome();
        this.dataCriacao = garcom.getDataCriacao();
        this.cpf = garcom.getCpf();
        this.password = garcom.getPassword();
        this.pedidos = garcom.getPedidos();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
