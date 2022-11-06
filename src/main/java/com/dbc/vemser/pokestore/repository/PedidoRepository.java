package com.dbc.vemser.pokestore.repository;

import com.dbc.vemser.pokestore.dto.PedidoDTO;
import com.dbc.vemser.pokestore.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Integer> {

}