package com.dbc.vemser.pokestore.repository;

import com.dbc.vemser.pokestore.config.ConexaoBancoDeDados;
import com.dbc.vemser.pokestore.entity.Cupom;
import com.dbc.vemser.pokestore.entity.Pedido;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PedidoRepository implements Repositorio<Integer, Pedido> {

    private ConexaoBancoDeDados conexaoBancoDeDados;

    public PedidoRepository(ConexaoBancoDeDados conexaoBancoDeDados){

        this.conexaoBancoDeDados = conexaoBancoDeDados;
    }
    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_PEDIDO.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public Pedido adicionar(Pedido pedido) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            pedido.setIdPedido(proximoId);

            String sql = "INSERT INTO PEDIDO\n" +
                    "(ID_PEDIDO,ID_CUPOM,ID_USUARIO,VALOR_FINAL, DELETADO)\n" +
                    "VALUES(?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, pedido.getIdPedido());
            if (pedido.getCupom() != null) {
                stmt.setInt(2, pedido.getCupom().getIdCupom());
            } else {
                stmt.setNull(2, Types.NULL);
            }
            stmt.setInt(3, pedido.getIdUsuario());
            stmt.setDouble(4, pedido.getValorFinal());
            stmt.setString(5, pedido.getDeletado());
            int res = stmt.executeUpdate();
            System.out.println("adicionarPedido.res=" + res);
            return pedido;
        } catch (SQLException e) {
            e.printStackTrace();
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

            String sql = "UPDATE PEDIDO \n" +
                    " SET DELETADO = 'T'\n" +
                    " WHERE ID_PEDIDO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerPedidoPorId.res=" + res);
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

    public boolean apagarPedido(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE PEDIDO WHERE ID_PEDIDO = ? ";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerPedidoPorId.res=" + res);
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
    public boolean editar(Integer id, Pedido pedido) throws BancoDeDadosException {
        return false;
    }

    public Pedido editarPedido(Integer id, Pedido pedido) throws BancoDeDadosException {
        Connection con = null;

        try {

            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE PEDIDO SET ");
            sql.append(" ID_CUPOM = ?,");
            sql.append(" ID_USUARIO = ?,");
            sql.append(" DELETADO = ?, ");
            sql.append(" VALOR_FINAL = ? ");
            sql.append(" WHERE id_pedido = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, pedido.getCupom().getIdCupom());
//            if (pedido.getCupom() != null) {
//                stmt.setInt(1, pedido.getCupom().getIdCupom());
//            } else {
//                stmt.setNull(1, Types.NULL);
//            }
            stmt.setInt(2, pedido.getIdUsuario());
            stmt.setString(3, pedido.getDeletado());
            stmt.setDouble(4, pedido.getValorFinal());
            stmt.setInt(5, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("editarPedido.res=" + res);
            return pedido;
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

    public boolean editarValorFinal(Integer id, double valorFinal) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE PEDIDO SET ");
            sql.append(" valor_final = ?");
            sql.append(" WHERE id_pedido = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setDouble(1, valorFinal);
            stmt.setInt(2, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("editarValorFinal.res=" + res);
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
    public List<Pedido> listar() throws BancoDeDadosException {
        List<Pedido> pedidos = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM PEDIDO" +
                    " WHERE DELETADO = 'F'";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Pedido pedido = new Pedido();
                pedido.setCupom(new Cupom());
                pedido.setIdPedido(res.getInt("ID_PEDIDO"));
                pedido.getCupom().setIdCupom(res.getInt("ID_CUPOM"));
                pedido.setIdUsuario(res.getInt("ID_USUARIO"));
                pedido.setValorFinal(res.getDouble("VALOR_FINAL"));
                pedidos.add(pedido);
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
        return pedidos;
    }

    public Pedido atualizarCupom(Integer id, Pedido pedido) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE PEDIDO SET ");
            sql.append(" ID_CUPOM = ?,");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, pedido.getCupom().getIdCupom());

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("editarCupom.res=" + res);
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
        return pedido;
    }

    public Pedido findById(Integer id) throws BancoDeDadosException{

        Connection con = null;
        Pedido pedido = new Pedido();

        try {

            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM PEDIDO" +
                    " WHERE ID_PEDIDO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta

            ResultSet res = stmt.executeQuery();

            res.next();
            pedido.setIdPedido(res.getInt("id_pedido"));
            pedido.setIdCupom(res.getInt("id_cupom"));
            pedido.setIdUsuario(res.getInt("usuario"));
            pedido.setValorFinal(res.getDouble("valor_final"));
            pedido.setDeletado(res.getString("deletado"));

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
        } return pedido;
    }
}