package com.br.microservice.produto.produto.dto;

import lombok.Data;

@Data
public class ProdutoRequest {

    private Long id;
    private String nome;
    private Integer quantidade;
    private Long categoria;
    private Long fornecedor;


}
