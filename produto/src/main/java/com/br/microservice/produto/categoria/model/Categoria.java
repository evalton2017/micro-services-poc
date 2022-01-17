package com.br.microservice.produto.categoria.model;

import com.br.microservice.produto.categoria.dto.CategoriaRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="descricao", nullable = false)
    private String descricao;

    public static Categoria of(CategoriaRequest request){
        var categoria = new Categoria();
        BeanUtils.copyProperties(request, categoria);
        return categoria;
    }
}
