package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.entity.Produto;
import com.dbc.vemser.pokestore.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    // criação de um objeto
    public void adicionarProduto(Produto produto) {
        try {
            Produto produtoAdicionado = produtoRepository.adicionar(produto);
            System.out.println("Produto adicionado com sucesso! " + produtoAdicionado);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
//            System.out.println("TRACE: ");
//            e.printStackTrace();
        }
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
    public void editarProduto(Integer id, Produto produto) {
        try {
            boolean conseguiuEditar = produtoRepository.editar(id, produto);
            System.out.println("Produto editado? " + conseguiuEditar + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    // leitura
    public List<Produto> listarProdutos() {
        try {
            List<Produto> listar = produtoRepository.listar();
            return listar;
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e.getCause());
        }
    }
}
