package com.br.microservice.produto.produto.model;

import com.br.microservice.produto.categoria.model.Categoria;
import com.br.microservice.produto.fornecedor.controller.ForncedorController;
import com.br.microservice.produto.fornecedor.model.Fornecedor;
import com.br.microservice.produto.produto.dto.ProdutoRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="nome", nullable = false)
    private String nome;

    @Column(name="quantidade")
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name ="categoria", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name ="fornecedor", nullable = false)
    private Fornecedor fornecedor;

    @Column(name ="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }

    public static Produto of(ProdutoRequest request,Fornecedor fornecedor, Categoria categoria ){
        return Produto
                .builder()
                .nome(request.getNome())
                .quantidade(request.getQuantidade())
                .fornecedor(fornecedor)
                .categoria(categoria)
                .build();
    }

    public void atualizaEstoque(Integer newQuantidade){
        this.quantidade = this.quantidade - newQuantidade;
    }


}
