package com.dbc.vemser.pokestore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class PedidoCreateDTO {

    @NotNull
    @Schema(description = "Id do cupom utilizado")
    private Integer idCupom;

    @NotNull
    @Schema(description = "Id do usuário")
    private Integer idUsuario;

    @Schema(description = "Remoção")
    private String deletado;

    @Schema(description = "Lista de produtos")
    private List<ProdutoIdQuantidadeCreateDTO> produtosDTO = new ArrayList<>();

}


