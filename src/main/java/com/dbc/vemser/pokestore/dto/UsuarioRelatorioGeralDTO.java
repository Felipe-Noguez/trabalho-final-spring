package com.dbc.vemser.pokestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioRelatorioGeralDTO {

//    USUÁRIO
    private Integer idUsuario;
    private String nomeUsuario;
    private String email;
    private String cidade;

//    PEDIDO
    private Integer idPedido;
    private Double valorFinal;

//    PRODUTO
    private Integer idProduto;
    private String nomeProduto;
    private String descricaoProduto;
    private Integer quantidade;
    private Double valorProduto;

//    PRODUTO_PEDIDO
    private Integer idProdutoPedido;
    private Integer quantidadeProdutoPedido;

//    CUPOM
    private Integer idCumpom;
    private Double desconto;
}
