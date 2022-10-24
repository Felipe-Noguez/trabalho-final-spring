package com.dbc.vemser.pokestore.repository;

import com.dbc.vemser.pokestore.config.ConexaoBancoDeDados;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.entity.Usuario;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository implements Repositorio<Integer, Usuario> {

    private ConexaoBancoDeDados conexaoBancoDeDados;

    public UsuarioRepository(ConexaoBancoDeDados conexaoBancoDeDados){
        this.conexaoBancoDeDados = conexaoBancoDeDados;
    }

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_USUARIO.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    @Override
    public Usuario adicionar(Usuario usuario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            usuario.setIdUsuario(proximoId);

            String sql = "INSERT INTO USUARIO\n" +
                    "(ID_USUARIO,PIX,EMAIL,SENHA ,NOME,ENDERECO,CPF,CIDADE,ESTADO,TELEFONE, DELETADO)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, usuario.getIdUsuario());
            stmt.setString(2, usuario.getPix());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());
            stmt.setString(5, usuario.getNome());
            stmt.setString(6, usuario.getEndereco());
            stmt.setString(7, usuario.getCpf());
            stmt.setString(8, usuario.getCidade());
            stmt.setString(9, usuario.getEstado());
            stmt.setString(10, usuario.getTelefone());
            stmt.setString(11, usuario.getDeletado());

            int res = stmt.executeUpdate();
            System.out.println("adicionarUsuario.res=" + res);
            return usuario;
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

            String sql = "UPDATE USUARIO \n" +
                    "SET DELETADO = 'T'\n" +
                    "WHERE ID_USUARIO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerUsuarioPorId.res=" + res);

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
    public boolean editar(Integer id, Usuario usuario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE USUARIO SET ");
            sql.append(" pix = ?,");
            sql.append(" email = ?,");
            sql.append(" senha = ?,");
            sql.append(" nome = ?,");
            sql.append(" endereco = ?,");
            sql.append(" cpf = ?,");
            sql.append(" cidade = ?,");
            sql.append(" estado = ?,");
            sql.append(" telefone = ?,");
            sql.append(" deletado = ? ");
            sql.append(" WHERE id_usuario = ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, usuario.getPix());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getNome());
            stmt.setString(5, usuario.getEndereco());
            stmt.setString(6, usuario.getCpf());
            stmt.setString(7, usuario.getCidade());
            stmt.setString(8, usuario.getEstado());
            stmt.setString(9, usuario.getTelefone());
            stmt.setString(10, usuario.getDeletado());
            stmt.setInt(11, usuario.getIdUsuario());

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("editarUsuario.res=" + res);

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
    public List<Usuario> listar() throws BancoDeDadosException {
        List<Usuario> usuarios = new ArrayList<>();

        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM USUARIO" +
                     " WHERE DELETADO = 'F'";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(res.getInt("id_usuario"));
                usuario.setPix(res.getString("pix"));
                usuario.setEmail(res.getString("email"));
                usuario.setSenha(res.getString("senha"));
                usuario.setNome(res.getString("nome"));
                usuario.setEndereco(res.getString("endereco"));
                usuario.setCpf(res.getString("cpf"));
                usuario.setCidade(res.getString("cidade"));
                usuario.setEstado(res.getString("estado"));
                usuario.setTelefone(res.getString("telefone"));
                usuario.setDeletado(res.getString("deletado"));
                usuarios.add(usuario);
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
        return usuarios;
    }

    public boolean findByEmail(Usuario usuario) throws BancoDeDadosException{
        List<Usuario> lista = new ArrayList<>();
        Connection con = null;
        boolean existe = false;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM USUARIO" +
                    " WHERE EMAIL = ?";
            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);
            res.next();
            existe = res.getString("email").isEmpty();
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
        return existe;
    }

    public boolean findBySenha(Usuario usuario) throws BancoDeDadosException{
        List<Usuario> lista = new ArrayList<>();
        Connection con = null;
        boolean existe = false;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM USUARIO" +
                    " WHERE SENHA = ?";
            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);
            res.next();
            existe = res.getString("senha").isEmpty();
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
        return existe;
    }

    public boolean findByPix(Usuario usuario) throws BancoDeDadosException{
        List<Usuario> lista = new ArrayList<>();
        Connection con = null;
        boolean existe = false;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM USUARIO" +
                    " WHERE PIX = ?";
            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);
            res.next();
            existe = res.getString("pix").isEmpty();
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
        return existe;
    }

    public boolean findByCPF(Usuario usuario) throws BancoDeDadosException{
        List<Usuario> lista = new ArrayList<>();
        Connection con = null;
        boolean existe = false;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM USUARIO" +
                    " WHERE CPF = ?";
            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);
            res.next();
            existe = res.getString("cpf").isEmpty();
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
        return existe;
    }

    public Usuario selecionarUsuario(Usuario usuario) throws BancoDeDadosException {
        List<Usuario> lista = new ArrayList<>();
        Connection con = null;
        boolean existe = false;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM USUARIO" +
                    " WHERE EMAIL = ? AND SENHA = ?";

            //Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);
            res.next();
            existe = res.getString("email").isEmpty();
        } catch (SQLException e){
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
        return usuario;
    }

    public Usuario pegarLogin(Usuario usuarioLogin) throws BancoDeDadosException {
        Connection con = null;
        Usuario usuario = new Usuario();

        try {
            con = conexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM USUARIO " +
                    " WHERE EMAIL = ? AND SENHA = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, usuarioLogin.getEmail());
            stmt.setString(2, usuarioLogin.getSenha());

            ResultSet res = stmt.executeQuery();

//            if(res.next()) {
                res.next();
                System.out.println("testando");
                usuario.setIdUsuario(res.getInt("id_usuario"));
                usuario.setPix(res.getString("pix"));
                usuario.setEmail(res.getString("email"));
                usuario.setSenha(res.getString("senha"));
                usuario.setNome(res.getString("nome"));
                usuario.setEndereco(res.getString("endereco"));
                usuario.setCpf(res.getString("cpf"));
                usuario.setCidade(res.getString("cidade"));
                usuario.setEstado(res.getString("estado"));
                usuario.setTelefone(res.getString("telefone"));
                usuario.setDeletado(res.getString("deletado"));
//            }
            System.out.println("Usuário consultado!!");

        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            try{
                if (con != null){
                    con.close();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return usuario;
    }



}