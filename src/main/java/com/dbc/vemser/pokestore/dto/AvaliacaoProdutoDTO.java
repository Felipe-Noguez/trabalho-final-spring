package com.dbc.vemser.pokestore.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvaliacaoProdutoDTO extends AvaliacaoProdutoCreateDTO {

    private String nomeProduto;
    private String nomeUsuario;
    private LocalDate dataAvaliacao;

}
