package com.br.microservice.produto.produto.repository;

import com.br.microservice.produto.produto.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByNomeIgnoreCaseContaining(String nome);

    List<Produto> findByCategoriaId(Long id);

    List<Produto> findByFornecedorId(Long id);

    Boolean existsByCategoriaId(Long id);

    Boolean existsByFornecedorId(Long id);


}
