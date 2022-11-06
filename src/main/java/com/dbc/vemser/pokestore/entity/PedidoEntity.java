package com.dbc.vemser.pokestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "PEDIDO")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PEDIDO_SEQ")
    @SequenceGenerator(name = "PEDIDO_SEQ", sequenceName = "SEQ_PEDIDO", allocationSize = 1)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @Column(name = "id_cupom", insertable = false, updatable = false)
    private Integer idCupom;

    @Column(name = "id_usuario", insertable = false, updatable = false)
    private Integer idUsuario;

    @Column(name = "valor_final")
    private Double valorFinal = 0.0;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private UsuarioEntity usuario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cupom", referencedColumnName = "id_cupom")
    private CupomEntity cupom;

    @JsonIgnore
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
    private Set<ProdutoPedidoEntity> produtosPedidos = new HashSet<>();
}
