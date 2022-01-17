package com.br.microservice.produto.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEstoqueDto {

    private String vendasId;
    private List<ProdutoQuantidateDto> produtos;
}
