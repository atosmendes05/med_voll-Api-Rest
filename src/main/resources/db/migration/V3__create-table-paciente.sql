create table pacientes(

    id BIGSERIAL NOT NULL,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    telefone varchar(100),
    cpf varchar(8) not null unique,
    ativo BOOLEAN not null,
    logradouro varchar(100) not null,
    bairro varchar(100) not null,
    cep varchar(9) not null,
    complemento varchar(100),
    numero varchar(20),
    uf char(2) not null,
    cidade varchar(100) not null,

    primary key(id)

);