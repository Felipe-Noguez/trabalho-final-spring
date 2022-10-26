package com.dbc.vemser.pokestore.repository;

import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.entity.Cupom;
import com.dbc.vemser.pokestore.config.ConexaoBancoDeDados;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CupomRepository implements Repositorio<Integer, Cupom> {

    private ConexaoBancoDeDados conexaoBancoDeDados;

    public CupomRepository(ConexaoBancoDeDados conexaoBancoDeDados){

        this.conexaoBancoDeDados = conexaoBancoDeDados;
    }

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {

        String sql = "SELECT SEQ_CUPOM.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()){
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public Cupom adicionar(Cupom cupom) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            cupom.setIdCupom(proximoId);

            String sql = "INSERT INTO CUPOM\n" +
                    "(ID_CUPOM,DESCONTO,DELETADO)\n" +
                    "VALUES(?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, cupom.getIdCupom());
            stmt.setDouble(2, cupom.getValor());
            stmt.setString(3, cupom.getDeletado());

            int res = stmt.executeUpdate();
            System.out.println("adicionarCupom.res=" + res);
            return cupom;
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

            String sql = "UPDATE CUPOM \n" +
                    "SET DELETADO = 'T'\n" +
                    "WHERE ID_CUPOM = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerCupomPorId.res=" + res);

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
    public boolean editar(Integer id, Cupom cupom) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CUPOM SET " );
            sql.append(" desconto = ?,");
            sql.append(" deletado = ? ");
            sql.append(" WHERE id_cupom = ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());


            stmt.setDouble(1, cupom.getValor());
            stmt.setString(2, cupom.getDeletado());
            stmt.setInt(3, cupom.getIdCupom());

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("editarCupom.res=" + res);

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
    public List<Cupom> listar() throws BancoDeDadosException {
        List<Cupom> cupons = new ArrayList<>();

        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM CUPOM" +
                    " WHERE DELETADO = 'F'";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Cupom cupom = new Cupom();
                cupom.setIdCupom(res.getInt("id_cupom"));
                cupom.setValor(res.getDouble("desconto"));
                cupom.setDeletado(res.getString("deletado"));
                cupons.add(cupom);
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
        return cupons;
    }

}