package com.br.microservice.produto.produto.rabbitmq;

import com.br.microservice.produto.produto.dto.ProdutoEstoqueDto;
import com.br.microservice.produto.produto.service.ProdutoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProdutoEstoqueListener {

    @Autowired
    ProdutoService produtoService;

    @RabbitListener(queues = "${app-config.rabbit.queue.produto-estoque}")
    public void updateProdutoEstoque(ProdutoEstoqueDto produtoEstoqueDto) throws JsonProcessingException {
        log.info("Recebendo mensagem ", new ObjectMapper().writeValueAsString(produtoEstoqueDto));
        produtoService.updateQuantidadeProduto(produtoEstoqueDto);
    }
}
