package com.br.microservice.produto.categoria.service;

import com.br.microservice.produto.config.exception.SuccessResponse;
import com.br.microservice.produto.config.exception.ValidationException;
import com.br.microservice.produto.categoria.dto.CategoriaRequest;
import com.br.microservice.produto.categoria.dto.CategoriaResponse;
import com.br.microservice.produto.categoria.model.Categoria;
import com.br.microservice.produto.categoria.repository.CategoriaRepository;
import com.br.microservice.produto.produto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    ProdutoService produtoService;

    @Autowired
    public CategoriaService(@Lazy ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    public CategoriaResponse save(CategoriaRequest request){
        validateCatoriaNameInformed(request);
        var categoria = categoriaRepository.save(Categoria.of(request));
        return CategoriaResponse.of(categoria);

    }

    public CategoriaResponse update(CategoriaRequest request, Long id){
        validateCatoriaNameInformed(request);
        var categoria = Categoria.of(request);
        categoria.setId(id);
        categoriaRepository.save(Categoria.of(request));
        return CategoriaResponse.of(categoria);
    }


    public Categoria findById(Long id){
        return categoriaRepository.findById(id)
                .orElseThrow(()-> new ValidationException("Categoria não encontrada."));
    }

    public List<CategoriaResponse> listarCategoria(){
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaResponse::of)
                .collect(Collectors.toList());
    }

    public List<CategoriaResponse> listarPorId(Long id){
        return categoriaRepository.findById(id)
                .stream()
                .map(CategoriaResponse::of)
                .collect(Collectors.toList());
    }

    public List<CategoriaResponse> listarPorDescricao(String descricao){
        if(descricao.isEmpty()){
            throw  new ValidationException("Descrição é obrigatorio");
        }
        return categoriaRepository.findByDescricaoIgnoreCaseContaining(descricao)
                .stream()
                .map(CategoriaResponse::of)
                .collect(Collectors.toList());
    }

    private void validateCatoriaNameInformed(CategoriaRequest request){
        if(ObjectUtils.isEmpty(request.getDescricao())){
            throw new ValidationException("Descrição da Categoria é Obrigadtorio");
        }
    }

    public SuccessResponse delete(Long id){
        validateId(id);
        if(produtoService.existeCategoria(id)){
            throw  new ValidationException("Categoria possui vinculo a um produto");
        }

        categoriaRepository.deleteById(id);
        return SuccessResponse.create("Categoria deletedo");
    }

    private void validateId(Long id){
        if(ObjectUtils.isEmpty(id)){
            throw  new ValidationException("Id é obrigatorio");
        }
    }

}
