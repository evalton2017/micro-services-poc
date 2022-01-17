package com.br.microservice.produto.categoria.dto;

import com.br.microservice.produto.categoria.model.Categoria;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CategoriaResponse {

    private Long id;

    private String descricao;

    public static CategoriaResponse of(Categoria categoria){
        var response = new CategoriaResponse();
        BeanUtils.copyProperties(categoria, response);
        return response;
    }
}
