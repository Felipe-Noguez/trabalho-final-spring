package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.CupomCreateDTO;
import com.dbc.vemser.pokestore.dto.CupomDTO;
import com.dbc.vemser.pokestore.entity.CupomEntity;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.repository.CupomRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CupomServiceTest {

    @InjectMocks
    private CupomService cupomService;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private CupomRepository cupomRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(cupomService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarAdicionarCupomComSucesso(){

    // SETUP
        CupomCreateDTO cupomCreateDTO = new CupomCreateDTO();

        CupomEntity cupomEntity = new CupomEntity();
        cupomEntity.setPreco(150.0);

        when(cupomRepository.save(any(CupomEntity.class))).thenReturn(cupomEntity);

    // ACT
        CupomDTO cupomDTO = cupomService.adicionarCupom(cupomCreateDTO);

    // ASSERT
        assertNotNull(cupomDTO);
        assertEquals(150.0, cupomDTO.getPreco());
    }

    @Test
    public void deveTestarRemoverCupomComSucesso() throws RegraDeNegocioException {

        // SETUP
        Integer id = 10;
        CupomEntity cupomEntity = new CupomEntity();
        cupomEntity.setIdCupom(id);

        when(cupomRepository.findById(anyInt())).thenReturn(Optional.of(cupomEntity));

        // ACT
        cupomService.removerCupom(id);

        // ASSERT
        verify(cupomRepository, times(1)).delete(any());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarRemoverCupomComFalha() throws RegraDeNegocioException {
        // SETUP
        Integer id = 10;
        when(cupomRepository.findById(anyInt())).thenReturn(Optional.empty());

        // ACT
        cupomService.removerCupom(id);

        // ASSERT
        verify(cupomRepository, times(1)).delete(any());
    }

    @Test
    public void deveTestarEditarCupomComSucesso(){

        // SETUP
        Integer id = 12;
        CupomCreateDTO cupomCreateDTO = getCupomCreateDTO();

        // findById(id);
        CupomEntity cupomEntity = getCupomEntity();
        CupomEntity cupom = getCupomEntity();
        cupom.setPreco(180.0);
        when(cupomRepository.findById(anyInt())).thenReturn(Optional.of(cupomEntity));

        when(cupomRepository.save(any())).thenReturn(cupom);

        // ACT
        CupomDTO cupomDTO = cupomService.editarCupom(id, cupomCreateDTO);

        // ASSERT
        assertNotNull(cupomDTO);
        assertNotEquals(160.0, cupomDTO.getPreco());
    }

    @Test
    public void deveTestarListComSucesso(){
        // (SETUP)
        List<CupomEntity> lista = new ArrayList<>();
        lista.add(getCupomEntity());
        when(cupomRepository.findAll()).thenReturn(lista);

        //(ACT)
        List<CupomDTO> listaDto = cupomService.listarCupons();

        // (ASSERT)
        assertNotNull(listaDto);
        assertTrue(listaDto.size() > 0);
        assertEquals(1, lista.size());
    }

    private CupomCreateDTO getCupomCreateDTO() {
        CupomCreateDTO cupomCreateDTO = new CupomCreateDTO();
        cupomCreateDTO.setPreco(120.0);

        return cupomCreateDTO;
    }

    private CupomEntity getCupomEntity() {
        CupomEntity cupomEntity = new CupomEntity();
        cupomEntity.setIdCupom(12);
        cupomEntity.setPreco(160.0);

        return cupomEntity;
    }

}
