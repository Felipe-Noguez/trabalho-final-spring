package com.dbc.vemser.pokestore.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
public class AvaliacaoProdutoUltimaSemanaDTO {

    @Field("_id")
    private String idAvaliacao;
    private String nomeProduto;
    private String nomeUsuario;
    private LocalDate dataAvaliacao;

}
