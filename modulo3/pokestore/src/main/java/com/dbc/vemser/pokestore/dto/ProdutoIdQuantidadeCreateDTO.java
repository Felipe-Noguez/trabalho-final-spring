package com.dbc.vemser.pokestore.dto;

import com.dbc.vemser.pokestore.enums.Tipos;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class ProdutoIdQuantidadeCreateDTO {

    @NotNull
    private int idProduto;

    @NotNull
    private int quantidade;



}
