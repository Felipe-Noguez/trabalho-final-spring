package com.dbc.vemser.pokestore.dto;

import lombok.Data;

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
    private Integer idUsuarioProduto;

//    PRODUTO_PEDIDO
    private Integer idProdutoPedido;
    private Integer quantidadeProdutoPedido;

//    CUPOM
    private Integer idCumpom;
    private Double desconto;
}
