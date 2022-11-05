package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.*;
import com.dbc.vemser.pokestore.entity.UsuarioEntity;
import com.dbc.vemser.pokestore.enums.Requisicao;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public UsuarioDTO adicionarUsuario(UsuarioCreateDTO usuario){

        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuario, UsuarioEntity.class);
        usuarioEntity = usuarioRepository.save(usuarioEntity);

        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
        emailService.sendEmailUsuario(usuarioDTO, Requisicao.CREATE);
        return usuarioDTO;
    }

    // remoção
    public void remover(Integer id) throws RegraDeNegocioException {

        UsuarioEntity usuarioEncontrado = findById(id);
        emailService.sendEmailUsuario(objectMapper.convertValue(usuarioEncontrado, UsuarioDTO.class), Requisicao.DELETE);
        usuarioRepository.delete(usuarioEncontrado);
    }

    // atualização de um objeto
    public UsuarioDTO editar(Integer id, UsuarioCreateDTO usuarioAtualizar) throws RegraDeNegocioException {

        UsuarioEntity usuarioEncontrado = findById(id);

        usuarioEncontrado = objectMapper.convertValue(usuarioAtualizar, UsuarioEntity.class);
        usuarioEncontrado.setIdUsuario(id);

        usuarioEncontrado = usuarioRepository.save(usuarioEncontrado);
        return objectMapper.convertValue(usuarioEncontrado, UsuarioDTO.class);
    }

    // leitura
    public List<UsuarioDTO> listar(){
        return usuarioRepository.findAll().stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .toList();
    }

    public UsuarioEntity findById(Integer id) throws RegraDeNegocioException {

        return usuarioRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!"));
    }

    public PageDTO<UsuarioDTO> listarUsuariosPaginados(Integer pagina, Integer numeroPaginas){
        PageRequest pageRequest = PageRequest.of(pagina, numeroPaginas);
        Page<UsuarioEntity> paginaRepository = usuarioRepository.findAll(pageRequest);
        List<UsuarioDTO> usuariosDaPagina = paginaRepository.getContent().stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioDTO.class))
                .toList();
        return new PageDTO<>(paginaRepository.getTotalElements(),
                paginaRepository.getTotalPages(),
                pagina,
                numeroPaginas,
                usuariosDaPagina
        );
    }

    public List<UsuarioRelatorioPedidoDTO> listarRelatorioUsuarioPedido (Integer id) {
        return  usuarioRepository.relatorioUsuarioPedido(id);
    }

    public List<UsuarioRelatorioGeralDTO> listarRelatorioGeralUsuario (Integer id) {
        return  usuarioRepository.relatorioGeralUsuario(id);
    }
}

