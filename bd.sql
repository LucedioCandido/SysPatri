create database SysPatrimonio;
use SysPatrimonio;

#Tabela de localização
create table localizacao(
	cod_localizacao int auto_increment,
	nome varchar(45),
    descricao varchar(45),
    primary key(cod_localizacao)
);

#Tabela  de categorias
create table categoria(
	cod_categoria int auto_increment,
    nome varchar(45),
    descricao varchar(45),
    primary key(cod_categoria)
);

#Tabela de bens
create table bens(
	cod_bem int auto_increment,
    nome varchar(45),
    descricao varchar(45),
    cod_localizacao int not null,
    cod_categoria int not null,    
    primary key(cod_bem),
    foreign key(cod_localizacao) references localizacao(cod_localizacao),
    foreign key(cod_categoria) references categoria(cod_categoria)    
);
show databases;

select * from bens;




