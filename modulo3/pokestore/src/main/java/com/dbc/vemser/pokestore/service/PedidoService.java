package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.CupomDTO;
import com.dbc.vemser.pokestore.dto.PedidoCreateDTO;
import com.dbc.vemser.pokestore.dto.PedidoDTO;
import com.dbc.vemser.pokestore.dto.ProdutoIdQuantidadeCreateDTO;
import com.dbc.vemser.pokestore.entity.*;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.CupomRepository;
import com.dbc.vemser.pokestore.repository.PedidoRepository;
import com.dbc.vemser.pokestore.repository.ProdutoPedidoRepository;
import com.dbc.vemser.pokestore.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    CupomService cupomService;
    ProdutoService produtoService;
    UsuarioService usuarioService;
    private final PedidoRepository pedidoRepository;

    private final ProdutoRepository produtoRepository;

    private final CupomRepository cupomRepository;
    private final ProdutoPedidoRepository produtoPedidoRepository;

    private final ObjectMapper objectMapper;


    // criação de um objeto
    public PedidoDTO adicionarPedido(PedidoCreateDTO pedido) throws BancoDeDadosException, RegraDeNegocioException { // vai adicionar tudo dentro da tabela N para N (Pedido_Produto)

        Cupom cupom = cupomService.findById(pedido.getIdCupom());

        Usuario usuario = usuarioService.findById(pedido.getIdUsuario());

        ProdutoPedido produtoPedido = new ProdutoPedido();

        for(ProdutoIdQuantidadeCreateDTO produtosId: pedido.getProdutosDTO()) { //aqui dentro faço o findbyID, calculo o valorFinal tb
            List<Produto> produtos = produtoService.findById(produtosId);

            produto.setValor(produtosId.getQuantidade() * produtosId.getIdProduto());

            produtoPedidoRepository.adicionar(produtoService.findById(produtosId));
        }




//        produtoPedido = produtoPedidoRepository.listar().stream()
//                .filter(pedido1 -> pedido.getProduto().equals(pedido.getProduto()))
//                .findFirst()
//                .get();
//
//        for(int i = 0; i <= pedido.getProdutosDTO().size() ; i++) {
//            produtoRepository.adicionar(pedido.getProdutosDTO().indexOf(i);
//            produtoPedido.setProduto(produtoPedidoRepository.);
//        }

        Pedido pedidoEntity = objectMapper.convertValue(pedido, Pedido.class); // idCupom, idProduto, Quantidade (Produto), calcular direto daqui
//        Produto produto = produtoRepository.adicionar(pedido.);
        Cupom cupom = null;
        cupom.setIdCupom(pedido.getIdCupom());
        Pedido pedidoSalvo = pedidoRepository.adicionar(pedidoEntity);
        //inserir cupom
//        for (ProdutoPedido produtoPedido : pedidoEntity.getProdutosPedido()) {
//            produtoPedido.setPedido(pedidoSalvo);
//            produtoPedidoRepository.adicionar(produtoPedido);
//        }
        //calcularValorFinal, mandar o pedido, produtopedido para o bd;
        PedidoDTO pedidoDTO = objectMapper.convertValue(pedido, PedidoDTO.class);

        System.out.println("Pedido adicionado com sucesso! " + pedidoEntity);
            return pedidoDTO;
    }

//    Calculo valor final
    public double calcularValorFinal(Pedido pedido) { // vai mudar, mas so vai somar os produtos.
        if (pedido.getProdutosPedido().size() > 0) {
            double valorFinal = 0;
            for (ProdutoPedido value : pedido.getProdutosPedido()) {
                valorFinal += value.getValor();
            }
            if (pedido.getCupom() != null && pedido.getCupom().getDeletado().equals("F")) {
                valorFinal = valorFinal - pedido.getCupom().getValor();
            }
            return valorFinal;
        }
        return 0;
    }

    public Pedido inserirCupom(Cupom cupom, Pedido pedido){
        try {
            pedido.setCupom(cupom);
            pedidoRepository.atualizarCupom(pedido.getCupom().getIdCupom(),pedido);
            pedidoRepository.editar(pedido.getIdPedido(),pedido);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return pedido;
    }

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

        Pedido pedidoRecuperado = findById(id);
        System.out.println("fdsasad");
        pedidoRepository.editar(id, pedidoRecuperado);
        boolean conseguiuEditar = pedidoRepository.editar(id, pedidoRecuperado);
        System.out.println("Pedido editado? " + conseguiuEditar + "| com id=" + id);
        PedidoDTO pedidoDTO = objectMapper.convertValue(pedidoRecuperado, PedidoDTO.class);
        return pedidoDTO;
    }

    // leitura
    public List<PedidoDTO> listarPedido() throws BancoDeDadosException {
            return pedidoRepository.listar().stream()
                .map(pedido -> objectMapper.convertValue(pedido, PedidoDTO.class))
                .toList();
    }

    public Pedido findById(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        Pedido pedidoRecuperado = pedidoRepository.listar().stream()
                .filter(pedido -> pedido.getIdPedido().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Pedido não encontrado"));
        return pedidoRecuperado;
    }

    public Produto findByIdProduto(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        Produto produtoRecuperado = produtoRepository.listar().stream()
                .filter(produto -> produto.getIdProduto().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Pedido não encontrado"));
        return produtoRecuperado;
    }

}

// primeiro receber os dados
// mudar o jeito que chega (ta vindo do objeto), tem que vir do BD.
// Tem que pegar o idCupom, idProduto, quantidadeProduto e calcular direto no adicionar;
// Controler ter ids e quantidade