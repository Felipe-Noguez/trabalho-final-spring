package com.dbc.vemser.pokestore.repository;

import com.dbc.vemser.pokestore.dto.UsuarioRelatorioPedidoDTO;
import com.dbc.vemser.pokestore.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    @Query(" select new com.dbc.vemser.pokestore.dto.UsuarioRelatorioPedidoDTO (" +
            " u.idUsuario, " +
            " u.nome, " +
            " u.email, " +
            " u.cidade, " +
            " p.idPedido, " +
            " p.valorFinal " +
            ")" +
            " from USUARIO u " +
            " left join u.pedidos p " +
            " where (:idUsuario is null or u.idUsuario = : idUsuario) ")
    List<UsuarioRelatorioPedidoDTO> relatorioUsuarioPedido(Integer idUsuario);


//    @Query(" select new com.dbc.vemser.pokestore.dto.UsuarioRelatorioGeralDTO (" +
//            " u.idUsuario, " +
//            " u.nomeUsuario, " +
//            " u.email, " +
//            " u.cidade, " +
//            " pe.idPedido, " +
//            " pe.valorFinal," +
//            " pr.idProduto, " +
//            " pr.nomeProduto, " +
//            " pr.descricaoProduto, " +
//            " pr.quantidade," +
//            " pr.valorProduto," +
//            " pr.idUsuarioProduto," +
//            " prp.idProdutoPedido," +
//            " prp.quantidadeProdutoPedido," +
//            " c.idCupom," +
//            " c.desconto " +
//            ")" +
//            " from USUARIO u " +
//            " left join u.pedidos pe " +
//            " left join u.produtos prp " +
//            " left join u.pedidos pe " +
//            " left join u.produtosPedidos pr" +
//            " where (:idUsuario is null or u.idUsuario = : idPessoa) ")
//    List<UsuarioRelatorioPedidoDTO> relatorioGeralUsuario(Integer idUsuario);

}
//    //    USUÁRIO
//    private Integer idUsuario;
//    private String nomeUsuario;
//    private String email;
//    private String cidade;
//
//    //    PEDIDO
//    private Integer idPedido;
//    private Double valorFinal;
//
//    //    PRODUTO
//    private Integer idProduto;
//    private String nomeProduto;
//    private String descricaoProduto;
//    private Integer quantidade;
//    private Double valorProduto;
//    private Integer idUsuarioProduto;
//
//    //    PRODUTO_PEDIDO
//    private Integer idProdutoPedido;
//    private Integer quantidade;
//
//    //    CUPOM
//    private Integer idCumpom;
//    private Double desconto;