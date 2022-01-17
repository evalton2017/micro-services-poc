package com.br.microservice.produto.categoria.dto;

import com.br.microservice.produto.categoria.model.Categoria;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CategoriaRequest {

    private Long id;
    private  String descricao;

    public static CategoriaRequest of(Categoria categoria){
        var request = new CategoriaRequest();
        BeanUtils.copyProperties(categoria, request);
        return request;
    }

}
