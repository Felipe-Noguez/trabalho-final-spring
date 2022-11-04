package com.dbc.vemser.pokestore.entity;

import com.dbc.vemser.pokestore.enums.Tipos;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "Produto")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUTO_SEQ")
    @SequenceGenerator(name = "PRODUTO_SEQ", sequenceName = "SEQ_PRODUTO", allocationSize = 1)
    @Column(name = "id_produto")
    private Integer idProduto;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "tipo")
    protected Tipos tipo;

    @Column(name = "valor")
    private double valor;

    @Column(name = "idUsuario")
    private int idUsuario;

    @Column(name = "deletado")
    private String deletado = "F";

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private UsuarioEntity usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
    private Set<ProdutoPedidoEntity> produtosPedidos;

}
