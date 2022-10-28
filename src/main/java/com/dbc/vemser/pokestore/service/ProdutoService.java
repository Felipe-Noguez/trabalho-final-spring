package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.ProdutoCreateDTO;
import com.dbc.vemser.pokestore.dto.ProdutoDTO;
import com.dbc.vemser.pokestore.entity.Produto;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    private final ObjectMapper objectMapper;

    // criação de um objeto
    public ProdutoDTO adicionarProduto(ProdutoCreateDTO produto) throws RegraDeNegocioException {
            Produto produtoAdicionado = objectMapper.convertValue(produto, Produto.class);
        ProdutoDTO produtoDTO = null;
        try {
            produtoDTO = objectMapper.convertValue(produtoRepository.adicionar(produtoAdicionado), ProdutoDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao dicionar o produto ao banco de dados!");
        }
        System.out.println("Produto adicionado com sucesso! " + produtoAdicionado);
            return produtoDTO;
    }

    // remoção
    public void removerProduto(Integer id) throws RegraDeNegocioException {
        try {
            boolean conseguiuRemover = produtoRepository.remover(id);
            System.out.println("Produto removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao remover o produto do banco de dados!");
        }
    }

    // atualização de um objeto
    public ProdutoDTO editarProduto(Integer id, ProdutoCreateDTO produto) throws RegraDeNegocioException {
            try {
                produtoRepository.findById(id);
            } catch (BancoDeDadosException e) {
                throw new RegraDeNegocioException("Erro ao editar no banco de dados!");
            }

        Produto produtoEntity = objectMapper.convertValue(produto, Produto.class);

        Produto editado = null;
        try {
            editado = produtoRepository.editarProduto(id, produtoEntity);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao editar o produto!");
        }
        editado.setIdProduto(id);

        log.info("Cupom editado!");
        return objectMapper.convertValue(editado, ProdutoDTO.class);
    }

    // leitura
    public List<ProdutoDTO> listarProdutos() throws RegraDeNegocioException {
        try {
            return produtoRepository.listar().stream()
                    .map(produto -> objectMapper.convertValue(produto, ProdutoDTO.class))
                    .toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao listas os produtos do banco de dados!");
        }
    }

    public ProdutoDTO findById(Integer id) throws RegraDeNegocioException {
        Produto produto = null;
        try {
            produto = produtoRepository.findById(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Impossível encontrar o ID do produto no banco de dados!");
        }
        if(produto == null){
            throw new RegraDeNegocioException("Produto não encontrado");
        }
        log.info("Produto encontrado!!");
        ProdutoDTO produtoDTO = objectMapper.convertValue(produto, ProdutoDTO.class);
        return produtoDTO;
    }
}