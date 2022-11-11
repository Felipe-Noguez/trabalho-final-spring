package com.dbc.vemser.pokestore.repository;

import com.dbc.vemser.pokestore.dto.UsuarioRelatorioGeralDTO;
import com.dbc.vemser.pokestore.dto.UsuarioRelatorioPedidoDTO;
import com.dbc.vemser.pokestore.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Query(" select new com.dbc.vemser.pokestore.dto.UsuarioRelatorioGeralDTO (" +
            " u.idUsuario, " +
            " u.nome, " +
            " u.email, " +
            " u.cidade, " +
            " pe.idPedido, " +
            " pe.valorFinal," +
            " pr.idProduto, " +
            " pr.nome, " +
            " pr.descricao, " +
            " pr.quantidade," +
            " pr.valor," +
            " pp.idProdutoPedido," +
            " pp.quantidade," +
            " c.idCupom," +
            " c.valor " +
            ")" +
            " from USUARIO u " +
            " left join u.pedidos pe " +
            " left join u.produtos pr " +
            " left join pe.produtosPedidos pp" +
            " left join pr.produtosPedidos pp " +
            " left join pe.cupom c" +
            " where (:idUsuario is null or u.idUsuario = : idUsuario) ")
    List<UsuarioRelatorioGeralDTO> relatorioGeralUsuario(Integer idUsuario);

    Optional<UsuarioEntity> findByEmailAndSenha(String email, String senha);

    Optional<UsuarioEntity> findByEmail(String email);

}
