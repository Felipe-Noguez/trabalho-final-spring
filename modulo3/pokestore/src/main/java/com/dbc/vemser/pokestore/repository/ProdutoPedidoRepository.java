package com.dbc.vemser.pokestore.repository;


import com.dbc.vemser.pokestore.config.ConexaoBancoDeDados;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.entity.ProdutoPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoPedidoRepository implements Repositorio<Integer, ProdutoPedido> {


    private ConexaoBancoDeDados conexaoBancoDeDados;

    public ProdutoPedidoRepository(ConexaoBancoDeDados conexaoBancoDeDados){
        this.conexaoBancoDeDados = conexaoBancoDeDados;
    }

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_PRODUTO_PEDIDO.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()){
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public ProdutoPedido adicionar(ProdutoPedido produtoPedido) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            produtoPedido.setIdProdutoPedido(proximoId);

            String sql = "INSERT INTO PRODUTO_PEDIDO\n" +
                    "(ID_PRODUTO_PEDIDO,ID_PRODUTO,ID_PEDIDO,QUANTIDADE, VALOR, DELETADO)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, proximoId);
            stmt.setInt(2, produtoPedido.getProduto().getIdProduto());
            stmt.setInt(3, produtoPedido.getPedido().getIdPedido());
            stmt.setInt(4, produtoPedido.getQuantidade());
            stmt.setDouble(5, produtoPedido.getValor());
            stmt.setString(6, produtoPedido.getDeletado());
            int res = stmt.executeUpdate();
            System.out.println("adicionarPedido.res=" + res);
            return produtoPedido;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE PRODUTO_PEDIDO \n" +
                    " SET DELETADO = 'T'\n" +
                    " WHERE ID_PEDIDO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerProdutoPedidoPorId.res=" + res);
            return res > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean editar(Integer id, ProdutoPedido produtoPedido) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE PRODUTO_PEDIDO SET ");
            sql.append(" ID_PRODUTO_PEDIDO = ?,");
            sql.append(" ID_PRODUTO = ?,");
            sql.append(" ID_PEDIDO = ?,");
            sql.append(" QUANTIDADE = ?, ");
            sql.append(" VALOR = ?, ");
            sql.append(" DELETADO = ?, ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, produtoPedido.getIdProdutoPedido());
            stmt.setInt(2, produtoPedido.getProduto().getIdProduto());
            stmt.setDouble(3, produtoPedido.getPedido().getIdPedido());
            stmt.setInt(4, produtoPedido.getQuantidade());
            stmt.setDouble(5, produtoPedido.getValor());
            stmt.setString(6, produtoPedido.getDeletado());

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("editarPedido.res=" + res);
            return res > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<ProdutoPedido> listar() throws BancoDeDadosException {
        List<ProdutoPedido> produtoPedidos = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM PRODUTO_PEDIDO" +
                    " WHERE DELETADO = 'F'";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                ProdutoPedido pedido = new ProdutoPedido();
                pedido.setIdProdutoPedido(res.getInt("ID_PRODUTO_PEDIDO"));
                pedido.getProduto().setIdProduto(res.getInt("ID_PRODUTO"));
                pedido.getPedido().setIdPedido(res.getInt("ID_PEDIDO"));
                pedido.setQuantidade(res.getInt("QUANTIDADE"));
                pedido.setDeletado(res.getString("DELETADO"));
                produtoPedidos.add(pedido);
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return produtoPedidos;
    }
}
