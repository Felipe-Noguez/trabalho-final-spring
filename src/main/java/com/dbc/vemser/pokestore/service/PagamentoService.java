package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.PagamentoCreateDTO;
import com.dbc.vemser.pokestore.dto.PagamentoDTO;
import com.dbc.vemser.pokestore.dto.TopicoCupomDto;
import com.dbc.vemser.pokestore.entity.PagamentoEntity;
import com.dbc.vemser.pokestore.entity.PedidoEntity;
import com.dbc.vemser.pokestore.entity.UsuarioEntity;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.PagamentoRepository;
import com.dbc.vemser.pokestore.repository.PedidoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final UsuarioService usuarioService;
    private final ProducerService producerService;
    private final ObjectMapper objectMapper;

    private static final String NOME_CUPOM = "Gold gratis com os geekers";
    private static final Double PRECO = null;


    public PagamentoDTO criarPagamento(PagamentoCreateDTO dto) throws RegraDeNegocioException, JsonProcessingException {
        PedidoEntity pedido = pedidoRepository.getReferenceById(dto.getPedidoId());
        UsuarioEntity usuario = usuarioService.findById(pedido.getIdUsuario());

        PagamentoEntity pagamentoEntity = objectMapper.convertValue(dto, PagamentoEntity.class);
        pagamentoEntity.setDataPagamento(LocalDate.now());
        pagamentoEntity.setValorTotal(pedido.getValorFinal());

        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(15);

        TopicoCupomDto topicoCupomDto = new TopicoCupomDto();
        topicoCupomDto.setEmail(usuario.getEmail());
        topicoCupomDto.setNome(NOME_CUPOM);
        topicoCupomDto.setPreco(PRECO);
        topicoCupomDto.setDataVencimento(localDate);

        producerService.mensagemFactory(topicoCupomDto);

        pagamentoEntity = pagamentoRepository.save(pagamentoEntity);
        return objectMapper.convertValue(pagamentoEntity, PagamentoDTO.class);
    }

    public List<PagamentoDTO> listarPagamentos(){
        return pagamentoRepository.findAll().stream().map(x -> objectMapper.convertValue(x, PagamentoDTO.class)).toList();
    }

    public String mostrarMediaVendas(LocalDate dataInicio, LocalDate dataFim){

        Double media = pagamentoRepository.findAllDataPagamentoBetween(dataInicio, dataFim).getValorTotal();
        return "A média de vendas entre esse meses foi de: R$ "+ media;
    }

    public List<PagamentoDTO> listarPagamentosMaiorQue(Double valor){
        return pagamentoRepository.findAllPorPrecoMaiorQue(valor).stream().map(x -> objectMapper.convertValue(x, PagamentoDTO.class)).toList();
    }
}
