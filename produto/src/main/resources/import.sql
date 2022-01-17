INSERT INTO categoria(id,descricao) VALUES (1000,'Livros');
INSERT INTO categoria(id,descricao) VALUES (1001,'Jogos');
INSERT INTO categoria(id,descricao) VALUES (1002,'Informatica');

INSERT INTO fornecedor(id,nome) VALUES (1000,'Amazon');
INSERT INTO fornecedor(id,nome) VALUES (1001,'Mercado Livre');

INSERT INTO produto(id,nome, quantidade, categoria, fornecedor, created_at) VALUES(1001,'Programação a Objeto', 53, 1000,1000, CURRENT_TIMESTAMP );
INSERT INTO produto(id,nome, quantidade,categoria, fornecedor, created_at) VALUES(1002,'FIFA', 100, 1001,1000, CURRENT_TIMESTAMP);
INSERT INTO produto(id,nome, quantidade,categoria, fornecedor, created_at) VALUES(1003,'Teclado',1000, 1002,1001, CURRENT_TIMESTAMP);