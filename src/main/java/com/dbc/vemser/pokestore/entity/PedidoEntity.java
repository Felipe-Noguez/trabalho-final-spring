package com.dbc.vemser.pokestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "Pedido")
public class PedidoEntity {

    //nao apagar
//    private List<ProdutoPedidoEntity> produtosPedido = new ArrayList<>();
//    private CupomEntity cupom;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PEDIDO_SEQ")
    @SequenceGenerator(name = "PEDIDO_SEQ", sequenceName = "SEQ_PEDIDO", allocationSize = 1)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @Column(name = "id_cupom")
    private Integer idCupom;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "valor_final")
    private double valorFinal;

    @Column(name = "deletado")
    private String deletado = "F";

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
    private Set<ProdutoPedidoEntity> produtosPedidos;
}
