package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.*;
import com.dbc.vemser.pokestore.entity.CargoEntity;
import com.dbc.vemser.pokestore.entity.UsuarioEntity;
import com.dbc.vemser.pokestore.enums.Requisicao;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.CargoRepository;
import com.dbc.vemser.pokestore.repository.UsuarioRepository;
import com.dbc.vemser.pokestore.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final CargoRepository cargoRepository;
    private final TokenService tokenService;

    // criação de um objeto
    public UsuarioDTO adicionarUsuario(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);

        CargoEntity cargo = findCargosByNome("ROLE_CLIENTE");
        usuarioEntity.getCargos().add(cargo);

        String senhaCriptografada = passwordEncoder.encode(usuarioCreateDTO.getSenha());
        usuarioEntity.setSenha(senhaCriptografada);

        usuarioEntity = usuarioRepository.save(usuarioEntity);

        //        emailService.sendEmailUsuario(usuarioDTO, Requisicao.CREATE);

        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
        usuarioDTO.getCargos().add(objectMapper.convertValue(cargo, CargoDto.class));
        return usuarioDTO;
    }

    // atualização de um objeto
    public UsuarioDTO editar(Integer id, UsuarioCreateDTO usuarioAtualizar) throws RegraDeNegocioException {

        UsuarioEntity usuarioEncontrado = findById(id);

        usuarioEncontrado = objectMapper.convertValue(usuarioAtualizar, UsuarioEntity.class);
        usuarioEncontrado.setIdUsuario(id);

        usuarioEncontrado = usuarioRepository.save(usuarioEncontrado);
        emailService.sendEmailUsuario(objectMapper.convertValue(usuarioEncontrado, UsuarioDTO.class), Requisicao.UPDATE);

        return objectMapper.convertValue(usuarioEncontrado, UsuarioDTO.class);
    }

    //atualiza cargos
    public UsuarioDTO atualizarCargos(UsuarioCargosDTO usuarioCargosDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(usuarioCargosDTO.getIdUsuario());

        Set<CargoEntity> cargos = usuarioCargosDTO.getCargos().stream()
                .map(x -> {
                    try {
                        return findCargosByNome(x.getNome());
                    } catch (RegraDeNegocioException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());

        usuarioEntity.setCargos(cargos);
        usuarioRepository.save(usuarioEntity);

        return getUsuarioDTO(usuarioEntity);
    }

    //enviar email para recuprar senha
    public String enviaEmailParaRecuperarSenha(String email) throws RegraDeNegocioException {
        UsuarioEntity usuario = findByEmail(email);

        String token = tokenService.getToken(usuario);
        emailService.sendEmailRecuperarSenha(usuario, token);

        return "Verifique seu email para trocar a senha.";
    }

    public String atualizaSenha(String senha) throws RegraDeNegocioException {
        UsuarioEntity usuario = findById(getIdLoggedUser());

        String senhaCriptografada = passwordEncoder.encode(senha);
        usuario.setSenha(senhaCriptografada);
        usuarioRepository.save(usuario);

        return "Senha alterada com sucesso";
    }


    // remoção
    public void remover(Integer id) throws RegraDeNegocioException {

        UsuarioEntity usuarioEncontrado = findById(id);
        emailService.sendEmailUsuario(objectMapper.convertValue(usuarioEncontrado, UsuarioDTO.class), Requisicao.DELETE);
        usuarioRepository.delete(usuarioEncontrado);
    }

    // leitura
    public PageDTO<UsuarioDTO> listar(Integer pagina, Integer tamanho){
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<UsuarioEntity> paginaRepository = usuarioRepository.findAll(pageRequest);

        List<UsuarioDTO> usuariosDaPagina = paginaRepository.getContent().stream()
                .map(this::getUsuarioDTO)
                .toList();

        return new PageDTO<>(paginaRepository.getTotalElements(), paginaRepository.getTotalPages(), pagina, tamanho, usuariosDaPagina);
    }

    @NotNull
    private UsuarioDTO getUsuarioDTO(UsuarioEntity usuarioEntity) {
        UsuarioDTO dto = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
        dto.setCargos(usuarioEntity.getCargos().stream()
                .map(x -> objectMapper.convertValue(x, CargoDto.class))
                .toList());

        return dto;
    }

    public List<UsuarioRelatorioPedidoDTO> listarRelatorioUsuarioPedido (Integer id) {
        return  usuarioRepository.relatorioUsuarioPedido(id);
    }

    public List<UsuarioRelatorioGeralDTO> listarRelatorioGeralUsuario (Integer id) {
        return  usuarioRepository.relatorioGeralUsuario(id);
    }

    public UsuarioEntity findById(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new RegraDeNegocioException("Usuario não encontrado!"));
    }

    public Integer getIdLoggedUser() {
        return Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public UsuarioDTO getLoggedUser() throws RegraDeNegocioException {
        return getUsuarioDTO(findById(getIdLoggedUser()));
    }

    public Optional<UsuarioEntity> findByEmailAndSenha(String login, String senha) {
        return usuarioRepository.findByEmailAndSenha(login, senha);
    }

    public UsuarioEntity findByEmail(String email) throws RegraDeNegocioException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado"));
    }

    private CargoEntity findCargosByNome(String nome) throws RegraDeNegocioException {
        return cargoRepository.findByNome(nome)
                .orElseThrow(() -> new RegraDeNegocioException("Cargo não encontrado"));
    }

    public UsuarioDTO desativarUsuario (Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(idUsuario);
        usuarioEntity.setContaStatus('0');
        usuarioRepository.save(usuarioEntity);

        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }

}

