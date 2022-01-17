package com.br.microservice.produto.fornecedor.controller;

import com.br.microservice.produto.categoria.dto.CategoriaRequest;
import com.br.microservice.produto.categoria.dto.CategoriaResponse;
import com.br.microservice.produto.categoria.service.CategoriaService;
import com.br.microservice.produto.config.exception.SuccessResponse;
import com.br.microservice.produto.fornecedor.dto.FornecedorRequest;
import com.br.microservice.produto.fornecedor.dto.FornecedorResponse;
import com.br.microservice.produto.fornecedor.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedor")
public class ForncedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @PostMapping
    public FornecedorResponse save(@RequestBody FornecedorRequest request){
        return fornecedorService.save(request);
    }

    @PutMapping
    public FornecedorResponse save(@RequestBody FornecedorRequest request, @PathVariable Long id){
        return fornecedorService.update(request, id);
    }

    @GetMapping
    public List<FornecedorResponse> listarCategorias(){
        return fornecedorService.listarFornecedor();
    }

    @GetMapping("{id}")
    public List<FornecedorResponse> listarCategoriasPorId(@PathVariable Long id){
        return fornecedorService.listarPorId(id);
    }

    @GetMapping("busca/{nome}")
    public List<FornecedorResponse> listarCategoriasPorDescricao(@PathVariable String nome){
        return fornecedorService.listarPorNome(nome);
    }

    @DeleteMapping("{id}")
    public SuccessResponse deletarCategoria(@PathVariable Long id){
        return fornecedorService.delete(id);
    }
}
