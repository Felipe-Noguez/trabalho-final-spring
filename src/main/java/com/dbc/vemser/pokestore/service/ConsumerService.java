package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.CupomCreateDTO;
import com.dbc.vemser.pokestore.dto.CupomDTO;
import com.dbc.vemser.pokestore.dto.TopicoCupomDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {
    private final ObjectMapper objectMapper;
    private final CupomService cupomService;
    private final EmailService emailService;

    @KafkaListener(
            clientIdPrefix = "${spring.kafka.consumer.client-id}",
            groupId = "${spring.kafka.consumer.group-id}",
            topicPartitions = {@TopicPartition(topic = "${kafka.topic}", partitions = {"0"})}
    )
    public void consumirCupom(@Payload String mensagem) throws JsonProcessingException  {
        TopicoCupomDto topicoCupomDto = objectMapper.readValue(mensagem, TopicoCupomDto.class);
        CupomCreateDTO cupomCreateDTO = objectMapper.convertValue(topicoCupomDto, CupomCreateDTO.class);
        CupomDTO cupomDTO = cupomService.adicionarCupom(cupomCreateDTO);
        emailService.sendEmailCupom(topicoCupomDto.getEmail(), cupomDTO);
    }
}
