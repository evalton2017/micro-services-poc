package com.br.microservice.produto.vendas.client;

import com.br.microservice.produto.vendas.dto.VendasProdutoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name="vendasCliente",
        contextId = "vendasClient",
        url = "${app-config.services.vendas}"
)
public interface VendasClient {

    @GetMapping("produtos/{produtoId}")
    Optional<VendasProdutoResponse> buscaPorVendaProduto(@PathVariable Long produtoId);
}
