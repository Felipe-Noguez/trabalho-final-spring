package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.PageDTO;
import com.dbc.vemser.pokestore.dto.ProdutoCreateDTO;
import com.dbc.vemser.pokestore.dto.ProdutoDTO;
import com.dbc.vemser.pokestore.entity.ProdutoEntity;
import com.dbc.vemser.pokestore.entity.UsuarioEntity;
import com.dbc.vemser.pokestore.enums.Tipos;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.ProdutoRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;
    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private UsuarioService usuarioService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(produtoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarAdicionarProdutoComSucesso() throws RegraDeNegocioException {

        // SETUP
        ProdutoCreateDTO produtoCreateDTO = getProdutoCreateDTO();
        ProdutoEntity produtoEntity = getProdutoEntity();
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(5);

        when(usuarioService.findById(anyInt())).thenReturn(usuario);
        when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

        // ACT
        ProdutoDTO produtoDTO = produtoService.adicionarProduto(produtoCreateDTO);

        // ASSERT
        assertNotNull(produtoDTO);
        assertEquals(80.0, produtoDTO.getValor());
    }

    @Test
    public void deveTestarRemoverProdutoComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer id = 5;
        ProdutoEntity produtoEntity = getProdutoEntity();

        when(produtoRepository.findById(anyInt())).thenReturn(Optional.of(produtoEntity));

        // ACT
        produtoService.removerProduto(id);

        // ASSERT
        verify(produtoRepository, times(1)).delete(any());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarRemoverProdutoComFalha() throws RegraDeNegocioException {
        // SETUP
        Integer id = 5;
        when(produtoRepository.findById(anyInt())).thenReturn(Optional.empty());

        // ACT
        produtoService.removerProduto(id);

        // ASSERT
        verify(produtoRepository, times(1)).delete(any());
    }

    @Test
    public void deveTestarEditarProdutoComSucesso() throws RegraDeNegocioException {

        // SETUP
        Integer id = 5;
        ProdutoCreateDTO produtoCreateDTO = getProdutoCreateDTO();

        // findById(id);
        ProdutoEntity produtoEntity = getProdutoEntity();
        ProdutoEntity produto = getProdutoEntity();
        produto.setValor(180.0);

        when(produtoRepository.findById(anyInt())).thenReturn(Optional.of(produtoEntity));
        when(produtoRepository.save(any())).thenReturn(produto);

        // ACT
        ProdutoDTO produtoDTO = produtoService.editarProduto(id, produtoCreateDTO);

        // ASSERT
        assertNotNull(produtoDTO);
        assertNotEquals(80.0, produtoDTO.getValor());
    }


    @Test
    public void deveTestarListarProdutosComSucesso(){
        // SETUP
        Integer pagina = 10;
        Integer quantidade = 1;

        //pessoaRepository.findAll(pageable);
        ProdutoEntity produtoEntity = getProdutoEntity();
        Page<ProdutoEntity> paginaMock = new PageImpl<>(List.of(produtoEntity));
        when(produtoRepository.findAll(any(Pageable.class))).thenReturn(paginaMock);

        // ACT
        PageDTO<ProdutoDTO> paginaSolicitada = produtoService.listarProdutos(pagina, quantidade);

        // ASSERT
        assertNotNull(paginaSolicitada);
        assertEquals(1, paginaSolicitada.getTamanho());
        assertEquals(1, paginaSolicitada.getQuantidadePaginas());
    }

    private ProdutoCreateDTO getProdutoCreateDTO() {
        ProdutoCreateDTO produtoCreateDTO = new ProdutoCreateDTO();
        produtoCreateDTO.setValor(80.0);
        produtoCreateDTO.setIdUsuario(1);
        produtoCreateDTO.setQuantidade(10);
        produtoCreateDTO.setNome("Pelucia");
        produtoCreateDTO.setTipo(Tipos.COLECIONAVEL);
        produtoCreateDTO.setDescricao("Muito grande");

        return produtoCreateDTO;
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

}
