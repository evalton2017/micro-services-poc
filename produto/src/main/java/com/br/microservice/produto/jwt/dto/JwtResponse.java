package com.br.microservice.produto.jwt.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private Integer id;
    private String nome;
    private String email;
    private String password;
    private String cpf;

    public static JwtResponse getUser(Claims jwtClaims){
        try{
            return new ObjectMapper().convertValue(jwtClaims.get("user"), JwtResponse.class);
        }catch (Exception ex){
            return null;
        }

    }

}
