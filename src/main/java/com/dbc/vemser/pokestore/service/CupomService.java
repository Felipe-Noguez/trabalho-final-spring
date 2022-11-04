package com.dbc.vemser.pokestore.service;


import com.dbc.vemser.pokestore.dto.CupomCreateDTO;
import com.dbc.vemser.pokestore.dto.CupomDTO;
import com.dbc.vemser.pokestore.entity.CupomEntity;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.CupomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CupomService {
    private final CupomRepository cupomRepository;
    private final ObjectMapper objectMapper;

    // criação de um objeto
    public CupomDTO adicionarCupom(CupomCreateDTO cupom) {
        CupomEntity cupomEntity = objectMapper.convertValue(cupom, CupomEntity.class);

        return objectMapper.convertValue(
                cupomRepository.save(cupomEntity), CupomDTO.class);
    }

    // remoção
    public void removerCupom(Integer id) throws RegraDeNegocioException {
        CupomEntity cupomEntity = findById(id);
        cupomRepository.delete(cupomEntity);
    }

    // atualização de um objeto
    public CupomDTO editarCupom(Integer id, CupomCreateDTO cupomAtualiazar) {

        cupomRepository.findById(id);
        CupomEntity cupomEntity = objectMapper.convertValue(cupomAtualiazar, CupomEntity.class);

        cupomEntity = cupomRepository.save(cupomEntity);
        return objectMapper.convertValue(cupomEntity, CupomDTO.class);
    }

    // leitura
    public List<CupomDTO> listarCupons(){

        return cupomRepository.findAll().stream()
                .map(cupom -> objectMapper.convertValue(cupom, CupomDTO.class))
                .toList();
    }

    public CupomEntity findById(Integer id) throws RegraDeNegocioException{

        return cupomRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Cupom não listado."));

    }

}
