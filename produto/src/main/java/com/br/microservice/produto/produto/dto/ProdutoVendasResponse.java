package com.br.microservice.produto.produto.dto;

import com.br.microservice.produto.categoria.dto.CategoriaResponse;
import com.br.microservice.produto.fornecedor.dto.FornecedorResponse;
import com.br.microservice.produto.produto.model.Produto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoVendasResponse {

    private Long id;
    private String nome;
    private Integer quantidade;
    private CategoriaResponse categoria;
    private FornecedorResponse fornecedor;
    @JsonProperty("created_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createAt;
    private List<String> vendas;

    public static ProdutoVendasResponse of(Produto produto, List<String> vendas){
        return ProdutoVendasResponse
                .builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .quantidade(produto.getQuantidade())
                .createAt(produto.getCreatedAt())
                .fornecedor(FornecedorResponse.of(produto.getFornecedor()))
                .categoria(CategoriaResponse.of(produto.getCategoria()))
                .vendas(vendas)
                .build();
    }


}
