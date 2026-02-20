CREATE TABLE tabela_tarifaria (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    data_vigencia DATE NOT NULL,
    ativa BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE faixa (
    id BIGSERIAL PRIMARY KEY,
    tabela_tarifaria_id BIGINT NOT NULL,
    categoria VARCHAR(30) NOT NULL,
    inicio INTEGER NOT NULL,
    fim INTEGER NOT NULL,
    valor_unitario NUMERIC(10,2) NOT NULL,

    CONSTRAINT fk_faixa_tabela
        FOREIGN KEY (tabela_tarifaria_id)
        REFERENCES tabela_tarifaria (id)
        ON DELETE CASCADE
);
