package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.UsuarioCreateDTO;
import com.dbc.vemser.pokestore.dto.UsuarioDTO;
import com.dbc.vemser.pokestore.entity.Usuario;
import com.dbc.vemser.pokestore.enums.Requisicao;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    private final EmailService emailService;

    private final ObjectMapper objectMapper;

    // criação de um objeto
    public UsuarioDTO adicionarUsuario(UsuarioCreateDTO usuario) throws BancoDeDadosException, RegraDeNegocioException {

        Usuario usuarioEntity = objectMapper.convertValue(usuario, Usuario.class);

        Usuario usuarioSalvar = usuarioRepository.adicionar(usuarioEntity);

        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioSalvar, UsuarioDTO.class);

        emailService.sendEmailUsuario(usuarioDTO, Requisicao.CREATE);

        System.out.println("Usuario adicionado com sucesso! " + usuarioEntity);

        return usuarioDTO;
    }

    // remoção
    public void remover(Integer id) throws BancoDeDadosException, RegraDeNegocioException {
            UsuarioDTO usuarioDeletadoDTO = findById(id);
            emailService.sendEmailUsuario(usuarioDeletadoDTO, Requisicao.DELETE);
            boolean conseguiuRemover = usuarioRepository.remover(id);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + id);
    }

    // atualização de um objeto
    public UsuarioDTO editar(Integer id, UsuarioCreateDTO usuario) throws RegraDeNegocioException, BancoDeDadosException{
        Usuario usuarioAdicionado = objectMapper.convertValue(usuario, Usuario.class);
        usuarioAdicionado = usuarioRepository.adicionar(usuarioAdicionado);

        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioAdicionado, UsuarioDTO.class);

        log.info("UsuarioEditado!");

        return usuarioDTO;

    }

    // leitura
    public List<UsuarioDTO> listar() throws RegraDeNegocioException, BancoDeDadosException {
        return usuarioRepository.listar().stream()
                    .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                    .toList();
    }

        public Usuario verificarUsuario (Usuario usuario) {
        try {
            return usuarioRepository.pegarLogin(usuario);
        } catch (BancoDeDadosException e) {
            System.out.println("ERRO: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
        }

    public Usuario findById(Integer id) throws RegraDeNegocioException {
        try {
            return usuarioRepository.findById(id);
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException("Usuário não encontrado!");
        }
    }
}
//    public static Usuario fazerLogin(UsuarioService usuarioService, Scanner entrada) {
//
//        Usuario usuario = new Usuario();
//        Usuario resultadoUser = null;
//
//        try {
//            while (true) {
//                System.out.println("Digite o email ");
//                usuario.setEmail(entrada.nextLine());
//                System.out.println("Digite a senha:");
//                usuario.setSenha(entrada.nextLine());
//                Usuario usuarioEncontrado = usuarioService.verificarUsuario(usuario);
//                if (usuarioEncontrado.getEmail().equals(usuario.getEmail()) && usuarioEncontrado.getSenha().equals(usuario.getSenha())) {
//                    System.out.println("\n" + usuario.getEmail() + " Logado com sucesso!");
//                    resultadoUser = usuarioEncontrado;
//                    break;
//                }
//            }
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
//        return resultadoUser;
//    }

