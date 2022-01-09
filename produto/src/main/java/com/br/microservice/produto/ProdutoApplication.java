package com.br.microservice.produto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProdutoApplication {

	private static final Logger LOG = LoggerFactory.getLogger(ProdutoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProdutoApplication.class, args);
		LOG.info("A aplicacao iniciou corretamente.");
	}

}
