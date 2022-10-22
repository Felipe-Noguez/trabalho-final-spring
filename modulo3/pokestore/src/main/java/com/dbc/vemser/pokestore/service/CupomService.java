package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.CupomCreateDTO;
import com.dbc.vemser.pokestore.dto.CupomDTO;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.entity.Cupom;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.CupomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

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
            Cupom cupomRecuperado = findById(id);
            cupomRepository.editar(id, cupomRecuperado);
            boolean conseguiuEditar = cupomRepository.editar(id, cupomRecuperado);
            System.out.println("Cupom editado? " + conseguiuEditar + "| com id=" + id);
            CupomDTO cupomDTO = objectMapper.convertValue(cupomRecuperado, CupomDTO.class);
            return cupomDTO;
    }

    // leitura
    public List<CupomDTO> listarCupons() throws RegraDeNegocioException, BancoDeDadosException {
            return cupomRepository.listar().stream()
                    .map(cupom -> objectMapper.convertValue(cupom, CupomDTO.class))
                    .toList();
}
    public Cupom findById(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        Cupom cupomRecuperado = cupomRepository.listar().stream()
                .filter(cupom -> cupom.getIdCupom().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Cupom não encontrado"));
        return cupomRecuperado;
    }

}
