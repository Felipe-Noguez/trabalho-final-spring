CREATE USER POKE_STORE IDENTIFIED BY oracle;
GRANT CONNECT TO POKE_STORE;
GRANT CONNECT, RESOURCE, DBA TO POKE_STORE;
GRANT CREATE SESSION TO POKE_STORE;
GRANT DBA TO POKE_STORE;
GRANT CREATE VIEW, CREATE PROCEDURE, CREATE SEQUENCE to POKE_STORE;
GRANT UNLIMITED TABLESPACE TO POKE_STORE;
GRANT CREATE MATERIALIZED VIEW TO POKE_STORE;
GRANT CREATE TABLE TO POKE_STORE;
GRANT GLOBAL QUERY REWRITE TO POKE_STORE;
GRANT SELECT ANY TABLE TO POKE_STORE;

CREATE TABLE USUARIO (
    ID_USUARIO NUMBER NOT NULL,
    PIX VARCHAR2(50) NOT NULL,
    EMAIL VARCHAR2(255) NOT NULL,
    SENHA VARCHAR2(500) NOT NULL,
    NOME VARCHAR2(255) NOT NULL,
    ENDERECO VARCHAR2(255) NOT NULL,
    CPF CHAR(11) NOT NULL,
    CIDADE VARCHAR2(255) NOT NULL,
    ESTADO VARCHAR2(50) NOT NULL,
    TELEFONE CHAR(11) NOT NULL,
    STATUS CHAR(1) NOT NULL,
    PRIMARY KEY (ID_USUARIO),
    UNIQUE (PIX, EMAIL,CPF)
);

CREATE TABLE CARGO (
    ID_CARGO NUMBER NOT NULL,
    NOME varchar2(512) UNIQUE NOT NULL,
    PRIMARY KEY(ID_CARGO)
);

CREATE TABLE USUARIO_CARGO (
    ID_USUARIO NUMBER NOT NULL,
    ID_CARGO NUMBER NOT NULL,
    PRIMARY KEY(ID_USUARIO, ID_CARGO),
    CONSTRAINT FK_USUARIO_CARGO_CARGO FOREIGN KEY (ID_CARGO) REFERENCES CARGO (ID_CARGO),
    CONSTRAINT FK_USUARIO_CARGO_USUARIO FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO (ID_USUARIO)
);

CREATE TABLE PRODUTO (
    ID_PRODUTO NUMBER NOT NULL,
    NOME VARCHAR2(255) NOT NULL,
    DESCRICAO VARCHAR2(255) NOT NULL,
    QUANTIDADE NUMBER NOT NULL,
    TIPO VARCHAR2(1),
    VALOR NUMBER(10,2),
    ID_USUARIO NUMBER NOT NULL,
    PRIMARY KEY (ID_PRODUTO),
    FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID_USUARIO)
);

CREATE TABLE CUPOM (
    ID_CUPOM NUMBER NOT NULL,
    DESCONTO NUMBER(10,2),
    PRIMARY KEY (ID_CUPOM)
);

CREATE TABLE PEDIDO (
    ID_PEDIDO NUMBER NOT NULL,
    ID_CUPOM NUMBER,
    ID_USUARIO NUMBER NOT NULL,
    VALOR_FINAL NUMBER(10,2),
    PRIMARY KEY (ID_PEDIDO),
    FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID_USUARIO),
    FOREIGN KEY (ID_CUPOM) REFERENCES CUPOM(ID_CUPOM)
);

CREATE TABLE PRODUTO_PEDIDO (
    ID_PRODUTO_PEDIDO NUMBER NOT NULL,
    ID_PRODUTO NUMBER NOT NULL,
    ID_PEDIDO NUMBER NOT NULL,
    QUANTIDADE NUMBER NOT NULL,
    VALOR NUMBER(10,2),
    PRIMARY KEY (ID_PRODUTO_PEDIDO),
    FOREIGN KEY (ID_PRODUTO) REFERENCES PRODUTO(ID_PRODUTO),
    FOREIGN KEY (ID_PEDIDO) REFERENCES PEDIDO(ID_PEDIDO)
);


CREATE SEQUENCE SEQ_USUARIO
    START WITH 1
    INCREMENT BY 1
    NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_PEDIDO
    START WITH 1
    INCREMENT BY 1
    NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_PRODUTO_PEDIDO
    START WITH 1
    INCREMENT BY 1
    NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_PRODUTO
    START WITH 1
    INCREMENT BY 1
    NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_CUPOM
    START WITH 1
    INCREMENT BY 1
    NOCACHE NOCYCLE;

CREATE SEQUENCE seq_cargo
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE;

-- USUARIO --

INSERT INTO POKE_STORE.USUARIO (ID_USUARIO,PIX,EMAIL,SENHA,NOME,ENDERECO,CPF,CIDADE,ESTADO,TELEFONE, STATUS)
VALUES (SEQ_USUARIO.nextval, 'gustavo', 'gustavo', '795f14832eb56fcb6ea109dfced5bc3acc749413e8d63db1f86efb01a7258825542aed37bf402f29', 'gustavo lucena', 'RUA UM', '03132190341','PORTO ALEGRE','RIO GRANDE DO SUL','51991458796', '1');

INSERT INTO POKE_STORE.USUARIO (ID_USUARIO,PIX,EMAIL,SENHA,NOME,ENDERECO,CPF,CIDADE,ESTADO,TELEFONE, STATUS)
VALUES (SEQ_USUARIO.nextval, 'joaokleber@hotmail.com', 'joaokleber@hotmail.com', '795f14832eb56fcb6ea109dfced5bc3acc749413e8d63db1f86efb01a7258825542aed37bf402f29', 'JOAO KLEBER', 'RUA DOIS', '03216549875','PORTO ALEGRE','RIO GRANDE DO SUL','51987654321', '1');

INSERT INTO POKE_STORE.USUARIO (ID_USUARIO,PIX,EMAIL,SENHA,NOME,ENDERECO,CPF,CIDADE,ESTADO,TELEFONE, STATUS)
VALUES (SEQ_USUARIO.nextval, 'richarlison@hotmail.com', 'richarlison@hotmail.com', '795f14832eb56fcb6ea109dfced5bc3acc749413e8d63db1f86efb01a7258825542aed37bf402f29', 'RICHARLISON DE ALMEIDA', 'RUA TRES', '01154987652','PORTO ALEGRE','RIO GRANDE DO SUL','5199145876', '1');

INSERT INTO POKE_STORE.USUARIO (ID_USUARIO,PIX,EMAIL,SENHA,NOME,ENDERECO,CPF,CIDADE,ESTADO,TELEFONE, STATUS)
VALUES (SEQ_USUARIO.nextval, 'vladimir@hotmail.com', 'vladimir@hotmail.com', '795f14832eb56fcb6ea109dfced5bc3acc749413e8d63db1f86efb01a7258825542aed37bf402f29', 'VLADIMIR PUTIN', 'RUA DEZENOVE', '03132626987','PORTO ALEGRE','RIO GRANDE DO SUL','51974458796', '1');

INSERT INTO POKE_STORE.USUARIO (ID_USUARIO,PIX,EMAIL,SENHA,NOME,ENDERECO,CPF,CIDADE,ESTADO,TELEFONE, STATUS)
VALUES (SEQ_USUARIO.nextval, 'neyney@hotmail.com', 'neyney@hotmail.com', '795f14832eb56fcb6ea109dfced5bc3acc749413e8d63db1f86efb01a7258825542aed37bf402f29', 'NEYMAR JR', 'RUA DEZ', '12345678911','SANTOS','SAO PAULO','51991458555', '1');

-- PRODUTOS --

INSERT INTO POKE_STORE.PRODUTO (ID_PRODUTO,NOME,DESCRICAO,QUANTIDADE,TIPO,VALOR,ID_USUARIO)
VALUES (SEQ_PRODUTO.nextval,'Pikachu','Pelucia gigante de 1 metro e meio de altura',10,2,50.99,1);

INSERT INTO POKE_STORE.PRODUTO (ID_PRODUTO,NOME,DESCRICAO,QUANTIDADE,TIPO,VALOR,ID_USUARIO)
VALUES (SEQ_PRODUTO.nextval,'Charizard','Pelucia gigante de 1 metro e meio de altura',80,2,67.99,2);

INSERT INTO POKE_STORE.PRODUTO (ID_PRODUTO,NOME,DESCRICAO,QUANTIDADE,TIPO,VALOR,ID_USUARIO)
VALUES (SEQ_PRODUTO.nextval,'Pokemon Fire Red','Jogo de GBA EDICAO LIMITADA',100,0,3500.99,3);

INSERT INTO POKE_STORE.PRODUTO (ID_PRODUTO,NOME,DESCRICAO,QUANTIDADE,TIPO,VALOR,ID_USUARIO)
VALUES (SEQ_PRODUTO.nextval,'Pokemon leaf Green','Jogo de GBA EDICAO LIMITADA',100,0,9500.99,4);

INSERT INTO POKE_STORE.PRODUTO (ID_PRODUTO,NOME,DESCRICAO,QUANTIDADE,TIPO,VALOR,ID_USUARIO)
VALUES (SEQ_PRODUTO.nextval,'Nintendo Switch','Edicao Deluxe Ouro Macico',3,1,11999.99,5);

-- CUPOM ---

INSERT INTO POKE_STORE.CUPOM (ID_CUPOM, DESCONTO)
VALUES (SEQ_CUPOM.nextval, 150.00);

INSERT INTO POKE_STORE.CUPOM (ID_CUPOM, DESCONTO)
VALUES (SEQ_CUPOM.nextval, 200.00);

INSERT INTO POKE_STORE.CUPOM (ID_CUPOM, DESCONTO)
VALUES (SEQ_CUPOM.nextval, 190.00);

INSERT INTO CUPOM (ID_CUPOM, DESCONTO)
VALUES (SEQ_CUPOM.nextval, 80.00);

INSERT INTO CUPOM (ID_CUPOM, DESCONTO)
VALUES (SEQ_CUPOM.nextval, 80.00);

-- PEDIDOS (O VALOR FINAL TA ERRADO, PORQUE A GENTE NAO SABE COMO REFERENCIAR AO VALOR DO PRODUTO, AFINAL, O CLIENTE TERIA QUE ESCOLHER NO PEDIDO E SERIA BASEADO NA QUANTIDADE * PRECO - CUPOM) ----

INSERT INTO PEDIDO (ID_PEDIDO,ID_CUPOM,ID_USUARIO,VALOR_FINAL)
VALUES (SEQ_PEDIDO.nextval, 1, 1, 1230.00);

INSERT INTO PEDIDO (ID_PEDIDO,ID_CUPOM,ID_USUARIO,VALOR_FINAL)
VALUES (SEQ_PEDIDO.nextval, 2, 2, 120.00);

INSERT INTO PEDIDO (ID_PEDIDO,ID_CUPOM,ID_USUARIO,VALOR_FINAL)
VALUES (SEQ_PEDIDO.nextval, 3, 3, 930.00);

INSERT INTO PEDIDO (ID_PEDIDO,ID_CUPOM,ID_USUARIO,VALOR_FINAL)
VALUES (SEQ_PEDIDO.nextval, 4, 4, 960.00);

INSERT INTO PEDIDO (ID_PEDIDO,ID_CUPOM,ID_USUARIO,VALOR_FINAL)
VALUES (SEQ_PEDIDO.nextval, 4, 4, 1230.00);

----- INSERT PRODUTO PEDIDO -----
INSERT INTO PRODUTO_PEDIDO (ID_PRODUTO_PEDIDO, ID_PRODUTO, ID_PEDIDO, QUANTIDADE, VALOR)
VALUES (SEQ_PRODUTO_PEDIDO.nextval, 1, 1, 5, 800);

----- CRIACAO CARGOS -----

INSERT INTO CARGO (ID_CARGO, NOME)
VALUES (seq_cargo.nextval, 'ROLE_ADMIN');

INSERT INTO CARGO (ID_CARGO, NOME)
VALUES (seq_cargo.nextval, 'ROLE_CLIENTE');

INSERT INTO CARGO (ID_CARGO, NOME)
VALUES (seq_cargo.nextval, 'ROLE_VENDEDOR');

INSERT INTO USUARIO_CARGO (ID_USUARIO, ID_CARGO)
VALUES (1,1);

