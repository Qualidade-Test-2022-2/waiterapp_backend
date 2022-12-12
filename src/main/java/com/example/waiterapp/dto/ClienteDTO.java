package com.example.waiterapp.dto;
import com.example.waiterapp.models.Pedido;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String email;
    private String cpf;

    @NotNull
    @NotEmpty
    @NotBlank
    private String password;
    private LocalDateTime dataCriacao;
    private List<Pedido> pedidos = new ArrayList<>();

    public List<Pedido> getPedidos() { return pedidos; }

    public Long getId() { return id; }

    public String getNome() { return nome; }

    public String getEmail() { return email; }

    public String getCpf() { return cpf; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }

    public String getPassword() { return password; }
}
