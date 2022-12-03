package com.example.waiterapp.pagamento.pagamentocomcartao;

import com.example.waiterapp.pagamento.Pagamento;
import com.example.waiterapp.enums.Estado;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class PagamentoComCartao extends Pagamento {

    public PagamentoComCartao() {
    }

    public PagamentoComCartao(Long id, Estado estadoPagamento, LocalDateTime dataPagamento) {
        super(id, estadoPagamento, dataPagamento);
    }

}
