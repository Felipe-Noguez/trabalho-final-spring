package com.dbc.vemser.pokestore.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PedidoCreateDTO {

    private Integer idCupom;

//    private Cupom cupom; // ID

    private Integer idUsuario;

    private String deletado;

    private List<ProdutoIdQuantidadeCreateDTO> produtosDTO = new ArrayList<>(); //produtoPedido inserir produto;

//    private List<ProdutoPedidoDTO> produtosPedido = new ArrayList<>();
}

//  primeuiro pedido id dele, inserir produtoPedido + idProdutos;
//  salvar o pedido
//  na tabela de relação (mesmo bloco de codigo), pegar esses ids, inserindo na tabela de relação (gerando uma linha)
//  no outro ponto, dar um get do Pedido, dar um get em cada item;
//  Put (ter que fazer, ex, trocar o pedido 5, pega o ele, exclui ele da tabela de relação (ProdutoPedido), adicionar os itens novos, com oidPedido5;
//  Esse put chamar o delete e depois o post(create);
// ver com o Noah (stream para cada iD pega cada item)
