package com.dbc.vemser.pokestore.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProdutoIdQuantidadeCreateDTO {

    @NotNull
    private int idProduto;

    @NotNull
    private int quantidade;

//    public Integer getId(){
//        return idProduto;
//    }
}
