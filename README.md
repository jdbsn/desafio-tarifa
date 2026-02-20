# Desafio Técnico — API de Tabela Tarifária de Água

API REST desenvolvida com Java 21 e Spring Boot para gestão e cálculo de tarifas de água por categoria e faixas de consumo.

O sistema é totalmente parametrizável, permitindo cadastrar novas tabelas tarifárias sem necessidade de alteração de código.

---

## 📋 Pré-requisitos

- Java 21 (JDK)
- PostgreSQL 14+
- Maven ou Maven Wrapper

---

## ⚙️ Configuração da Base de Dados

1. Crie um banco de dados no PostgreSQL:

desafio_tarifa_db

2. Configure as variáveis de ambiente:

DB_URL=jdbc:postgresql://localhost:5432/desafio_tarifa_db  
DB_USERNAME=postgres  
DB_PASSWORD=sua_senha

As tabelas serão criadas automaticamente na primeira execução através do Flyway.

---

## 🚀 Instalação e Execução

1. Clone o repositório:
   git clone <url-do-repositorio>  
   cd desafio-tarifa

2. Compile o projeto:
   ./mvnw clean install

3. Execute a aplicação:
   DB_URL=jdbc:postgresql://localhost:5432/desafio_tarifa_db DB_USERNAME=postgres DB_PASSWORD=sua_senha ./mvnw spring-boot:run

A aplicação iniciará em:
http://localhost:8080

---

# 🔌 Endpoints

## Criar Tabela Tarifária

POST /api/tabelas-tarifarias

Request:

```
{
  "nome": "Tabela Vigente 2026",
  "dataVigencia": "2026-01-01",
  "ativa": true,
  "categorias": [
    {
      "nome": "INDUSTRIAL",
      "faixas": [
        { "inicio": 0, "fim": 10, "valorUnitario": 1.00 },
        { "inicio": 11, "fim": 20, "valorUnitario": 2.00 },
        { "inicio": 21, "fim": 99999, "valorUnitario": 3.00 }
      ]
    }
  ]
}
```

Response — 201 Created:

```
{
  "id": 1,
  "nome": "Tabela Vigente 2026",
  "dataVigencia": "2026-01-01",
  "ativa": true,
  "categorias": [
    {
      "nome": "INDUSTRIAL",
      "faixas": [
        { "inicio": 0, "fim": 10, "valorUnitario": 1.00 },
        { "inicio": 11, "fim": 20, "valorUnitario": 2.00 },
        { "inicio": 21, "fim": 99999, "valorUnitario": 3.00 }
      ]
    }
  ]
}
```

---

## Listar Tabelas Tarifárias

GET /api/tabelas-tarifarias

Response — 200 OK:

```
[
  {
    "id": 1,
    "nome": "Tabela Vigente 2026",
    "dataVigencia": "2026-01-01",
    "ativa": true,
    "categorias": [
      {
        "nome": "INDUSTRIAL",
        "faixas": [
          { "inicio": 0, "fim": 10, "valorUnitario": 1.00 },
          { "inicio": 11, "fim": 20, "valorUnitario": 2.00 },
          { "inicio": 21, "fim": 99999, "valorUnitario": 3.00 }
        ]
      }
    ]
  }
]
```

---

## Excluir Tabela Tarifária

DELETE /api/tabelas-tarifarias/{id}

Response — 204 No Content

---

## Calcular Tarifa

POST /api/calculos

Request

```
{
  "categoria": "INDUSTRIAL",
  "consumo": 18
}

```
Response — 200 OK:

```
{
  "categoria": "INDUSTRIAL",
  "consumoTotal": 18,
  "valorTotal": 26.00,
  "detalhamento": [
    {
      "faixa": { "inicio": 0, "fim": 10 },
      "m3Cobrados": 10,
      "valorUnitario": 1.00,
      "subtotal": 10.00
    },
    {
      "faixa": { "inicio": 11, "fim": 20 },
      "m3Cobrados": 8,
      "valorUnitario": 2.00,
      "subtotal": 16.00
    }
  ]
}
```

---

# 🧪 Como Testar a Aplicação

- Utilize Postman, Insomnia ou curl
- Cadastre uma tabela tarifária ativa
- Execute o endpoint de cálculo
- Verifique o detalhamento retornado

---

# 📌 Regras de Negócio

- Apenas uma tabela tarifária pode estar ativa por vez
- As faixas devem iniciar em 0, ser contínuas e cobrir até 99999
- O cálculo é progressivo por faixa
