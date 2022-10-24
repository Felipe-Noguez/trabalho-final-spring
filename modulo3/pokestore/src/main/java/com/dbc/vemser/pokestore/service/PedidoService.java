package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.CupomDTO;
import com.dbc.vemser.pokestore.dto.PedidoCreateDTO;
import com.dbc.vemser.pokestore.dto.PedidoDTO;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.PedidoRepository;
import com.dbc.vemser.pokestore.entity.Cupom;
import com.dbc.vemser.pokestore.entity.Pedido;
import com.dbc.vemser.pokestore.entity.ProdutoPedido;
import com.dbc.vemser.pokestore.repository.ProdutoPedidoRepository;
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
    private final PedidoRepository pedidoRepository;

    private final ProdutoPedidoRepository produtoPedidoRepository;

    private final ObjectMapper objectMapper;


    // criação de um objeto
    public PedidoDTO adicionarPedido(PedidoCreateDTO pedido) throws BancoDeDadosException, RegraDeNegocioException { // vai adicionar tudo dentro da tabela N para N (Pedido_Produto)
        Pedido pedidoEntity = objectMapper.convertValue(pedido, Pedido.class);
        for (ProdutoPedido produtoPedido : pedidoEntity.getProdutosPedido()) {
            produtoPedido.setPedido(pedidoEntity);
            produtoPedidoRepository.adicionar(produtoPedido);
        }
        PedidoDTO pedidoDTO = objectMapper.convertValue(pedidoRepository.adicionar(pedidoEntity), PedidoDTO.class);

        System.out.println("Pedido adicionado com sucesso! " + pedidoEntity);
            return pedidoDTO;
    }

    //Calculo valor final
    public double calcularValorFinal(Pedido pedido) { // mudar para service
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

}
