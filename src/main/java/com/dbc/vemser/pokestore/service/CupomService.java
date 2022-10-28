package com.dbc.vemser.pokestore.service;


import com.dbc.vemser.pokestore.dto.CupomCreateDTO;
import com.dbc.vemser.pokestore.dto.CupomDTO;
import com.dbc.vemser.pokestore.entity.Cupom;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
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
    public CupomDTO adicionarCupom(CupomCreateDTO cupom) throws RegraDeNegocioException {
        Cupom cupomEntity = objectMapper.convertValue(cupom, Cupom.class);
        CupomDTO cupomDTO = null;
        try {
            cupomDTO = objectMapper.convertValue(cupomRepository.adicionar(cupomEntity), CupomDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao adicionar o cupom ao banco de dados!");
        }
        System.out.println("Cupom adicionado com sucesso! " + cupomEntity);
            return cupomDTO;
    }

    // remoção
    public void removerCupom(Integer id) throws RegraDeNegocioException {
        boolean conseguiuRemover = false;
        try {
            conseguiuRemover = cupomRepository.remover(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao remover o cupom ao banco de dados!");
        }
        System.out.println("Cupom removido? " + conseguiuRemover + "| com id=" + id);
    }

    // atualização de um objeto
    public CupomDTO editarCupom(Integer id, CupomCreateDTO cupom) throws RegraDeNegocioException {
        try {
                cupomRepository.findById(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao editar no banco de dados!");
        }

        Cupom cupomEntity = objectMapper.convertValue(cupom, Cupom.class);

        Cupom editado = null;

        try {
            editado = cupomRepository.editarCupom(id, cupomEntity);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao editar o cupom!");
        }
        editado.setIdCupom(id);

        log.info("Cupom editado!");
        return objectMapper.convertValue(editado, CupomDTO.class);
    }

    // leitura
    public List<CupomDTO> listarCupons() throws RegraDeNegocioException {
        try {
            return cupomRepository.listar().stream()
                    .map(cupom -> objectMapper.convertValue(cupom, CupomDTO.class))
                    .toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao listas os cupons do banco de dados!");
        }
    }
    public CupomDTO findById(Integer id) throws RegraDeNegocioException{
        Cupom cupom = null;
        try {
            cupom = cupomRepository.findById(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Impossível encontrar o ID do cupom no banco de dados!");
        }
        if(cupom == null){
            throw new RegraDeNegocioException("Cupom não encontrado");
        }
        log.info("Cupom encontrado!!");
        CupomDTO cupomDTO = objectMapper.convertValue(cupom, CupomDTO.class);
        return cupomDTO;
    }

}
