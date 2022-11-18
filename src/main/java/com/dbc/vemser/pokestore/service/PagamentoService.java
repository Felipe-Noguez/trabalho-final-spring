package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.PagamentoCreateDTO;
import com.dbc.vemser.pokestore.dto.PagamentoDTO;
import com.dbc.vemser.pokestore.entity.PagamentoEntity;
import com.dbc.vemser.pokestore.entity.PedidoEntity;
import com.dbc.vemser.pokestore.enums.StatusPagamento;
import com.dbc.vemser.pokestore.repository.PagamentoRepository;
import com.dbc.vemser.pokestore.repository.PedidoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final ObjectMapper objectMapper;

    public PagamentoDTO criarPagamento(PagamentoCreateDTO dto){
        PedidoEntity pedido = pedidoRepository.getReferenceById(dto.getPedidoId());

        PagamentoEntity pagamentoEntity = objectMapper.convertValue(dto, PagamentoEntity.class);
        pagamentoEntity.setDataPagamento(LocalDate.now());
        pagamentoEntity.setValorTotal(pedido.getValorFinal());

        pagamentoEntity = pagamentoRepository.save(pagamentoEntity);
        return objectMapper.convertValue(pagamentoEntity, PagamentoDTO.class);
    }

    public List<PagamentoDTO> listarPagamentos(){
        return pagamentoRepository.findAll().stream().map(x -> objectMapper.convertValue(x, PagamentoDTO.class)).toList();
    }

    public String mostrarMediaVendas(LocalDate dataInicio, LocalDate dataFim){

        Double media = pagamentoRepository.findAllDataPagamentoBetween(dataInicio, dataFim).stream()
                .filter(x -> x.getStatus().equals(StatusPagamento.PAGO))
                .mapToDouble(PagamentoEntity::getValorTotal)
                .average()
                .getAsDouble();

        return "A média de vendas entre esse meses foi de: R$ "+ media;
    }
}
