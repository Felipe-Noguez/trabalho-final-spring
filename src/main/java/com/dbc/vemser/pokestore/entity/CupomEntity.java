package com.dbc.vemser.pokestore.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "CUPOM")
public class CupomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUPOM_SEQ")
    @SequenceGenerator(name = "CUPOM_SEQ", sequenceName = "SEQ_CUPOM", allocationSize = 1)
    @Column(name = "id_cupom")
    private Integer idCupom;

    @Column(name = "desconto")
    private Double valor;

    @Column(name = "deletado")
    private String deletado = "F";

    @JsonIgnore
    @OneToMany(mappedBy = "cupom", fetch = FetchType.LAZY)
    private Set<PedidoEntity> pedidos;
}
