package com.br.microservice.produto.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoQuantidateDto {

    private Long produtoId;
    private Integer quantidade;
}
