package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.PageDTO;
import com.dbc.vemser.pokestore.dto.ProdutoCreateDTO;
import com.dbc.vemser.pokestore.dto.ProdutoDTO;
import com.dbc.vemser.pokestore.dto.UsuarioDTO;
import com.dbc.vemser.pokestore.entity.ProdutoEntity;
import com.dbc.vemser.pokestore.entity.UsuarioEntity;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.ProdutoRepository;
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
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;

    // criação de um objeto
    public ProdutoDTO adicionarProduto(ProdutoCreateDTO produto) {

        ProdutoEntity produtoAdicionado = objectMapper.convertValue(produto, ProdutoEntity.class);
        return objectMapper.convertValue(produtoRepository.save(produtoAdicionado), ProdutoDTO.class);
    }

    // remoção
    public void removerProduto(Integer id) throws RegraDeNegocioException {
        ProdutoEntity produto = findById(id);
        produtoRepository.delete(produto);

    }

    // atualização de um objeto
    public ProdutoDTO editarProduto(Integer id, ProdutoCreateDTO produtoAtualizar) throws RegraDeNegocioException {

        ProdutoEntity produtoEncontrado = findById(id);
        produtoEncontrado = objectMapper.convertValue(produtoAtualizar, ProdutoEntity.class);
        produtoEncontrado.setIdProduto(id);

        produtoEncontrado = produtoRepository.save(produtoEncontrado);
        return objectMapper.convertValue(produtoEncontrado, ProdutoDTO.class);
    }

    // leitura
    public List<ProdutoDTO> listarProdutos() {
        return produtoRepository.findAll().stream()
                .map(produto ->  {
                    ProdutoDTO produtoDTO = objectMapper.convertValue(produto, ProdutoDTO.class);
                    produtoDTO.setIdUsuario(produto.getUsuario().getIdUsuario());
                    return produtoDTO;
                }).toList();
    }

    public ProdutoEntity findById(Integer id) throws RegraDeNegocioException {

        return produtoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Produto não encontrado!"));
    }

    public PageDTO<ProdutoDTO> listarProdutosPaginados(Integer pagina, Integer numeroPaginas){
        PageRequest pageRequest = PageRequest.of(pagina, numeroPaginas);
        Page<ProdutoEntity> paginaRepository = produtoRepository.findAll(pageRequest);
        List<ProdutoDTO> produtosDaPagina = paginaRepository.getContent().stream()
                .map(produtoEntity -> objectMapper.convertValue(produtoEntity, ProdutoDTO.class))
                .toList();
        return new PageDTO<>(paginaRepository.getTotalElements(),
                paginaRepository.getTotalPages(),
                pagina,
                numeroPaginas,
                produtosDaPagina
        );
    }
}