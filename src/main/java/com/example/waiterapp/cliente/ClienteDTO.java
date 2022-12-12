package com.example.waiterapp.cliente;
import com.example.waiterapp.pedido.Pedido;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank
    @Size(min=3, message="The field must be at least {min} characters")
    @Size(max=100, message="The field must be maximium {max} characters")
    private String nome;
    @NotBlank
    @Size(min=5, message="The field must be at least {min} characters")
    @Size(max=100, message="The field must be maximium {max} characters")
	@Email(message = "email invalid")
    private String email;
    @NotBlank
    @Size(min=11, message="The field must be at least {min} characters")
    @Size(max=11, message="The field must be maximium {max} characters")
    private String cpf;
    @NotBlank
    @Size(min=11, message="The field must be at least {min} characters")
    @Size(max=11, message="The field must be maximium {max} characters")
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
