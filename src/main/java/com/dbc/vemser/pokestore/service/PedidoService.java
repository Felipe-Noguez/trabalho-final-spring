package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.*;
import com.dbc.vemser.pokestore.entity.*;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.PedidoRepository;
import com.dbc.vemser.pokestore.repository.ProdutoPedidoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final CupomService cupomService;
    private final ProdutoService produtoService;
    private final UsuarioService usuarioService;
    private final PedidoRepository pedidoRepository;

    private final ProdutoPedidoRepository produtoPedidoRepository;
    private final ObjectMapper objectMapper;

    // criação de um objeto
    public PedidoDTO adicionarPedido(PedidoCreateDTO pedido) throws RegraDeNegocioException { // vai adicionar tudo dentro da tabela N para N (Pedido_Produto)

        CupomEntity cupomEntity = getCupomEntity(pedido);
        UsuarioEntity usuario = usuarioService.findById(pedido.getIdUsuario());

        PedidoEntity pedidoEntity = objectMapper.convertValue(pedido, PedidoEntity.class);
        pedidoEntity.setCupom(cupomEntity);
        pedidoEntity.setIdUsuario(usuario.getIdUsuario());

        adicionarPedido(pedido, pedidoEntity);

        if (cupomEntity != null) {
            pedidoEntity.setValorFinal(pedidoEntity.getValorFinal() - cupomEntity.getValor());
        }

        return salvarPedido(pedidoEntity);
    }

    // atualização de um objeto
    public PedidoDTO editarPedido(Integer id, PedidoCreateDTO pedidoAtualizar) throws RegraDeNegocioException {

        PedidoEntity pedidoEntity = findById(id);
        CupomEntity cupomEntity = getCupomEntity(pedidoAtualizar);
        UsuarioEntity usuario = usuarioService.findById(pedidoAtualizar.getIdUsuario());

        pedidoEntity = objectMapper.convertValue(pedidoAtualizar, PedidoEntity.class);
        pedidoEntity.setIdUsuario(usuario.getIdUsuario());
        pedidoEntity.setIdPedido(id);
        pedidoEntity.setCupom(cupomEntity);
        pedidoEntity.getProdutosPedidos().clear();

        adicionarPedido(pedidoAtualizar, pedidoEntity);

        if (cupomEntity != null) {
            pedidoEntity.setValorFinal(pedidoEntity.getValorFinal() - cupomEntity.getValor());
        }

        return salvarPedido(pedidoEntity);
    }

    public List<PedidoDTO> listarPedido() throws RegraDeNegocioException {
        return pedidoRepository.findAll().stream()
                .map(pedido -> objectMapper.convertValue(pedido, PedidoDTO.class))
                .toList();
    }
    // remoção
    public void removerPedido(Integer id) throws RegraDeNegocioException {
        PedidoEntity pedidoEntity = findById(id);
        pedidoRepository.delete(pedidoEntity);
    }

    public PedidoEntity findById(Integer id) throws RegraDeNegocioException {
        return pedidoRepository.findById(id).orElseThrow(
                () -> new RegraDeNegocioException("Pedido não encontrado!"));
    }

    //------------------ Metodos Privados ------------------

    private CupomEntity getCupomEntity(PedidoCreateDTO pedido) throws RegraDeNegocioException {
        if (pedido.getIdCupom() != null) {
            return cupomService.findById(pedido.getIdCupom());
        }else {
            return null;
        }
    }

    private void adicionarPedido(PedidoCreateDTO pedido, PedidoEntity pedidoEntity) throws RegraDeNegocioException {
        for (ProdutoIdQuantidadeCreateDTO item : pedido.getProdutosDTO()) {
            ProdutoEntity produtoEntity = produtoService.findById(item.getIdProduto());

            //adicionar um produtoPedido na lista do pedidoEntity -> List<pedidoProdutoEntity>
            ProdutoPedidoEntity produtoPedido = new ProdutoPedidoEntity();
            produtoPedido.setProduto(produtoEntity);
            produtoPedido.setQuantidade(item.getQuantidade());
            produtoPedido.setPedido(pedidoEntity);
            produtoPedido.setValor(produtoEntity.getValor() * item.getQuantidade());

            pedidoEntity.getProdutosPedidos().add(produtoPedido);
            pedidoEntity.setValorFinal(pedidoEntity.getValorFinal() + produtoPedido.getValor());
        }
    }

    @NotNull
    private List<ProdutoPedidoDTO> getProdutoPedidoDTOList(PedidoEntity pedidoEntity) {
        return pedidoEntity.getProdutosPedidos().stream()
                .map(item -> {
                    ProdutoPedidoDTO produtoPedidoDTO = objectMapper.convertValue(item, ProdutoPedidoDTO.class);
                    produtoPedidoDTO.setProduto(objectMapper.convertValue(item.getProduto(), ProdutoDTO.class));
                    produtoPedidoDTO.setPedido(objectMapper.convertValue(item.getPedido(), PedidoDTO.class));

                    return produtoPedidoDTO;
                }).toList();
    }

    @NotNull
    private PedidoDTO salvarPedido(PedidoEntity pedidoEntity) {

        pedidoEntity = pedidoRepository.save(pedidoEntity);

        List<ProdutoPedidoDTO> produtoPedidoDTOList = getProdutoPedidoDTOList(pedidoEntity);
        PedidoDTO pedidoDTO = objectMapper.convertValue(pedidoEntity, PedidoDTO.class);
        pedidoDTO.setProdutosPedido(produtoPedidoDTOList);

        return pedidoDTO;
    }
}