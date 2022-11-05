package com.dbc.vemser.pokestore.repository;

import com.dbc.vemser.pokestore.entity.CupomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CupomRepository extends JpaRepository<CupomEntity, Integer> {

}