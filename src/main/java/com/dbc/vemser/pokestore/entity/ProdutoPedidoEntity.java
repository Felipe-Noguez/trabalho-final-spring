package com.dbc.vemser.pokestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "PRODUTO_PEDIDO")
public class ProdutoPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUTO_PEDIDO_SEQ")
    @SequenceGenerator(name = "PRODUTO_PEDIDO_SEQ", sequenceName = "SEQ_PRODUTO_PEDIDO", allocationSize = 1)
    @Column(name = "id_produto_pedido")
    private Integer idProdutoPedido;

    @Column(name = "id_produto", insertable = false, updatable = false)
    private Integer idProduto;

    @Column(name = "id_pedido", insertable = false, updatable = false)
    private Integer idPedido;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "valor")
    private Double valor;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", referencedColumnName = "id_produto")
    private ProdutoEntity produto;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", referencedColumnName = "id_pedido")
    private PedidoEntity pedido;

}
