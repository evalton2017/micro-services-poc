package com.br.microservice.produto.fornecedor.dto;

import com.br.microservice.produto.categoria.model.Categoria;
import com.br.microservice.produto.fornecedor.model.Fornecedor;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class FornecedorRequest {

    private Long id;
    private  String nome;

    public static FornecedorRequest of(Fornecedor fornecedor){
        var request = new FornecedorRequest();
        BeanUtils.copyProperties(fornecedor, request);
        return request;
    }

}
