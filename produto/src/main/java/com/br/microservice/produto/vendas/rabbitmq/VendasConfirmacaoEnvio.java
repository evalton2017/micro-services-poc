package com.br.microservice.produto.vendas.rabbitmq;

import com.br.microservice.produto.vendas.dto.VendaConfirmadaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VendasConfirmacaoEnvio {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${app-config.rabbit.exchange.produto}")
    private String produtoTopico;

    @Value("${app-config.rabbit.routingKey.vendas-confirmada}")
    private String produtoVendaConfirmadaKey;

    public void envioConfirmacaoVendaMessage(VendaConfirmadaDto message){
        try{
            log.info("Enviando mensagem: {}", new ObjectMapper().writeValueAsString(message));
            rabbitTemplate.convertAndSend(produtoTopico, produtoVendaConfirmadaKey, message);
            log.info("Mensagem enviada com sucesso");
        }catch (Exception ex){
            log.info("Erro durante envio da mensagem de confirmação", ex.getMessage());
        }
    }


}
