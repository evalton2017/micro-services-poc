package com.br.microservice.produto.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEstoqueRequest {

    List<ProdutoQuantidateDto> produtos;
}
