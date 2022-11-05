package com.dbc.vemser.pokestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioRelatorioPedidoDTO {

    private Integer idUsuario;
    private String nome;
    private String email;
    private String cidade;
    private Integer idPedido;
    private Double valorFinal;

}
