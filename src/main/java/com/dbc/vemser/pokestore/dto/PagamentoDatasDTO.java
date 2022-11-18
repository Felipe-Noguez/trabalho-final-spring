package com.dbc.vemser.pokestore.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class PagamentoDatasDTO {
    @Field("_id")
    private String id;
    private Double valorTotal;
}
