package com.dbc.vemser.pokestore.repository;

import com.dbc.vemser.pokestore.config.ConexaoBancoDeDados;
import com.dbc.vemser.pokestore.entity.Produto;
import com.dbc.vemser.pokestore.enums.Tipos;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutoRepository implements Repositorio<Integer, Produto> {

    private ConexaoBancoDeDados conexaoBancoDeDados;

    public ProdutoRepository(ConexaoBancoDeDados conexaoBancoDeDados){
        this.conexaoBancoDeDados = conexaoBancoDeDados;
    }

    @Override
    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT SEQ_PRODUTO.nextval mysequence from DUAL";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            if (res.next()) {
                return res.getInt("mysequence");
            }

            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }
    }

    @Override
    public Produto adicionar(Produto produto) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            produto.setIdProduto(proximoId);

            String sql = "INSERT INTO PRODUTO\n" +
                    "(ID_PRODUTO,NOME,DESCRICAO,QUANTIDADE,TIPO,VALOR,ID_USUARIO,DELETADO)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?,?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, produto.getIdProduto());
            stmt.setString(2,produto.getNome());
            stmt.setString(3,produto.getDescricao());
            stmt.setInt(4,produto.getQuantidade());
            stmt.setString(5,produto.getTipo().getTipos());
            stmt.setDouble(6,produto.getValor());
            stmt.setInt(7,produto.getIdUsuario());
            stmt.setString(8,produto.getDeletado());

            int res = stmt.executeUpdate();
            System.out.println("adicionarProduto.res=" + res);
            return produto;
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

            String sql = "UPDATE PRODUTO \n" +
                    "SET DELETADO = 'T'\n" +
                    "WHERE ID_PRODUTO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerProdutoPorId.res=" + res);

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
    public boolean editar(Integer id, Produto produto) throws BancoDeDadosException {
        return false;
    }

    public Produto editarProduto(Integer id, Produto produto) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE PRODUTO SET ");
            sql.append(" nome = ?,");
            sql.append(" descricao = ?,");
            sql.append(" quantidade = ?, ");
            sql.append(" tipo = ?, ");
            sql.append(" valor = ?, ");
            sql.append(" id_usuario = ?, ");
            sql.append(" deletado = ? ");
            sql.append(" where id_produto = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());


            stmt.setString(1,produto.getNome());
            stmt.setString(2,produto.getDescricao());
            stmt.setInt(3,produto.getQuantidade());
            stmt.setString(4,produto.getTipo().getTipos());
            stmt.setDouble(5,produto.getValor());
            stmt.setInt(6,produto.getIdUsuario());
            stmt.setString(7,produto.getDeletado());
            stmt.setInt(8, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("editarCupom.res=" + res);

            return produto;
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
    public List<Produto> listar() throws BancoDeDadosException {
        List<Produto> produtos = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM PRODUTO" +
                    " WHERE DELETADO = 'F' ";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(res.getInt("ID_PRODUTO"));
                produto.setNome(res.getString("NOME"));
                produto.setDescricao(res.getString("DESCRICAO"));
                produto.setQuantidade(res.getInt("QUANTIDADE"));
                if(res.getString("TIPO").equalsIgnoreCase("0")){
                    produto.setTipo(Tipos.JOGOS);
                } else if (res.getString("TIPO").equalsIgnoreCase("1")){
                    produto.setTipo(Tipos.CONSOLE);
                } else {
                    produto.setTipo(Tipos.COLECIONAVEL);
                }
                produto.setValor(res.getDouble("VALOR"));
                produto.setIdUsuario(res.getInt("ID_USUARIO"));
                produto.setDeletado(res.getString("DELETADO" ));
                produtos.add(produto);
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
        return produtos;
    }

    public Produto findById(Integer id) throws BancoDeDadosException{
        Connection con = null;
        Produto produto = new Produto();

        try {
            con = conexaoBancoDeDados.getConnection();


            String sql = "SELECT * FROM PRODUTO WHERE ID_PRODUTO = ?";

            // Executa-se a consulta

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet res = stmt.executeQuery();

            System.out.println("10");

            res.next();
            produto.setIdProduto(res.getInt("id_produto"));
            produto.setNome(res.getString("nome"));
            produto.setDescricao(res.getString("descricao"));
            produto.setQuantidade(res.getInt("quantidade"));
            if(res.getString("TIPO").equalsIgnoreCase("0")){
                produto.setTipo(Tipos.JOGOS);
            } else if (res.getString("TIPO").equalsIgnoreCase("1")){
                produto.setTipo(Tipos.CONSOLE);
            } else {
                produto.setTipo(Tipos.COLECIONAVEL);
            }
            produto.setValor(res.getInt("valor"));
            produto.setIdUsuario(res.getInt("id_usuario"));
            produto.setDeletado(res.getString("deletado"));

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
        }   return produto;

    }
}
