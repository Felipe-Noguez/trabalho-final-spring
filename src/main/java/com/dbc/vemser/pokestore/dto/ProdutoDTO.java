package com.dbc.vemser.pokestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoDTO extends ProdutoCreateDTO {

    private Integer idProduto;
}
