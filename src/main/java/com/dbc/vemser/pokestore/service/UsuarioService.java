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

    // constantes
    private final char ATIVO = '1';
    private final String ROLE_CLIENTE = "ROLE_CLIENTE";

    // criação de um objeto
    public UsuarioDTO adicionarUsuario(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);

        CargoEntity cargo = findCargosByNome(ROLE_CLIENTE);
        usuarioEntity.getCargos().add(cargo);

        String senhaCriptografada = passwordEncoder.encode(usuarioCreateDTO.getSenha());
        usuarioEntity.setSenha(senhaCriptografada);
        usuarioEntity.setContaStatus(ATIVO);

        usuarioEntity = usuarioRepository.save(usuarioEntity);

        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
        emailService.sendEmailUsuario(usuarioDTO, Requisicao.CREATE);

        usuarioDTO.getCargos().add(objectMapper.convertValue(cargo, CargoDto.class));
        return usuarioDTO;
    }

    // atualização de um objeto
    public UsuarioDTO editar(UsuarioCreateDTO usuarioAtualizar) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = findById(getIdLoggedUser());

        UsuarioEntity usuarioEntityAtualizar = objectMapper.convertValue(usuarioAtualizar, UsuarioEntity.class);
        usuarioEntityAtualizar.setIdUsuario(usuarioEncontrado.getIdUsuario());
        usuarioEntityAtualizar.setCargos(usuarioEncontrado.getCargos());
        usuarioEntityAtualizar.setContaStatus(ATIVO);

        String senhaCriptografada = passwordEncoder.encode(usuarioAtualizar.getSenha());
        usuarioEntityAtualizar.setSenha(senhaCriptografada);

        usuarioEntityAtualizar = usuarioRepository.save(usuarioEntityAtualizar);
        emailService.sendEmailUsuario(objectMapper.convertValue(usuarioEntityAtualizar, UsuarioDTO.class), Requisicao.UPDATE);

        List<CargoDto> cargos = getCargoDtos(usuarioEntityAtualizar);

        UsuarioDTO usuarioDTO =  objectMapper.convertValue(usuarioEntityAtualizar, UsuarioDTO.class);
        usuarioDTO.setCargos(cargos);
        return usuarioDTO;
    }

    //atualiza cargos
    public UsuarioDTO atualizarCargos(UsuarioCargosDTO usuarioCargosDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(usuarioCargosDTO.getIdUsuario());
        usuarioEntity.setContaStatus(ATIVO);

        Set<CargoEntity> cargos = getCargos(usuarioCargosDTO);

        usuarioEntity.setCargos(cargos);
        usuarioRepository.save(usuarioEntity);

        return getUsuarioDTO(usuarioEntity);
    }

    //enviar email para recuprar senha
    public String enviarEmailParaRecuperarSenha(String email) throws RegraDeNegocioException {
        UsuarioEntity usuario = findByEmail(email);

        String token = tokenService.getTokenRecuperarSenha(usuario);
        emailService.sendEmailRecuperarSenha(usuario, token);

        return "Verifique seu email para trocar a senha.";
    }

    public String atualizarSenha(String senha) throws RegraDeNegocioException {
        UsuarioEntity usuario = findById(getIdLoggedUser());

        String senhaCriptografada = passwordEncoder.encode(senha);
        usuario.setSenha(senhaCriptografada);
        usuarioRepository.save(usuario);

        return "Senha alterada com sucesso!";
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
        return Integer.parseInt(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
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

    public UsuarioDTO desativarUsuario (Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(idUsuario);
        usuarioEntity.setContaStatus('0');
        usuarioRepository.save(usuarioEntity);

        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }

    public UsuarioDTO desativarConta () throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(getIdLoggedUser());
        usuarioEntity.setContaStatus('0');
        usuarioRepository.save(usuarioEntity);

        List<CargoDto> cargos = getCargoDtos(usuarioEntity);

        UsuarioDTO usuarioDTO =  objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
        usuarioDTO.setCargos(cargos);
        return usuarioDTO;
    }

//    ------------------------- METODOS PRIVADOS --------------------------------------

    public CargoEntity findCargosByNome(String nome) throws RegraDeNegocioException {
        return cargoRepository.findByNome(nome)
                .orElseThrow(() -> new RegraDeNegocioException("Cargo não encontrado"));
    }

    @NotNull
    private UsuarioDTO getUsuarioDTO(UsuarioEntity usuarioEntity) {
        UsuarioDTO dto = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
        dto.setCargos(usuarioEntity.getCargos().stream()
                .map(x -> objectMapper.convertValue(x, CargoDto.class))
                .toList());

        return dto;
    }

    @NotNull
    private Set<CargoEntity> getCargos(UsuarioCargosDTO usuarioCargosDTO) {
        return usuarioCargosDTO.getCargos().stream()
                .map(x -> {
                    try {
                        return findCargosByNome(x.getNome());
                    } catch (RegraDeNegocioException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());
    }

    @NotNull
    private List<CargoDto> getCargoDtos(UsuarioEntity usuarioEntityAtualizar) {
        return usuarioEntityAtualizar.getCargos().stream()
                .map(x -> {
                    try {
                        return objectMapper.convertValue(findCargosByNome(x.getNome()), CargoDto.class);
                    } catch (RegraDeNegocioException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }
}

