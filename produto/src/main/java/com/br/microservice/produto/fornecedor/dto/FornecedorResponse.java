package com.br.microservice.produto.fornecedor.dto;

import com.br.microservice.produto.fornecedor.model.Fornecedor;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class FornecedorResponse {

    private Long id;

    private String nome;

    public static FornecedorResponse of(Fornecedor fornecedor){
        var response = new FornecedorResponse();
        BeanUtils.copyProperties(fornecedor, response);
        return response;
    }
}
