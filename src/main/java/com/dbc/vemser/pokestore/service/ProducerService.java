package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.TopicoCupomDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerService {

    private static final Integer PARTICAO = 1;
    @Value("${spring.kafka.consumer.client-id}")
    private String usuario;
    @Value("${kafka.topic}")
    private String topico;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void mensagemFactory(TopicoCupomDto topicoCupomDto) throws JsonProcessingException {
        String mensagemStr = objectMapper.writeValueAsString(topicoCupomDto);

        // mensagem, chave, topico
        MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(mensagemStr)
                .setHeader(KafkaHeaders.TOPIC, topico)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                .setHeader(KafkaHeaders.PARTITION_ID, PARTICAO);

        Message<String> message = stringMessageBuilder.build();

        ListenableFuture<SendResult<String, String>> enviadoParaTopico = kafkaTemplate.send(message);
        enviadoParaTopico.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult result) {
                log.info("Cupom enviado para o kafka com o nome: {} ", topicoCupomDto.getNome());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Erro ao publicar cupom no kafka com o nome: {}", topicoCupomDto.getNome(), ex);
            }
        });
    }
}
