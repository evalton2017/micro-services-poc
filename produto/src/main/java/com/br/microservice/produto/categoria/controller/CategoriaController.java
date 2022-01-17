package com.br.microservice.produto.categoria.controller;

import com.br.microservice.produto.categoria.dto.CategoriaRequest;
import com.br.microservice.produto.categoria.dto.CategoriaResponse;
import com.br.microservice.produto.categoria.service.CategoriaService;
import com.br.microservice.produto.config.exception.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public CategoriaResponse salvar(@RequestBody CategoriaRequest request){
        return categoriaService.save(request);
    }

    @PutMapping("{id}")
    public CategoriaResponse alterar(@RequestBody CategoriaRequest request, @PathVariable Long id){
        return categoriaService.update(request, id);
    }

    @GetMapping
    public List<CategoriaResponse> listarCategorias(){
        return categoriaService.listarCategoria();
    }

    @GetMapping("{id}")
    public List<CategoriaResponse> listarCategoriasPorId(@PathVariable Long id){
        return categoriaService.listarPorId(id);
    }

    @GetMapping("descricao/{descricao}")
    public List<CategoriaResponse> listarCategoriasPorDescricao(@PathVariable String descricao){
        return categoriaService.listarPorDescricao(descricao);
    }

    @DeleteMapping("{id}")
    public SuccessResponse deletarCategoria(@PathVariable Long id){
        return categoriaService.delete(id);
    }


}
