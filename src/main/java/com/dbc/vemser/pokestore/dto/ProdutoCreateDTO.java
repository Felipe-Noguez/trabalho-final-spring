package com.dbc.vemser.pokestore.dto;

import com.dbc.vemser.pokestore.enums.Tipos;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class ProdutoCreateDTO {

    @NotNull
    @Size(max = 250)
    @Schema(description = "Nome do produto")
    private String nome;

    @NotNull
    @Size(max = 250)
    @Schema(description = "Descrição do produto")
    private String descricao;

    @NotNull
    @Positive
    @Schema(description = "Quantidade de produtos disponíveis")
    @JsonProperty("Quantidade em estoque")
    private Integer quantidade;

    @NotNull
    @Schema(description = "Tipo de produto")
    protected Tipos tipo;

    @NotNull
    @Schema(description = "Valor do produto")
    private Double valor;

    @Schema(description = "Id do usuário que cadadstrou o produto")
    private Integer idUsuario;


}
