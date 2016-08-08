Banco de Dados : PostgreSQL 9.5

Usuario : postgres

Senha : root

***SQL de Inicialização***

CREATE TABLE cliente (
  id serial NOT NULL,
  data jsonb,
  primary key(id)
);

INSERT INTO cliente VALUES (1,'{"nome": "Jonasta dos Santos", "nascimento": "25/09/1989", "cpf" : "06725625967", "email" : "jonastads@gmail.com", "telefone" : "4188721205", "ativo": true}');
