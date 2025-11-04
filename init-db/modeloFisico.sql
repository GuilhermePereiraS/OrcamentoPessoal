

CREATE TABLE Usuario (
    Id_usuario serial PRIMARY KEY,
    login varchar(21),
    senha varchar(60),
    dt_nascimento date,
    nome varchar(200),
    saldo numeric(7,2)
);

CREATE TABLE Forma_pgmt (
    id_forma_pgmt serial PRIMARY KEY,
    descricao varchar(10)
);

CREATE TABLE Tipo_gasto (
    id_tipo_gasto serial PRIMARY KEY,
    descricao varchar(200)
);

CREATE TABLE Extrato (
    id_extrato serial PRIMARY KEY,
    Id_usuario integer,
    id_forma_pgmt integer,
    id_tipo_gasto integer,
    descricao varchar(100),
    tp_transacao varchar(8),
    vl_transacao numeric(7,2),
    dt_transacao date,

    FOREIGN KEY (Id_usuario) REFERENCES Usuario (Id_usuario) ,
    FOREIGN KEY (id_forma_pgmt) REFERENCES Forma_pgmt (id_forma_pgmt),
    FOREIGN KEY (id_tipo_gasto) REFERENCES Tipo_gasto (id_tipo_gasto)
);

CREATE TABLE Porquinho (
    id_porquinho serial PRIMARY KEY,
    Id_usuario integer,
    nome_meta varchar(50),
    vl_alcancado numeric(7,2),
    vl_necessario numeric(7,2),
    dt_meta date,
    FOREIGN KEY (Id_usuario) REFERENCES Usuario (Id_usuario)
);

CREATE TABLE Orcamento (
    id_orcamento serial PRIMARY KEY,
    Id_usuario integer,
    mes integer,
    ano integer,
    limite_mensal numeric(7,2),
    FOREIGN KEY (Id_usuario) REFERENCES Usuario (Id_usuario)
);

CREATE TABLE Item (
    id_item serial PRIMARY KEY,
    id_extrato integer,
    nome varchar(50),
    vl_unitario numeric(7,2),
    vl_total numeric(7,2),
    quantidade integer,
    FOREIGN KEY (id_extrato) REFERENCES Extrato (id_extrato)
);

CREATE TABLE orcamento_tipo_gasto (
    id_orcamento integer,
    id_tipo_gasto integer,
    limite numeric(7,2),
    FOREIGN KEY (id_orcamento) REFERENCES Orcamento (id_orcamento),
    FOREIGN KEY (id_tipo_gasto) REFERENCES Tipo_gasto (id_tipo_gasto)
);

CREATE FUNCTION atualizaSaldo()
    RETURNS TRIGGER AS $$
DECLARE
linha RECORD;
valor NUMERIC := 0;
argIdUsuario INT;
BEGIN
	argIdUsuario := COALESCE(NEW.id_usuario, OLD.id_usuario);

FOR linha IN
    SELECT tp_transacao, vl_transacao
    FROM EXTRATO
    WHERE EXTRATO.id_usuario = argIdUsuario
    ORDER BY EXTRATO.dt_transacao ASC
LOOP
	IF linha.tp_transacao = 'entrada'
	THEN
        valor := valor + linha.vl_transacao;
    ELSIF linha.tp_transacao = 'saida'
    THEN
        valor := valor - linha.vl_transacao;
    END IF;
END LOOP;

UPDATE usuario
SET saldo = valor
WHERE usuario.id_usuario = argIdUsuario;

RETURN NEW;
END;
$$
LANGUAGE plpgsql;

-- trigger abaixo
CREATE TRIGGER trigger_atualiza_saldo
    AFTER INSERT OR
    UPDATE OR
    DELETE
    ON extrato
FOR EACH ROW EXECUTE FUNCTION atualizaSaldo();

INSERT INTO Tipo_gasto (descricao)
VALUES ('Supérfluo'),
       ('Necessário'),
       ('Mercado');

INSERT INTO Forma_pgmt (descricao)
VALUES ('Dinheiro'),
       ('Debito'),
       ('Credito'),
       ('Pix');




