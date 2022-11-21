package com.dbc.vemser.pokestore.entity;

import com.dbc.vemser.pokestore.enums.FormaPagamento;
import com.dbc.vemser.pokestore.enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "pagamento")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagamentoEntity {

    @Id
    private String idPagamento;
    private Double valorTotal;
    private StatusPagamento status;
    private FormaPagamento formaPagamento;
    private Integer pedidoId;
    private LocalDate dataPagamento;
}
