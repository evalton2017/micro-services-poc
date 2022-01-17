package com.br.microservice.produto.produto.service;

import com.br.microservice.produto.categoria.dto.CategoriaRequest;
import com.br.microservice.produto.categoria.dto.CategoriaResponse;
import com.br.microservice.produto.categoria.model.Categoria;
import com.br.microservice.produto.categoria.service.CategoriaService;
import com.br.microservice.produto.config.exception.NotFoundException;
import com.br.microservice.produto.config.exception.SuccessResponse;
import com.br.microservice.produto.config.exception.ValidationException;
import com.br.microservice.produto.fornecedor.dto.FornecedorResponse;
import com.br.microservice.produto.fornecedor.service.FornecedorService;
import com.br.microservice.produto.produto.dto.*;
import com.br.microservice.produto.produto.model.Produto;
import com.br.microservice.produto.produto.repository.ProdutoRepository;
import com.br.microservice.produto.vendas.client.VendasClient;
import com.br.microservice.produto.vendas.dto.VendaConfirmadaDto;
import com.br.microservice.produto.vendas.enums.Status;
import com.br.microservice.produto.vendas.rabbitmq.VendasConfirmacaoEnvio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProdutoService {

    private static Integer ZERO = 0;

    @Autowired
    ProdutoRepository repository;

    @Autowired
    CategoriaService categoriaService;

    @Autowired
    FornecedorService fornecedorService;

    @Autowired
    private VendasConfirmacaoEnvio vendasConfirmacaoEnvio;

    @Autowired
    private VendasClient vendasClient;

    public ProdutoResponse save(ProdutoRequest request) {
        validateDadosProduto(request);
        var categoria = categoriaService.findById(request.getCategoria());
        var fornecedor = fornecedorService.findById(request.getFornecedor());
        var produto = repository.save(Produto.of(request, fornecedor, categoria));

        return ProdutoResponse.of(produto);
    }

    public ProdutoResponse update(ProdutoRequest request, Long id) {
        validateDadosProduto(request);
        var categoria = categoriaService.findById(request.getCategoria());
        var fornecedor = fornecedorService.findById(request.getFornecedor());
        var produto = Produto.of(request, fornecedor, categoria);
        produto.setId(id);
        repository.save(produto);

        return ProdutoResponse.of(produto);
    }

    public List<ProdutoResponse> listarFornecedor() {
        return repository.findAll()
                .stream()
                .map(ProdutoResponse::of)
                .collect(Collectors.toList());
    }

    public ProdutoResponse consultaPorId(Long id) {
        return repository.findById(id)
                .stream()
                .map(ProdutoResponse::of)
                .collect(Collectors.toList()).get(0);
    }

    public Produto buscaProdutoPorId(Long id) {
        return repository.findById(id).orElseThrow(
                ()-> new NotFoundException("Produto Não Encontrado"));
    }


    public List<ProdutoResponse> listarPorCategoria(Long id) {
        return repository.findByCategoriaId(id)
                .stream()
                .map(ProdutoResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProdutoResponse> listarPorFornecedor(Long id) {
        return repository.findByFornecedorId(id)
                .stream()
                .map(ProdutoResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProdutoResponse> listarPorNome(String nome) {
        if (nome.isEmpty()) {
            throw new ValidationException("Nome é obrigatorio");
        }
        return repository.findByNomeIgnoreCaseContaining(nome)
                .stream()
                .map(ProdutoResponse::of)
                .collect(Collectors.toList());
    }

    private void validateDadosProduto(ProdutoRequest request) {
        if (ObjectUtils.isEmpty(request.getNome())) {
            throw new ValidationException("Nome do Produto é Obrigadtorio");
        }

        if (ObjectUtils.isEmpty(request.getQuantidade())) {
            throw new ValidationException("Quantidade é Obrigadtorio");
        }

        if (ObjectUtils.isEmpty(request.getQuantidade() <= ZERO)) {
            throw new ValidationException("Quantidade deve ser superior a 0");
        }
    }

    public Boolean existeCategoria(Long id) {
        return repository.existsByCategoriaId(id);
    }

    public Boolean existeFornecedor(Long id) {
        return repository.existsByFornecedorId(id);
    }

    public SuccessResponse delete(Long id) {
        validateId(id);
        repository.deleteById(id);
        return SuccessResponse.create("Produto deletado");
    }

    private void validateId(Long id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new ValidationException("Id é obrigatorio");
        }
    }

    @Transactional
    public void updateQuantidadeProduto(ProdutoEstoqueDto produtoEstoqueDto){
        var produtoParaUpdate = new ArrayList<Produto>();
        try{
            validaProduto(produtoEstoqueDto);
            produtoEstoqueDto.getProdutos().forEach( produtoVenda ->{
                var existProduto =buscaProdutoPorId(produtoVenda.getProdutoId());
                if(produtoVenda.getQuantidade() > existProduto.getQuantidade()){
                    throw new ValidationException("O estoque deste produto é de "+ existProduto.getQuantidade());
                }
                existProduto.atualizaEstoque(produtoVenda.getQuantidade());
                produtoParaUpdate.add(existProduto);
            });
            if(!ObjectUtils.isEmpty(produtoParaUpdate)){
                repository.saveAll(produtoParaUpdate);
                var messageSucesso = new VendaConfirmadaDto(produtoEstoqueDto.getVendasId(), Status.APROVADO);
                vendasConfirmacaoEnvio.envioConfirmacaoVendaMessage(messageSucesso);
            }
        }catch(Exception e){
            log.error("Erro durante a atualizacao do produto. "+e.getMessage());
            var mensagemRejeicao = new VendaConfirmadaDto(produtoEstoqueDto.getVendasId(), Status.REJEITADO);
            vendasConfirmacaoEnvio.envioConfirmacaoVendaMessage(mensagemRejeicao);
        }
    }

    private void validaProduto(ProdutoEstoqueDto produtoEstoqueDto){
        if(ObjectUtils.isEmpty(produtoEstoqueDto) || ObjectUtils.isEmpty(produtoEstoqueDto.getVendasId())){
            throw new ValidationException("O id da Venda deve ser informado ");
        }
        if(ObjectUtils.isEmpty(produtoEstoqueDto.getProdutos())){
            throw new ValidationException("Produto não informado");
        }

        produtoEstoqueDto.getProdutos()
                .forEach(vendaProduto -> {
                    if(ObjectUtils.isEmpty(vendaProduto.getProdutoId()) || ObjectUtils.isEmpty(vendaProduto.getQuantidade())){
                        throw new ValidationException("Produto id e quantidade devem ser informados");
                    }
                });
    }

    public ProdutoVendasResponse buscaVendasProduto(Long id){
        var produto = buscaProdutoPorId(id);
        try{
            var vendas = vendasClient.buscaPorVendaProduto(produto.getId()).
                    orElseThrow(()-> new NotFoundException("Vendas para o produto não encontrado"));
            return ProdutoVendasResponse.of(produto, vendas.getVendasIds());
        }catch (Exception ex){
            throw  new ValidationException("Erro ao recuperar a venda");
        }
    }

    public SuccessResponse verificaEstoqueProduto(ProdutoEstoqueRequest request){
        if(ObjectUtils.isEmpty(request) || ObjectUtils.isEmpty(request.getProdutos())){
            throw new ValidationException("Produto não informado");
        }
        request.getProdutos().forEach(this::validaEstoque);
        return SuccessResponse.create("Estoque esta ok!");
    }

    private void validaEstoque(ProdutoQuantidateDto produtoQuantidateDto){
        if(ObjectUtils.isEmpty(produtoQuantidateDto.getProdutoId()) || ObjectUtils.isEmpty(produtoQuantidateDto.getQuantidade())){
            throw new ValidationException("Id do produto invalido ou estoque indisponivel");
        }
        var produto = buscaProdutoPorId(produtoQuantidateDto.getProdutoId());
        if(produtoQuantidateDto.getQuantidade() > produto.getQuantidade()){
            throw new ValidationException("Estoque indisponivel, a quantidade maxima é de "+produto.getQuantidade());
        }
    }


}
