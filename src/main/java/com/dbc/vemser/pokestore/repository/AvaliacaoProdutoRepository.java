package com.dbc.vemser.pokestore.repository;

import com.dbc.vemser.pokestore.entity.AvaliacaoProdutoEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoProdutoRepository extends MongoRepository<AvaliacaoProdutoEntity, Integer> {

    @Aggregation(pipeline = {
            "{ '$match': { 'nota': { $gte: ?0 } } }"
    })
    List<AvaliacaoProdutoEntity> aggPorNota(Double nota);
}
