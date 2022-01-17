package com.br.microservice.produto.fornecedor.service;

import com.br.microservice.produto.config.exception.SuccessResponse;
import com.br.microservice.produto.config.exception.ValidationException;
import com.br.microservice.produto.fornecedor.dto.FornecedorRequest;
import com.br.microservice.produto.fornecedor.dto.FornecedorResponse;
import com.br.microservice.produto.fornecedor.model.Fornecedor;
import com.br.microservice.produto.fornecedor.repository.FornecedorRepository;
import com.br.microservice.produto.produto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FornecedorService {

    @Autowired
    FornecedorRepository fornecedorRepository;

    ProdutoService produtoService;

    @Autowired
    public FornecedorService(@Lazy ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    public Fornecedor findById(Long id){
        return fornecedorRepository.findById(id)
                .orElseThrow(()-> new ValidationException("Fornecedor não encontrada."));
    }

    public FornecedorResponse save(FornecedorRequest request){
        validateCatoriaNameInformed(request);
        var fornecedor = fornecedorRepository.save(Fornecedor.of(request));
        return FornecedorResponse.of(fornecedor);
    }

    public FornecedorResponse update(FornecedorRequest request, Long id){
        validateCatoriaNameInformed(request);
        var fornecedor = Fornecedor.of(request);
        fornecedor.setId(id);
        fornecedorRepository.save(Fornecedor.of(request));
        return FornecedorResponse.of(fornecedor);
    }

    public List<FornecedorResponse> listarFornecedor(){
        return fornecedorRepository.findAll()
                .stream()
                .map(FornecedorResponse::of)
                .collect(Collectors.toList());
    }

    public List<FornecedorResponse> listarPorId(Long id){
        return fornecedorRepository.findById(id)
                .stream()
                .map(FornecedorResponse::of)
                .collect(Collectors.toList());
    }

    public List<FornecedorResponse> listarPorNome(String nome){
        if(nome.isEmpty()){
            throw  new ValidationException("Nome é obrigatorio");
        }
        return fornecedorRepository.findByNomeIgnoreCaseContaining(nome)
                .stream()
                .map(FornecedorResponse::of)
                .collect(Collectors.toList());
    }

    private void validateCatoriaNameInformed(FornecedorRequest request){
        if(ObjectUtils.isEmpty(request.getNome())){
            throw new ValidationException("Nome do Fornecedor é Obrigadtorio");
        }
    }

    public SuccessResponse delete(Long id){
        validateId(id);
        if(produtoService.existeFornecedor(id)){
            throw  new ValidationException("Fornecedor possui vinculo a um produto");
        }

        fornecedorRepository.deleteById(id);
        return SuccessResponse.create("Fornecedor deletedo");
    }

    private void validateId(Long id){
        if(ObjectUtils.isEmpty(id)){
            throw  new ValidationException("Id é obrigatorio");
        }
    }

}
