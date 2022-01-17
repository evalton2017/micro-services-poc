package com.br.microservice.produto.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app-config.rabbit.exchange.produto}")
    private String produtoTopico;

    @Value("${app-config.rabbit.routingKey.produto-estoque}")
    private String ProdutoEstoqueKey;

    @Value("${app-config.rabbit.routingKey.vendas-confirmada}")
    private String produtoVendaConfirmadaKey;

    @Value("${app-config.rabbit.queue.produto-estoque}")
    private String filaProdutoEstoqueMq;

    @Value("${app-config.rabbit.queue.vendas-confirmada}")
    private String filaProdutoVendaConfirmadaMq;

    @Bean
    public TopicExchange pridutoTopico(){
        return new TopicExchange(produtoTopico);
    }

    @Bean
    public Queue produtoEstoqueMq(){
        return new Queue(filaProdutoEstoqueMq, true);
    }

    @Bean
    public Queue vendasConfigmadaMq(){
        return new Queue(filaProdutoVendaConfirmadaMq, true);
    }

    @Bean
    public Binding produtoEstoqueMqBinding(TopicExchange topicExchange){
        return BindingBuilder
                .bind(produtoEstoqueMq())
                .to(topicExchange)
                .with(ProdutoEstoqueKey);
    }

    @Bean
    public Binding produtoVendaConfirmadaMqBinding(TopicExchange topicExchange){
        return BindingBuilder
                .bind(vendasConfigmadaMq())
                .to(topicExchange)
                .with(produtoVendaConfirmadaKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
