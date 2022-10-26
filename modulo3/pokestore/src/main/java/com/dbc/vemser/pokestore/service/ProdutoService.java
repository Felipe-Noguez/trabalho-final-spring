package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.ProdutoCreateDTO;
import com.dbc.vemser.pokestore.dto.ProdutoDTO;
import com.dbc.vemser.pokestore.entity.Usuario;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.entity.Produto;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    private final ObjectMapper objectMapper;

    // criação de um objeto
    public ProdutoDTO adicionarProduto(ProdutoCreateDTO produto) throws BancoDeDadosException, RegraDeNegocioException {
            Produto produtoAdicionado = objectMapper.convertValue(produto, Produto.class);
            ProdutoDTO produtoDTO = objectMapper.convertValue(produtoRepository.adicionar(produtoAdicionado), ProdutoDTO.class);
            System.out.println("Produto adicionado com sucesso! " + produtoAdicionado);
            return produtoDTO;
    }

    // remoção
    public void removerProduto(Integer id) {
        try {
            boolean conseguiuRemover = produtoRepository.remover(id);
            System.out.println("Produto removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    // atualização de um objeto
    public ProdutoDTO editarProduto(Integer id, ProdutoCreateDTO produto) throws RegraDeNegocioException, BancoDeDadosException {
        Produto produtoRecuperado = findById(id);
        System.out.println("aeae");
        produtoRepository.editar(id, produtoRecuperado);

        boolean conseguiuEditar = produtoRepository.editar(id, produtoRecuperado);
        System.out.println("adad");
        System.out.println("Produto editado? " + conseguiuEditar + "| com id=" + id);
        ProdutoDTO produtoDTO = objectMapper.convertValue(produtoRecuperado, ProdutoDTO.class);
        return produtoDTO;
    }

    // leitura
    public List<ProdutoDTO> listarProdutos() throws BancoDeDadosException, RegraDeNegocioException {
        return produtoRepository.listar().stream()
                .map(produto -> objectMapper.convertValue(produto, ProdutoDTO.class))
                .toList();
    }

    public Produto findById(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        Produto produtoRecuperado = produtoRepository.listar().stream()
                .filter(usuario -> usuario.getIdProduto().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado"));
        return produtoRecuperado;
    }
}