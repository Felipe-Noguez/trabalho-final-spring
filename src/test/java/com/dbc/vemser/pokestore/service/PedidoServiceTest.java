package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.*;
import com.dbc.vemser.pokestore.entity.*;
import com.dbc.vemser.pokestore.enums.Tipos;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.PedidoRepository;
import com.dbc.vemser.pokestore.repository.ProdutoPedidoRepository;
import com.dbc.vemser.pokestore.repository.UsuarioRepository;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private EmailService emailService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CupomService cupomService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoPedidoRepository produtoPedidoRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ProdutoService produtoService;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(pedidoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarAdicionarPedido() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        CupomEntity cupomEntity = getCupomEntity();
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        PedidoEntity pedidoEntity = getPedidoEntity();
        PedidoCreateDTO pedidoCreateDTO = getPedidoCreateDTO();
        ProdutoPedidoEntity produtoPedido = getProdutoPedidoEntity();
        ProdutoEntity produtoEntity = getProdutoEntity();

        when(cupomService.findById(any())).thenReturn(cupomEntity);
        when(usuarioService.findById(anyInt())).thenReturn(usuarioEntity);
        when(produtoService.findById(anyInt())).thenReturn(produtoEntity);
        when(produtoPedidoRepository.save(any())).thenReturn(produtoPedido);
        when(pedidoRepository.save(any())).thenReturn(pedidoEntity);


        // Ação (ACT)
        PedidoDTO pedidoDTO = pedidoService.adicionarPedido(pedidoCreateDTO);

        // Verificação (ASSERT)
        assertNotNull(pedidoDTO);
//        assertEquals();

    }

    @Test
    public void deveTestarEditarPedido() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer id = 88;

        CupomEntity cupomEntity = getCupomEntity();
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        PedidoCreateDTO pedidoCreateDTO = getPedidoCreateDTO();
        PedidoEntity pedidoEntity = getPedidoEntity();
        ProdutoPedidoEntity produtoPedido = getProdutoPedidoEntity();
        ProdutoEntity produtoEntity = getProdutoEntity();

        when(cupomService.findById(anyInt())).thenReturn(cupomEntity);
        when(pedidoRepository.findById(anyInt())).thenReturn(Optional.of(pedidoEntity));
        when(usuarioService.findById(anyInt())).thenReturn(usuarioEntity);
        when(produtoPedidoRepository.save(any(ProdutoPedidoEntity.class))).thenReturn(produtoPedido);
        when(produtoService.findById(any())).thenReturn(produtoEntity);
        when(pedidoRepository.save(any(PedidoEntity.class))).thenReturn(pedidoEntity);

        // Ação (ACT)
        PedidoDTO pedidoDTO = pedidoService.editarPedido(id ,pedidoCreateDTO);

        // Verificação (ASSERT)
        assertNotNull(pedidoDTO);
//        assertNotEquals(88, pedidoDTO.getIdPedido());
    }

    @Test
    public void deveTestarListarPedidosComSucesso(){

        // SETUP
        PedidoEntity pedidoEntity = getPedidoEntity();
        List<PedidoEntity> entityList = new ArrayList<>();
        entityList.add(pedidoEntity);

        when(pedidoRepository.findAll()).thenReturn(entityList);

        // ACT
        List<PedidoDTO> paginaSolicitada = pedidoService.listarPedido();

        // ASSERT
        assertNotNull(paginaSolicitada);
        assertEquals(1, paginaSolicitada.size());
    }

    @Test
    public void deveTestarRemoverPedido() throws RegraDeNegocioException {
        // SETUP
        Integer id = 88;
        PedidoEntity pedidoEntity = getPedidoEntity();
        when(pedidoRepository.findById(anyInt())).thenReturn(Optional.of(pedidoEntity));

        // ACT
        pedidoService.removerPedido(id);

        // ASSERT
        verify(pedidoRepository, times(1)).delete(any());
    }

    @Test
    public void deveTestarFindById() throws RegraDeNegocioException {
        // SETUP
        Integer id = 20;
        PedidoEntity pedidoEntity = getPedidoEntity();
        when(pedidoRepository.findById(anyInt())).thenReturn(Optional.of(pedidoEntity));

        // ACT
        PedidoEntity pedidoEntity1 = pedidoService.findById(id);

        // ASSERT
        assertNotNull(pedidoEntity1);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // SETUP
        Integer id = 20;
        PedidoEntity pedidoEntity = getPedidoEntity();
        when(pedidoRepository.findById(anyInt())).thenReturn(Optional.empty());

        // ACT
        PedidoEntity pedidoEntity1 = pedidoService.findById(id);

        // ASSERT
    }

    public static CupomEntity getCupomEntity() {
        CupomEntity cupomEntity = new CupomEntity();
        cupomEntity.setIdCupom(33);
        cupomEntity.setValor(69.90);

        Set<PedidoEntity> pedidoEntities = new HashSet<>();
        pedidoEntities.add(getPedidoEntity());

        return cupomEntity;
    }

    public static PedidoEntity getPedidoEntity() {
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setIdPedido(88);
        pedidoEntity.setUsuario(getUsuarioEntity());

        return pedidoEntity;
    }

    public static PedidoCreateDTO getPedidoCreateDTO() {
        PedidoCreateDTO pedidoCreateDTO = new PedidoCreateDTO();
        pedidoCreateDTO.setIdCupom(1);
        pedidoCreateDTO.setIdUsuario(55);

        List<ProdutoIdQuantidadeCreateDTO> produtoIdQuantidadeCreateDTOS = new ArrayList<>();
        produtoIdQuantidadeCreateDTOS.add(getProdutoIdQuantidadeCreateDTO());
        pedidoCreateDTO.setProdutosDTO(produtoIdQuantidadeCreateDTOS);

        return pedidoCreateDTO;
    }

    public static ProdutoPedidoEntity getProdutoPedidoEntity(){
        ProdutoPedidoEntity produtoPedido = new ProdutoPedidoEntity();
        produtoPedido.setProduto(getProdutoEntity());
        produtoPedido.setPedido(getPedidoEntity());
        produtoPedido.setIdPedido(getPedidoEntity().getIdPedido());
        produtoPedido.setIdProduto(getProdutoEntity().getIdProduto());
        produtoPedido.setIdProdutoPedido(25);
        produtoPedido.setValor(500.0);

        return produtoPedido;
    }

    public static ProdutoIdQuantidadeCreateDTO getProdutoIdQuantidadeCreateDTO(){
        ProdutoIdQuantidadeCreateDTO produtoIdQuantidadeCreateDTO = new ProdutoIdQuantidadeCreateDTO();
        produtoIdQuantidadeCreateDTO.setIdProduto(44);
        produtoIdQuantidadeCreateDTO.setQuantidade(10);

        return produtoIdQuantidadeCreateDTO;
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

    private static CargoEntity getCargoEntity() {
        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setIdCargo(1);
        cargoEntity.setNome("ROLE_ADMIN");

        return cargoEntity;
    }

    private static ProdutoCreateDTO getProdutoCreateDTO() {
        ProdutoCreateDTO produtoCreateDTO = new ProdutoCreateDTO();
        produtoCreateDTO.setValor(80.0);
        produtoCreateDTO.setIdUsuario(1);
        produtoCreateDTO.setQuantidade(10);
        produtoCreateDTO.setNome("Pelucia");
        produtoCreateDTO.setTipo(Tipos.COLECIONAVEL);
        produtoCreateDTO.setDescricao("Muito grande");

        return produtoCreateDTO;
    }

    private static ProdutoEntity getProdutoEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome("gustavo");

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setIdProduto(5);
        produtoEntity.setValor(80.0);
        produtoEntity.setQuantidade(10);
        produtoEntity.setNome("Pelucia");
        produtoEntity.setTipo(Tipos.COLECIONAVEL);
        produtoEntity.setDescricao("Muito grande");
        produtoEntity.setUsuario(usuarioEntity);

        return produtoEntity;
    }
}
