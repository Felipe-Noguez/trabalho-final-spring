package com.dbc.vemser.pokestore.repository;


import com.dbc.vemser.pokestore.entity.ProdutoPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedidoEntity, Integer> {

}
