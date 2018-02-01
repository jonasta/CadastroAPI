Java version "1.8.0_101"

Tomcat 8.0

Banco de Dados : PostgreSQL 9.5

Usuario : postgres

Senha : root

***SQL de Inicialização***

CREATE TABLE cliente (
  id serial NOT NULL,
  data jsonb,
  primary key(id)
);

INSERT INTO cliente VALUES (1,'{"nome": "Jonasta dos Santos", "nascimento": "25/09/1989", "cpf" : "58675623100", "email" : "jons@gmail.com", "telefone" : "4199996666", "ativo": true}');
