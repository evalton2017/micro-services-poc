package com.br.microservice.produto.produto.controller;


import com.br.microservice.produto.config.exception.SuccessResponse;
import com.br.microservice.produto.produto.dto.ProdutoEstoqueRequest;
import com.br.microservice.produto.produto.dto.ProdutoRequest;
import com.br.microservice.produto.produto.dto.ProdutoResponse;
import com.br.microservice.produto.produto.dto.ProdutoVendasResponse;
import com.br.microservice.produto.produto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @PostMapping
    public ProdutoResponse save(@RequestBody ProdutoRequest request){
        return produtoService.save(request);
    }

    @PutMapping("{id}")
    public ProdutoResponse save(@RequestBody ProdutoRequest request, @PathVariable Long id){
        return produtoService.update(request, id);
    }

    @GetMapping
    public List<ProdutoResponse> listarCategorias(){
        return produtoService.listarFornecedor();
    }

    @GetMapping("{id}")
    public ProdutoResponse listarPorId(@PathVariable Long id){
        return produtoService.consultaPorId(id);
    }

    @GetMapping("categoria/{id}")
    public List<ProdutoResponse> listarPorCategoria(@PathVariable Long id){
        return produtoService.listarPorCategoria(id);
    }

    @GetMapping("fornecedor/{id}")
    public List<ProdutoResponse> listarPorFornecedor(@PathVariable Long id){
        return produtoService.listarPorFornecedor(id);
    }

    @GetMapping("busca/{nome}")
    public List<ProdutoResponse> listarCategoriasPorNome(@PathVariable String nome){
        return produtoService.listarPorNome(nome);
    }

    @DeleteMapping("{id}")
    public SuccessResponse deletarCategoria(@PathVariable Long id){
        return produtoService.delete(id);
    }

    @PostMapping("verifica-estoque")
    public SuccessResponse veriricaEstoque(@RequestBody ProdutoEstoqueRequest request){
        return produtoService.verificaEstoqueProduto(request);
    }

    @GetMapping("{produtoId}/vendas")
    public ProdutoVendasResponse buscaVendasProduto(@PathVariable Long id){
        return produtoService.buscaVendasProduto(id);
    }

}
