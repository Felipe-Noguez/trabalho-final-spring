package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.PagamentoCreateDTO;
import com.dbc.vemser.pokestore.dto.PagamentoDTO;
import com.dbc.vemser.pokestore.dto.PagamentoDatasDTO;
import com.dbc.vemser.pokestore.entity.PagamentoEntity;
import com.dbc.vemser.pokestore.entity.PedidoEntity;
import com.dbc.vemser.pokestore.enums.FormaPagamento;
import com.dbc.vemser.pokestore.enums.StatusPagamento;
import com.dbc.vemser.pokestore.repository.PagamentoRepository;
import com.dbc.vemser.pokestore.repository.PedidoRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PagamentoServiceTest {

    @InjectMocks
    private PagamentoService pagamentoService;
    @Mock
    private PagamentoRepository pagamentoRepository;
    @Mock
    private PedidoRepository pedidoRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(pagamentoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCriarPagamentoComSucesso(){

        // SETUP
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setIdPedido(1);

        PagamentoCreateDTO pagamentoCreateDTO = getPagamentoCreateDTO();
        PagamentoEntity pagamentoEntity = getPagamentoEntity();

        when(pedidoRepository.getReferenceById(anyInt())).thenReturn(pedidoEntity);
        when(pagamentoRepository.save(any(PagamentoEntity.class))).thenReturn(pagamentoEntity);

        // ACT
        PagamentoDTO pagamentoDTO = pagamentoService.criarPagamento(pagamentoCreateDTO);

        // ASSERT
        assertNotNull(pagamentoDTO);
        assertEquals(250.0, pagamentoDTO.getValorTotal());
        assertEquals(LocalDate.of(2022, 10, 10), pagamentoDTO.getDataPagamento());
    }

    @Test
    public void deveTertarVistarPagamentos(){
        // (SETUP)
        List<PagamentoEntity> lista = new ArrayList<>();
        lista.add(getPagamentoEntity());
        when(pagamentoRepository.findAll()).thenReturn(lista);

        //(ACT)
        List<PagamentoDTO> listaDto = pagamentoService.listarPagamentos();

        // (ASSERT)
        assertNotNull(listaDto);
        assertTrue(listaDto.size() > 0);
        assertEquals(1, lista.size());
    }

    @Test
    public void deveTestarMostrarMediaVendas(){
        // (SETUP)
        PagamentoDatasDTO pagamentoDatasDTO = new PagamentoDatasDTO();
        pagamentoDatasDTO.setValorTotal(1250.0);
        when(pagamentoRepository.findAllDataPagamentoBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(pagamentoDatasDTO);
        //(ACT)
        String result = pagamentoService.mostrarMediaVendas(LocalDate.of(2022, 10, 10), LocalDate.of(2022, 11, 10));

        // (ASSERT)
        assertNotNull(result);
        assertEquals("A média de vendas entre esse meses foi de: R$ 1250.0", result);
    }

    @Test
    public void deveTestarlistarPagamentosMaiorQue(){
        // (SETUP)
        Double valor = 50.0;
        PagamentoEntity pagamentoEntity = getPagamentoEntity();
        List<PagamentoEntity> list = new ArrayList<>();
        list.add(pagamentoEntity);
        when(pagamentoRepository.findAllPorPrecoMaiorQue(any(Double.class))).thenReturn(list);
        //(ACT)
        List<PagamentoDTO> pagamentoDTOS = pagamentoService.listarPagamentosMaiorQue(valor);

        // (ASSERT)
        assertNotNull(pagamentoDTOS);
        assertEquals(1, pagamentoDTOS.size());
    }

    private PagamentoCreateDTO getPagamentoCreateDTO() {
        PagamentoCreateDTO pagamentoCreateDTO = new PagamentoCreateDTO();
        pagamentoCreateDTO.setFormaPagamento(FormaPagamento.PIX);
        pagamentoCreateDTO.setStatus(StatusPagamento.PAGO);
        pagamentoCreateDTO.setPedidoId(1);

        return pagamentoCreateDTO;
    }

    private PagamentoEntity getPagamentoEntity() {
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        pagamentoEntity.setDataPagamento(LocalDate.of(2022, 10, 10));
        pagamentoEntity.setValorTotal(250.0);
        pagamentoEntity.setIdPagamento("10b");
        pagamentoEntity.setFormaPagamento(FormaPagamento.PIX);
        pagamentoEntity.setStatus(StatusPagamento.PAGO);
        pagamentoEntity.setPedidoId(1);

        return pagamentoEntity;
    }
}
