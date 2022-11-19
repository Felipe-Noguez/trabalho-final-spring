package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.AvaliacaoProdutoCreateDTO;
import com.dbc.vemser.pokestore.dto.AvaliacaoProdutoDTO;
import com.dbc.vemser.pokestore.entity.AvaliacaoProdutoEntity;
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
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AvaliacaoProdutoTest {

    @InjectMocks
    private AvaliacaoProdutoService avaliacaoProdutoService;

    @Mock
    private UsuarioService usuarioService;
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
    public void deveTestarCadastrarAvaliacao () throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        AvaliacaoProdutoCreateDTO avaliacaoProdutoCreateDTO = getAvaliacaoProdutoCreateDTO();

        AvaliacaoProdutoEntity avaliacaoProdutoEntity = getAvaliacaoProdutoEntity();

        avaliacaoProdutoEntity.setIdAvaliacao("22");
        when(avaliacaoProdutoRepository.save(any())).thenReturn(avaliacaoProdutoEntity);

        // Ação (ACT)
        AvaliacaoProdutoDTO produtoDTORetorno = avaliacaoProdutoService.cadastrarAvaliacao(avaliacaoProdutoCreateDTO, avaliacaoProdutoEntity.getIdProduto());

        // Verificação (ASSERT)
        assertNotNull(produtoDTORetorno);

    }

//    @Test
//    public void deveTestarFiltrarPorNotaComSucesso() {
//        // Criar variaveis (SETUP)
//        Double nota = 7.0;
//        AvaliacaoProdutoEntity avaliacaoProdutoEntity = getAvaliacaoProdutoEntity();
//        avaliacaoProdutoEntity.setIdProduto(11);
//        List<AvaliacaoProdutoEntity> avaliacaoProdutoEntityList = new ArrayList<>();
//        avaliacaoProdutoEntityList.add(avaliacaoProdutoEntity);
//
//        when(avaliacaoProdutoRepository.findById()).thenReturn(avaliacaoProdutoEntityList);
//        // Ação (ACT)
//        List<AvaliacaoProdutoDTO> filtrarPorNota = avaliacaoProdutoService.filtrarPorNota(nota);
//
//        // Verificação (ASSERT)
//        assertNotNull(avaliacaoProdutoEntityList);
//
//    }


    private static AvaliacaoProdutoEntity getAvaliacaoProdutoEntity() {
        AvaliacaoProdutoEntity avaliacaoProdutoEntity = new AvaliacaoProdutoEntity();
        avaliacaoProdutoEntity.setNomeUsuario("Mike Mangini");
        avaliacaoProdutoEntity.setNomeProduto("Pikachu");
        avaliacaoProdutoEntity.setAvaliacaoProduto("Produto excelente.");
        avaliacaoProdutoEntity.setNota(7.0);
        avaliacaoProdutoEntity.setDataAvaliacao(LocalDate.of(2022, 11, 18));
        return avaliacaoProdutoEntity;
    }

    private static AvaliacaoProdutoCreateDTO getAvaliacaoProdutoCreateDTO() {
        AvaliacaoProdutoCreateDTO avaliacaoProdutoCreateDTO = new AvaliacaoProdutoCreateDTO();
        avaliacaoProdutoCreateDTO.setAvaliacaoProduto("Prouto de ótima qualidade");
        avaliacaoProdutoCreateDTO.setNota(9.0);
        return avaliacaoProdutoCreateDTO;
    }
}
