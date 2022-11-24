package com.dbc.vemser.pokestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "CUPOM")
public class CupomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUPOM_SEQ")
    @SequenceGenerator(name = "CUPOM_SEQ", sequenceName = "SEQ_CUPOM", allocationSize = 1)
    @Column(name = "id_cupom")
    private Integer idCupom;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @JsonIgnore
    @OneToMany(mappedBy = "cupom", fetch = FetchType.LAZY)
    private Set<PedidoEntity> pedidos;
}
