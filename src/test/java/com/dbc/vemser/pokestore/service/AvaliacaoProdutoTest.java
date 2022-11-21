package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.*;
import com.dbc.vemser.pokestore.entity.AvaliacaoProdutoEntity;
import com.dbc.vemser.pokestore.entity.ProdutoEntity;
import com.dbc.vemser.pokestore.entity.UsuarioEntity;
import com.dbc.vemser.pokestore.enums.Tipos;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.AvaliacaoProdutoRepository;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AvaliacaoProdutoTest {

    @InjectMocks
    private AvaliacaoProdutoService avaliacaoProdutoService;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private ProdutoService produtoService;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private AvaliacaoProdutoRepository avaliacaoProdutoRepository;


    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(avaliacaoProdutoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCadastrarAvaliacaoComSucesso () throws RegraDeNegocioException {

        // SETUP
        AvaliacaoProdutoCreateDTO avaliacaoProdutoCreateDTO = getAvaliacaoProdutoCreateDTO();
        AvaliacaoProdutoEntity avaliacaoProdutoEntity = getAvaliacaoProdutoEntity();
        UsuarioDTO usuarioRecuperado = getUsuarioDTO();
        ProdutoEntity produto = getProdutoEntity();

        when(usuarioService.getLoggedUser()).thenReturn(usuarioRecuperado);
        when(produtoService.findById(anyInt())).thenReturn(produto);
        when(avaliacaoProdutoRepository.save(any())).thenReturn(avaliacaoProdutoEntity);

        // ACT
        AvaliacaoProdutoDTO produtoDTORetorno = avaliacaoProdutoService.cadastrarAvaliacao(avaliacaoProdutoCreateDTO, produto.getIdProduto());

        // ASSERT
        assertNotNull(produtoDTORetorno);
        assertEquals(avaliacaoProdutoCreateDTO.getNota(), produtoDTORetorno.getNota());
    }

    @Test
    public void deveTestarFiltrarPorNotaComSucesso() {

        // SETUP
        Double nota = 7.0;
        AvaliacaoProdutoEntity avaliacaoProdutoEntity = getAvaliacaoProdutoEntity();
        avaliacaoProdutoEntity.setIdProduto(11);
        List<AvaliacaoProdutoEntity> avaliacaoProdutoEntityList = new ArrayList<>();
        avaliacaoProdutoEntityList.add(avaliacaoProdutoEntity);

        when(avaliacaoProdutoRepository.aggPorNota(anyDouble())).thenReturn(avaliacaoProdutoEntityList);

        // ACT
        List<AvaliacaoProdutoDTO> avaliacaoProdutoDTO = avaliacaoProdutoService.filtrarPorNota(nota);

        // ASSERT
        assertNotNull(avaliacaoProdutoDTO);
        assertEquals(1, avaliacaoProdutoDTO.size());
    }

    @Test
    public void deveTestarFiltrarAvaliacaoUltimaSemana() throws RegraDeNegocioException {
        // SETUP

        // ACT
        List<AvaliacaoProdutoUltimaSemanaDTO> produtoUltimaSemanaDTOS = avaliacaoProdutoService.filtarAvaliacaoUltimaSemana();

        // ASSERT
        assertNotNull(produtoUltimaSemanaDTOS);
        assertEquals("33", getAvaliacaoProdutoUltimaSemanaDTO().getIdAvaliacao());
    }

    private static AvaliacaoProdutoEntity getAvaliacaoProdutoEntity() {
        AvaliacaoProdutoEntity avaliacaoProdutoEntity = new AvaliacaoProdutoEntity();
        avaliacaoProdutoEntity.setNomeUsuario("Mike Mangini");
        avaliacaoProdutoEntity.setNomeProduto("Pikachu");
        avaliacaoProdutoEntity.setAvaliacaoProduto("Produto excelente.");
        avaliacaoProdutoEntity.setNota(7.0);
        avaliacaoProdutoEntity.setDataAvaliacao(LocalDate.of(2022, 11, 18));
        avaliacaoProdutoEntity.setIdAvaliacao("2b");
        return avaliacaoProdutoEntity;
    }

    private static AvaliacaoProdutoCreateDTO getAvaliacaoProdutoCreateDTO() {
        AvaliacaoProdutoCreateDTO avaliacaoProdutoCreateDTO = new AvaliacaoProdutoCreateDTO();
        avaliacaoProdutoCreateDTO.setAvaliacaoProduto("Prouto de ótima qualidade");
        avaliacaoProdutoCreateDTO.setNota(9.0);
        return avaliacaoProdutoCreateDTO;
    }

    private static UsuarioDTO getUsuarioDTO() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Alanis");
        usuarioDTO.setEmail("alanis@mail");
        usuarioDTO.setTelefone("51988784");

        List<CargoDto> cargoDtoList = new ArrayList<>();
        usuarioDTO.setCargos(cargoDtoList);

        return usuarioDTO;
    }

    private ProdutoEntity getProdutoEntity() {
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

    private AvaliacaoProdutoUltimaSemanaDTO getAvaliacaoProdutoUltimaSemanaDTO() {
        AvaliacaoProdutoUltimaSemanaDTO produtoUltimaSemanaDTO = new AvaliacaoProdutoUltimaSemanaDTO();
        produtoUltimaSemanaDTO.setNomeProduto("Pikachu");
        produtoUltimaSemanaDTO.setIdAvaliacao("33");
        produtoUltimaSemanaDTO.setNomeUsuario("Alanis");

        return produtoUltimaSemanaDTO;
    }

}
