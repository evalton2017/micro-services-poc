package com.br.microservice.produto.vendas.dto;

import com.br.microservice.produto.vendas.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaConfirmadaDto {
    private String vendaId;
    private Status statusVenda;
}
