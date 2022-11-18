package com.dbc.vemser.pokestore.repository;

import com.dbc.vemser.pokestore.entity.PagamentoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagamentoRepository extends MongoRepository<PagamentoEntity, Integer> {


//    @Aggregation(pipeline = {
//            "{$match: { 'dataPagamento': { $lte: ?1 ,$gt: ?0 } , 'status': 'PAGO' }}",
//            "{$group: { _id: '$status' , valorTotal: { $avg: $valorTotal }}}"
//    })
//    Document findAllDataPagamentoBetween(LocalDate dataInicio, LocalDate dataFim);

    @Query("'dataPagamento': { $gt: ?0, $lte: ?1 }")
    List<PagamentoEntity> findAllDataPagamentoBetween(LocalDate dataInicio, LocalDate dataFim);
}
