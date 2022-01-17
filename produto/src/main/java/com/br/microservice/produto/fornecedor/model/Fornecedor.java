package com.br.microservice.produto.fornecedor.model;

import com.br.microservice.produto.categoria.dto.CategoriaRequest;
import com.br.microservice.produto.categoria.model.Categoria;
import com.br.microservice.produto.fornecedor.dto.FornecedorRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="nome", nullable = false)
    private String nome;

    public static Fornecedor of(FornecedorRequest request){
        var fornecedor = new Fornecedor();
        BeanUtils.copyProperties(request, fornecedor);
        return fornecedor;
    }
}
