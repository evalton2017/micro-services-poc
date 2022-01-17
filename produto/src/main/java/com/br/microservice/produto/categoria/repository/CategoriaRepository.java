package com.br.microservice.produto.categoria.repository;

import com.br.microservice.produto.categoria.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByDescricaoIgnoreCaseContaining(String descricao);
}
