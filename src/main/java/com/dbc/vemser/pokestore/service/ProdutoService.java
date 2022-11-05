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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    // criação de um objeto
    public ProdutoDTO adicionarProduto(ProdutoCreateDTO produtoCreate) throws RegraDeNegocioException {

        ProdutoEntity produtoEntity = objectMapper.convertValue(produtoCreate, ProdutoEntity.class);
        produtoEntity.setUsuario(usuarioService.findById(produtoCreate.getIdUsuario()));

        ProdutoDTO produtoDTO = objectMapper.convertValue(produtoRepository.save(produtoEntity), ProdutoDTO.class);
        produtoDTO.setIdProduto(produtoCreate.getIdUsuario());
        return produtoDTO;
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
    public PageDTO<ProdutoDTO> listarProdutos(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);

        Page<ProdutoEntity> pageEntity = produtoRepository.findAll(pageRequest);
        List<ProdutoDTO> listDTO = pageEntity.getContent().stream()
                .map(produto ->  {
                    ProdutoDTO produtoDTO = objectMapper.convertValue(produto, ProdutoDTO.class);
                    produtoDTO.setIdUsuario(produto.getUsuario().getIdUsuario());
                    return produtoDTO;
                }).toList();

        return new PageDTO<>(pageEntity.getTotalElements(), pageEntity.getTotalPages(), pagina, tamanho, listDTO);
    }

    public ProdutoEntity findById(Integer id) throws RegraDeNegocioException {

        return produtoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Produto não encontrado!"));
    }
}