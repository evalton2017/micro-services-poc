package com.br.microservice.produto.fornecedor.repository;

import com.br.microservice.produto.fornecedor.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    List<Fornecedor> findByNomeIgnoreCaseContaining(String nome);
}
