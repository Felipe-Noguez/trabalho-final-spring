package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.*;
import com.dbc.vemser.pokestore.entity.CargoEntity;
import com.dbc.vemser.pokestore.entity.ProdutoEntity;
import com.dbc.vemser.pokestore.entity.UsuarioEntity;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.CargoRepository;
import com.dbc.vemser.pokestore.repository.UsuarioRepository;
import com.dbc.vemser.pokestore.security.TokenService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CargoRepository cargoRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(usuarioService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarAdicionarUsuarioComSucesso () throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        UsuarioCreateDTO usuarioCreateDTO = getUsuarioCreateDTO();

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setIdUsuario(7);
        when(cargoRepository.findByNome(anyString())).thenReturn(Optional.of(getCargoEntity()));
        String senha = "123";
        when(passwordEncoder.encode(senha)).thenReturn("Ahu82ha");
        when(usuarioRepository.save(any())).thenReturn(usuarioEntity);

        // Ação (ACT)
        UsuarioDTO usuarioDTORetorno = usuarioService.adicionarUsuario(usuarioCreateDTO);

        // Verificação (ASSERT)
        assertNotNull(usuarioDTORetorno);
        assertEquals("Alanis", usuarioDTORetorno.getNome());
    }

    @Test
    public void deveTestarEditarUsuarioComSucesso() throws RegraDeNegocioException {

        // SETUP
        Integer id = 5;
        UsuarioCreateDTO usuarioCreateDTO = getUsuarioCreateDTO();
        UsernamePasswordAuthenticationToken dto = new UsernamePasswordAuthenticationToken(1,null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);

        // findById(id);
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        UsuarioEntity usuario = getUsuarioEntity();
        usuario.setSenha("48jdye12");

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));
        when(passwordEncoder.encode(anyString())).thenReturn("Ahu82ha");
        when(usuarioRepository.save(any())).thenReturn(usuario);
        when(cargoRepository.findByNome(anyString())).thenReturn(Optional.of(getCargoEntity()));

        // ACT
        UsuarioDTO usuarioDTO = usuarioService.editar(usuarioCreateDTO);

        // ASSERT
        assertNotNull(usuarioDTO);
    }

    @Test
    public void deveTestarAtualizarCargos() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer id = 99;
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setIdUsuario(id);
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));
        when(cargoRepository.findByNome(anyString())).thenReturn(Optional.of(getCargoEntity()));

        UsuarioDTO usuarioDTO = getUsuarioDTO();
        usuarioDTO.setIdUsuario(id);

        UsuarioCargosDTO usuarioCargosDTO = new UsuarioCargosDTO();
        usuarioCargosDTO.setIdUsuario(id);
        usuarioCargosDTO.setCargos(usuarioDTO.getCargos());

        // Ação (ACT)
        UsuarioDTO cargosDTO = new UsuarioDTO();
        cargosDTO = usuarioService.atualizarCargos(usuarioCargosDTO);

        // Verificação (ASSERT)
        assertNotNull(cargosDTO);
        assertNotEquals(usuarioEntity.getCargos(), usuarioDTO.getCargos());
    }

    @Test
    public void deveTestarEnviarEmailParaRecuperarSenha() throws RegraDeNegocioException {

        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        String token = "vdbvyd515v1d5151d";

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuarioEntity));
        when(tokenService.getTokenRecuperarSenha(any(UsuarioEntity.class))).thenReturn(token);

        // Ação (ACT)
        String email = usuarioService.enviarEmailParaRecuperarSenha(usuarioEntity.getEmail());

        // Verificação (ASSERT)
        assertNotNull(email);
        verify(emailService, times(1)).sendEmailRecuperarSenha(any(UsuarioEntity.class), anyString());
    }

    @Test
    public void deveTestarAtualizarSenha() throws RegraDeNegocioException {

        // SETUP
        UsernamePasswordAuthenticationToken dto = new UsernamePasswordAuthenticationToken(1,null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);

        // findById(id);
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        String senha = "minhasenha123";

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));
        when(passwordEncoder.encode(anyString())).thenReturn("Ahu82hajij878");
        when(usuarioRepository.save(any())).thenReturn(usuarioEntity);

        // ACT
        usuarioService.atualizarSenha(senha);

        // ASSERT
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    public void deveTestarGetLoggedUser() throws RegraDeNegocioException {
        // SETUP
        UsernamePasswordAuthenticationToken dto = new UsernamePasswordAuthenticationToken(1,null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);
        UsuarioEntity usuarioEntity = getUsuarioEntity();

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));
        // ACT
        UsuarioDTO usuarioDTO = usuarioService.getLoggedUser();

        assertNotNull(usuarioDTO);
        assertEquals(usuarioEntity.getIdUsuario(), usuarioDTO.getIdUsuario());
    }

    @Test
    public void deveListarPaginadoComSucesso() {
        // Criar variaveis (SETUP)
        Integer pagina = 4;
        Integer quantidade = 6;

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        Page<UsuarioEntity> usuarioEntityPage = new PageImpl<>(List.of(usuarioEntity));
        when(usuarioRepository.findAll(any(Pageable.class))).thenReturn(usuarioEntityPage);

        // Ação (ACT)
        PageDTO<UsuarioDTO> usuarioEntityPage1 = usuarioService.listar(pagina, quantidade);

        // Verificação (ASSERT)
        assertNotNull(usuarioEntityPage1);
    }

    @Test
    public void deveExibirRelatorioPedido() {
        // Criar variaveis (SETUP)
        Integer id = 2;

        UsuarioRelatorioPedidoDTO relatorioPedidoDTO = getUsuarioRelatorioPedidoDTO();
        List<UsuarioRelatorioPedidoDTO> usuarioRelatorioPedidoDTOList = new ArrayList<>();
        usuarioRelatorioPedidoDTOList.add(relatorioPedidoDTO);
        when(usuarioRepository.relatorioUsuarioPedido(anyInt())).thenReturn(usuarioRelatorioPedidoDTOList);

        // Ação (ACT)
//        List<UsuarioRelatorioPedidoDTO> usuarioRelatorioPedidoDTO = usuarioRepository.relatorioUsuarioPedido(id);
        List<UsuarioRelatorioPedidoDTO> usuarioRelatorioPedidoDTO = usuarioService.listarRelatorioUsuarioPedido(id);

        // Verificação (ASSERT)
        assertNotNull(usuarioRelatorioPedidoDTO);
        assertTrue(usuarioRelatorioPedidoDTO.size() > 0);
        assertEquals(1, usuarioRelatorioPedidoDTOList.size());

    }

    @Test
    public void deveTestarRelatorioGeral() {
        // Criar variaveis (SETUP)
        Integer id = 7;
        UsuarioRelatorioGeralDTO usuarioRelatorioGeralDTO = getUsuarioRelatorioGeralDTO();
        List<UsuarioRelatorioGeralDTO> usuarioRelatorioGeralDTOList = new ArrayList<>();
        usuarioRelatorioGeralDTOList.add(usuarioRelatorioGeralDTO);
        when(usuarioRepository.relatorioGeralUsuario(anyInt())).thenReturn(usuarioRelatorioGeralDTOList);

        // Ação (ACT)
        List<UsuarioRelatorioGeralDTO> relatorioGeralDTOList = usuarioService.listarRelatorioGeralUsuario(id);

        // Verificação (ASSERT)
        assertNotNull(relatorioGeralDTOList);
        assertTrue(relatorioGeralDTOList.size() > 0);
        assertEquals(1, usuarioRelatorioGeralDTOList.size());
    }

    @Test
    public void deveTestarFindById() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer id = 8;
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setIdUsuario(id);
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        // Ação (ACT)
        UsuarioEntity usuarioEntity1 = usuarioService.findById(id);

        // Verificação (ASSERT)
        assertNotNull(usuarioEntity1);
        assertEquals(8, usuarioEntity.getIdUsuario());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer id = 12;

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Ação (ACT)
        UsuarioEntity usuarioEntity = usuarioService.findById(id);

        // Verificação (ASSERT)
    }

    @Test
    public void deveTestarFindByEmailAndSenha() {
        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        when(usuarioRepository.findByEmailAndSenha(anyString(), anyString())).thenReturn(Optional.of(usuarioEntity));

        // Ação (ACT)
        Optional<UsuarioEntity> usuarioEntityEmailSenha = usuarioService.findByEmailAndSenha(usuarioEntity.getEmail(), usuarioEntity.getSenha());

        // Verificação (ASSERT)
        assertEquals("alanis@mail", usuarioEntityEmailSenha.get().getEmail());
        assertEquals("123", usuarioEntityEmailSenha.get().getSenha());
    }

    @Test
    public void deveTestarFindByEmail() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuarioEntity));

        // Ação (ACT)
        UsuarioEntity usuarioEntityEmail = usuarioService.findByEmail(usuarioEntity.getEmail());

        // Verificação (ASSERT)
        assertEquals("alanis@mail", usuarioEntityEmail.getEmail());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByEmailComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Ação (ACT)
        UsuarioEntity usuarioEntityEmail = usuarioService.findByEmail(usuarioEntity.getEmail());

        // Verificação (ASSERT)
    }

    @Test
    public void deveTestarDesativarUsuario() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer id = 15;
        Character status = '0';
        UsuarioEntity usuarioEntityRetorno = getUsuarioEntity();
        usuarioEntityRetorno.setContaStatus(status);

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntityRetorno));
        when(usuarioRepository.save(any())).thenReturn(usuarioEntityRetorno);

        // Ação (ACT)
        UsuarioDTO usuarioDTODesativar = usuarioService.desativarUsuario(id);

        // Verificação (ASSERT)
        assertEquals(status, usuarioDTODesativar.getContaStatus());
    }

    @Test
    public void deveTestarRemoverUsuarioComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer id = 4;

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setIdUsuario(id);
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));
        // Ação (ACT)
        usuarioService.remover(id);

        // Verificação (ASSERT)
        verify(usuarioRepository, times(1)).delete(any());

    }

    @Test
    public void deveTestarGetLoggedUser() {
        // Criar variaveis (SETUP)
        UsernamePasswordAuthenticationToken dto = new UsernamePasswordAuthenticationToken(1,null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);

        // Ação (ACT)
        Integer idUsuarioLogado = usuarioService.getIdLoggedUser();

        // Verificação (ASSERT)
        assertEquals(1, idUsuarioLogado);

    }

    @Test
    public void deveTestarDesativarConta() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        UsernamePasswordAuthenticationToken dto = new UsernamePasswordAuthenticationToken(1,null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);

        Character status = '0';
        UsuarioEntity usuarioEntityRetorno = getUsuarioEntity();
        usuarioEntityRetorno.setContaStatus(status);

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntityRetorno));
        when(usuarioRepository.save(any())).thenReturn(usuarioEntityRetorno);
        when(cargoRepository.findByNome(anyString())).thenReturn(Optional.of(getCargoEntity()));

        // Ação (ACT)
        UsuarioDTO usuarioDTODesativar = usuarioService.desativarConta();

        // Verificação (ASSERT)
        assertNotNull(usuarioDTODesativar);
        assertEquals(usuarioEntityRetorno.getContaStatus(), usuarioDTODesativar.getContaStatus());

    }

    private static UsuarioEntity getUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome("Alanis");
        usuarioEntity.setEmail("alanis@mail");
        usuarioEntity.setSenha("123");
        usuarioEntity.setTelefone("51988784");
        usuarioEntity.setContaStatus('1');
        usuarioEntity.setIdUsuario(10);

        Set<CargoEntity> cargoEntities = new HashSet<>();
        cargoEntities.add(getCargoEntity());
        usuarioEntity.setCargos(cargoEntities);

        return usuarioEntity;
    }

    private static UsuarioDTO getUsuarioDTO() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Alanis");
        usuarioDTO.setEmail("alanis@mail");
        usuarioDTO.setTelefone("51988784");

        List<CargoDto> cargoDtoList = new ArrayList<>();
        cargoDtoList.add(getCargoDto());
        usuarioDTO.setCargos(cargoDtoList);

        return usuarioDTO;
    }

    private static UsuarioCreateDTO getUsuarioCreateDTO() {
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        usuarioCreateDTO.setNome("Alanis");
        usuarioCreateDTO.setEmail("alanis@mail");
        usuarioCreateDTO.setSenha("123");
        usuarioCreateDTO.setTelefone("51988784");

        return usuarioCreateDTO;
    }

    private static CargoEntity getCargoEntity() {
        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setIdCargo(1);
        cargoEntity.setNome("ROLE_ADMIN");


        return cargoEntity;
    }

    private static CargoDto getCargoDto() {
        CargoDto cargoDTO = new CargoDto();
        cargoDTO.setNome("ROLE_CLIENTE");

        return cargoDTO;
    }

    private static UsuarioCargosDTO getUsuarioCargosDTO() {
        UsuarioCargosDTO usuarioCargosDTO = new UsuarioCargosDTO();
        usuarioCargosDTO.setIdUsuario(2);
        usuarioCargosDTO.setCargos(List.of());

        return usuarioCargosDTO;
    }

    private static UsuarioRelatorioPedidoDTO getUsuarioRelatorioPedidoDTO() {
        UsuarioRelatorioPedidoDTO usuarioRelatorioPedidoDTO = new UsuarioRelatorioPedidoDTO();
        usuarioRelatorioPedidoDTO.setIdPedido(1);
        usuarioRelatorioPedidoDTO.setNome("Felipe");
        usuarioRelatorioPedidoDTO.setEmail("felipe@mail");

        return  usuarioRelatorioPedidoDTO;
    }

    private static UsuarioRelatorioGeralDTO getUsuarioRelatorioGeralDTO() {
        UsuarioRelatorioGeralDTO relatorioGeralDTO = new UsuarioRelatorioGeralDTO();
        relatorioGeralDTO.setNomeUsuario("Jean");
        relatorioGeralDTO.setEmail("jean@mail");
        relatorioGeralDTO.setValorFinal(200.90);
        relatorioGeralDTO.setDesconto(20.00);

        return relatorioGeralDTO;
    }
}
