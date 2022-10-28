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
    public CupomDTO adicionarCupom(CupomCreateDTO cupom) throws BancoDeDadosException, RegraDeNegocioException{
            Cupom cupomEntity = objectMapper.convertValue(cupom, Cupom.class);
            CupomDTO cupomDTO = objectMapper.convertValue(cupomRepository.adicionar(cupomEntity), CupomDTO.class);
            System.out.println("Cupom adicionado com sucesso! " + cupomEntity);
            return cupomDTO;
    }

    // remoção
    public void removerCupom(Integer id) throws BancoDeDadosException {
            boolean conseguiuRemover = cupomRepository.remover(id);
            System.out.println("Cupom removido? " + conseguiuRemover + "| com id=" + id);
    }

    // atualização de um objeto
    public CupomDTO editarCupom(Integer id, CupomCreateDTO cupom) throws BancoDeDadosException, RegraDeNegocioException {

        if(cupomRepository.findById(id) == null){
            throw new RegraDeNegocioException("Cupom não encontrado!");
        }

        Cupom cupomEntity = objectMapper.convertValue(cupom, Cupom.class);

        Cupom editado = cupomRepository.editarCupom(id, cupomEntity);

        log.info("Cupom editado!");
        return objectMapper.convertValue(editado, CupomDTO.class);
    }

    // leitura
    public List<CupomDTO> listarCupons() throws RegraDeNegocioException, BancoDeDadosException {
            return cupomRepository.listar().stream()
                    .map(cupom -> objectMapper.convertValue(cupom, CupomDTO.class))
                    .toList();
}
    public CupomDTO findById(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        Cupom cupom = cupomRepository.findById(id);
        if(cupom == null){
            throw new RegraDeNegocioException("Cupom não encontrado");
        }
        log.info("Cupom encontrado!!");
        CupomDTO cupomDTO = objectMapper.convertValue(cupom, CupomDTO.class);
        return cupomDTO;
    }

}
