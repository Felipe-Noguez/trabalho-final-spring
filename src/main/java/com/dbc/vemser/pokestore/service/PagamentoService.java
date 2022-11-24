package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.PagamentoCreateDTO;
import com.dbc.vemser.pokestore.dto.PagamentoDTO;
import com.dbc.vemser.pokestore.dto.TopicoCupomDto;
import com.dbc.vemser.pokestore.entity.PagamentoEntity;
import com.dbc.vemser.pokestore.entity.PedidoEntity;
import com.dbc.vemser.pokestore.entity.UsuarioEntity;
import com.dbc.vemser.pokestore.enums.StatusPagamento;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.PagamentoRepository;
import com.dbc.vemser.pokestore.repository.PedidoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    @Value("${email.admin}")
    String emailAdmin;

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final UsuarioService usuarioService;
    private final ProducerService producerService;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @Value("${kafka.nome.cupom}")
    private String nomeCupom;
    @Value("${kafka.preco.cupom}")
    private Double preco;
    private final LocalDate DATA_RELATORIO = LocalDate.now().minusDays(7);
    private static final String TIME_ZONE = "America/Sao_Paulo";


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
        topicoCupomDto.setNome(nomeCupom);
        topicoCupomDto.setPreco(preco);
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

    @Scheduled(cron = "0 0 22 * * FRI", zone = TIME_ZONE)
    public void gerarRelatorioVendasSemanal() {
        List<PagamentoEntity> listRelatorio = pagamentoRepository.buscarPagamentoUltimaSemana(DATA_RELATORIO);
        double totalPagos = listRelatorio.stream()
                .filter(x-> x.getStatus().equals(StatusPagamento.PAGO))
                .mapToDouble(PagamentoEntity::getValorTotal)
                .sum();

        double totalCancelado = listRelatorio.stream()
                .filter(x -> x.getStatus().equals(StatusPagamento.CANCELADO))
                .mapToDouble(PagamentoEntity::getValorTotal)
                .sum();

        double totalPendente = listRelatorio.stream()
                .filter(x -> x.getStatus().equals(StatusPagamento.PENDENTE))
                .mapToDouble(PagamentoEntity::getValorTotal)
                .sum();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Total de pedidos pagos: R$"+totalPagos+"<br>");
        stringBuilder.append("Total de pedidos pendentes: R$"+totalPendente+"<br>");
        stringBuilder.append("Total de pedidos cancelados: R$"+totalCancelado+"<br>");

        emailService.sendRelatorioSemanal(stringBuilder.toString(), emailAdmin);
    }
}
