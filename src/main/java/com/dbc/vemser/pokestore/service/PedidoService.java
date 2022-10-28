package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.*;
import com.dbc.vemser.pokestore.entity.*;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.CupomRepository;
import com.dbc.vemser.pokestore.repository.PedidoRepository;
import com.dbc.vemser.pokestore.repository.ProdutoPedidoRepository;
import com.dbc.vemser.pokestore.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final CupomService cupomService;
    private final ProdutoService produtoService;
    private final UsuarioService usuarioService;
    private final PedidoRepository pedidoRepository;

    private final ProdutoRepository produtoRepository;

    private final CupomRepository cupomRepository;
    private final ProdutoPedidoRepository produtoPedidoRepository;

    private final ObjectMapper objectMapper;


    // criação de um objeto
    public PedidoDTO adicionarPedido(PedidoCreateDTO pedido) throws BancoDeDadosException, RegraDeNegocioException { // vai adicionar tudo dentro da tabela N para N (Pedido_Produto)
        Cupom cupom = null;
        if(pedido.getIdCupom() != null) {
          CupomDTO cupomDTO = cupomService.findById(pedido.getIdCupom());
          cupom = objectMapper.convertValue(cupomDTO, Cupom.class);
        }
        PedidoDTO pedidoDTO = new PedidoDTO();
        UsuarioDTO usuario = usuarioService.findById(pedido.getIdUsuario());

        Pedido pedidoEntity = objectMapper.convertValue(pedido, Pedido.class);
        pedidoEntity.setCupom(cupom);
        pedidoEntity.setIdUsuario(usuario.getIdUsuario());
        pedidoEntity = pedidoRepository.adicionar(pedidoEntity);

        double valorFinal = 0;

        List<ProdutoPedidoDTO> produtoPedidoDTOList = new ArrayList<>();

        for(ProdutoIdQuantidadeCreateDTO produtosIdQuantidadeDTO: pedido.getProdutosDTO()) { //aqui dentro faço o findbyID, calculo o valorFinal tb
            ProdutoDTO produtoDTO = produtoService.findById(produtosIdQuantidadeDTO.getIdProduto());
            Produto produto = objectMapper.convertValue(produtoDTO, Produto.class);
            ProdutoPedido produtoPedido = new ProdutoPedido();
            produtoPedido.setProduto(produto);
            produtoPedido.setQuantidade(produtosIdQuantidadeDTO.getQuantidade());
            produtoPedido.setPedido(pedidoEntity);
            double valorProdutoPedido = produto.getValor() * produtosIdQuantidadeDTO.getQuantidade() ;
            produtoPedido.setValor(valorProdutoPedido);
            produtoPedidoRepository.adicionar(produtoPedido);
            valorFinal += valorProdutoPedido;
            ProdutoPedidoDTO produtoPedidoDTO = objectMapper.convertValue(produtoPedido, ProdutoPedidoDTO.class);
            produtoPedidoDTOList.add(produtoPedidoDTO);
        }
        if(cupom != null) {
            valorFinal -= cupom.getValor();
        }
        pedidoEntity.setValorFinal(valorFinal);
        pedidoRepository.editarValorFinal(pedidoEntity.getIdPedido(), valorFinal);

        pedidoDTO.setIdPedido(pedidoEntity.getIdPedido());
        pedidoDTO.setProdutosPedido(produtoPedidoDTOList);
        pedidoDTO.setValorFinal(valorFinal);

        return pedidoDTO;
    }

//    Calculo valor final
//    public double calcularValorFinal(Pedido pedido) { // vai mudar, mas so vai somar os produtos.
//        if (pedido.getProdutosPedido().size() > 0) {
//            double valorFinal = 0;
//            for (ProdutoPedido value : pedido.getProdutosPedido()) {
//                valorFinal += value.getValor();
//            }
//            if (pedido.getCupom() != null && pedido.getCupom().getDeletado().equals("F")) {
//                valorFinal = valorFinal - pedido.getCupom().getValor();
//            }
//            return valorFinal;
//        }
//        return 0;
//    }

//    public Pedido inserirCupom(Cupom cupom, Pedido pedido){
//        try {
//            pedido.setCupom(cupom);
//            pedidoRepository.atualizarCupom(pedido.getCupom().getIdCupom(),pedido);
//            pedidoRepository.editar(pedido.getIdPedido(),pedido);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
//        return pedido;
//    }

    // remoção
    public void removerPedido(Integer id) {
        try {
            boolean conseguiuRemover = pedidoRepository.remover(id);
            System.out.println("Pedido removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    // atualização de um objeto
    public PedidoDTO editarPedido(Integer id, PedidoCreateDTO pedido) throws BancoDeDadosException, RegraDeNegocioException{

        if(pedidoRepository.findById(id) == null){
            throw new RegraDeNegocioException("Pedido não encontrado!");
        }

        Pedido pedidoEntity = objectMapper.convertValue(pedido, Pedido.class);

        Pedido editado = pedidoRepository.editarPedido(id, pedidoEntity);

        log.info("Cupom editado!");
        return objectMapper.convertValue(editado, PedidoDTO.class);
    }

    // leitura
    public List<PedidoDTO> listarPedido() throws BancoDeDadosException {
            return pedidoRepository.listar().stream()
                .map(pedido -> objectMapper.convertValue(pedido, PedidoDTO.class))
                .toList();
    }

    public PedidoDTO findById(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        Pedido pedido = pedidoRepository.findById(id);
        if(pedido == null){
            throw new RegraDeNegocioException("Pedido não encontrado");
        }
        log.info("Pedido encontrado!!");
        PedidoDTO pedidoDTO = objectMapper.convertValue(pedido, PedidoDTO.class);
        return pedidoDTO;
    }

}

// primeiro receber os dados
// mudar o jeito que chega (ta vindo do objeto), tem que vir do BD.
// Tem que pegar o idCupom, idProduto, quantidadeProduto e calcular direto no adicionar;
// Controler ter ids e quantidade