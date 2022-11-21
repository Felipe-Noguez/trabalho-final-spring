package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.AvaliacaoProdutoCreateDTO;
import com.dbc.vemser.pokestore.dto.AvaliacaoProdutoDTO;
import com.dbc.vemser.pokestore.dto.AvaliacaoProdutoUltimaSemanaDTO;
import com.dbc.vemser.pokestore.dto.UsuarioDTO;
import com.dbc.vemser.pokestore.entity.AvaliacaoProdutoEntity;
import com.dbc.vemser.pokestore.entity.ProdutoEntity;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.AvaliacaoProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoProdutoService {

    private final AvaliacaoProdutoRepository avaliacaoProdutoRepository;
    private final UsuarioService usuarioService;
    private final ProdutoService produtoService;
    private final ObjectMapper objectMapper;
    private final LocalDate ultimaSemana = LocalDate.now().minusDays(7);


    public AvaliacaoProdutoDTO cadastrarAvaliacao(AvaliacaoProdutoCreateDTO avaliacaoProdutoCreateDTO, Integer idProduto) throws RegraDeNegocioException {

        UsuarioDTO usuarioRecuperado = usuarioService.getLoggedUser();
        ProdutoEntity produtoRecuperado = produtoService.findById(idProduto);

        AvaliacaoProdutoEntity avaliacaoProdutoEntity = objectMapper.convertValue(avaliacaoProdutoCreateDTO, AvaliacaoProdutoEntity.class);
        avaliacaoProdutoEntity.setDataAvaliacao(LocalDate.now());
        avaliacaoProdutoEntity.setNomeProduto(produtoRecuperado.getNome());
        avaliacaoProdutoEntity.setIdProduto(idProduto);
        avaliacaoProdutoEntity.setNomeUsuario(usuarioRecuperado.getNome());

        avaliacaoProdutoRepository.save(avaliacaoProdutoEntity);
        return objectMapper.convertValue(avaliacaoProdutoEntity, AvaliacaoProdutoDTO.class);
    }

    public List<AvaliacaoProdutoDTO> filtrarPorNota(Double notaAvaliacao) {

        return avaliacaoProdutoRepository.aggPorNota(notaAvaliacao)
                .stream()
                .map(avaliacaoProdutoEntity -> objectMapper.convertValue(avaliacaoProdutoEntity, AvaliacaoProdutoDTO.class))
                .toList();
    }

    public List<AvaliacaoProdutoUltimaSemanaDTO> filtarAvaliacaoUltimaSemana() throws RegraDeNegocioException {
        return avaliacaoProdutoRepository.buscarAvaliacaoUltimaSemana(ultimaSemana)
                .stream()
                .map(avaliacaoProdutoEntity -> objectMapper.convertValue(avaliacaoProdutoEntity, AvaliacaoProdutoUltimaSemanaDTO.class))
                .toList();
    }
}
