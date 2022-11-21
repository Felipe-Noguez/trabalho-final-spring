package com.dbc.vemser.pokestore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDate;

@Document(collection = "avaliacao_produto")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvaliacaoProdutoEntity {

    @Id
    private String idAvaliacao;
    private Integer idProduto;
    private String nomeUsuario;
    private String nomeProduto;
    private String avaliacaoProduto;
    private Double nota;
    private LocalDate dataAvaliacao;

}
