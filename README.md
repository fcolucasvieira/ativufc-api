<h1 align="center">🎓 AtivUFC API</h1>

![Status](https://img.shields.io/badge/status-concluído-brightgreen)
![Backend](https://img.shields.io/badge/backend-Spring%20Boot-blue)
![Database](https://img.shields.io/badge/database-PostgreSQL-blueviolet)
![Security](https://img.shields.io/badge/security-JWT-yellow)
![Build](https://img.shields.io/badge/build-Maven-brown)

Uma API para simplificar e modernizar a <b>gestão de horas complementares</b> na UFC.<br>
Segura, automatizada e transparente, conecta discentes, responsáveis e administradores em um único fluxo.<br>
Mais que um projeto acadêmico, é um avanço na experiência universitária e na valorização das atividades extracurriculares 🎓🚀

---

## 👨‍💻 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Security (JWT)**
- **Flyway** (versionamento do banco)
- **PostgreSQL**
- **Swagger/OpenAPI**

---

## ⚙️ Instalação e Configuração

###  Pré-requisitos
- Java SDK 21+
- PostgreSQL (instalado e rodando)
- IDE de sua preferência (IntelliJ, Eclipse, VS Code)

### 📦 Como executar o projeto
1. Clone o repositório:
   ```bash
   git clone https://github.com/fcolucasvieira/ativufc-api.git
   ```
2. Crie o banco de dados:
    ```sql
   CREATE DATABASE ativufc;
   ```
3. Configure o `application.properties`:
    ```bash
   spring.datasource.password=<sua_senha_postgres>
   ```
4. Crie o diretório para uploads:
    ```text
    ativufc-api/
    └── uploads/
        └── comprovantes/
    ```
5. Execute a classe `AtivufcApplication.java` na sua IDE.
* O Flyway irá criar as tabelas e usuários iniciais automaticamente.

## 🔑 Autenticação

A segurança da API é garantida com **JWT (JSON Web Token)**.  
Após o login, o token deve ser enviado em todas as requisições protegidas no _**header**_:

```bash
Authorization: Bearer <seu_token>
```

---

### 🔐 Login
**Endpoint:** `POST /auth/login`
- **Request:**
```json
{
  "email": "lucas.vieira@alu.ufc.br",
  "senha": "Senha@123"
}
```
- **Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "lucas.vieira@alu.ufc.br",
  "perfil": "DISCENTE",
  "id": 1,
  "identificador": "563655"
}
```
--- 

### 🔄 Reset de Senha
O processo ocorre em duas etapas: **request** e **confirm**.

1. Request Reset
    <br>**Endpoint:** `POST /auth/reset-password/request`

**Request:**
```json
{
  "email": "joao.silva@ufc.br"
}
```
**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```
2. Confirm Reset
    <br>**Endpoint:** `POST /auth/reset-password/confirm`
    
**Request:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "novaSenha": "novaSenha@123"
}
```
**Response:**
```json
{
  "sucesso": true, 
  "mensagem": "Senha redefinida com sucesso!"
}
```
---
### 📌 Observações

- O token JWT expira e precisa ser renovado com um novo login.
- Senhas devem ser fortes (letras maiúsculas, minúsculas, números e símbolos).
- Só usuários autenticados podem acessar rotas protegidas.

--- 

## 🔗 Endpoints Principais

| Método | Rota                               | Descrição             | Perfil Autorizado |
|--------|------------------------------------|-----------------------|-------------------|
| POST   | /solicitacoes                      | Cadastrar solicitação | Discente          |
| PUT    | /solicitacoes/{id}/status          | Atualizar status      | Responsável       |
| GET    | /solicitacoes/discente/{matricula} | Listar por matrícula  | Discente, Responsável, Admin |
| POST   | /comprovantes/{solicitacaoId}      | Upload comprovante    | Discente          |

---

## 👥 Perfis de Usuário
- **Discente:** cria solicitações e envia comprovantes.
- **Responsável:** valida solicitações e altera status.
- **Admin:** acesso total e gestão de tabelas auxiliares (cursos, instituições, atividades, subtipos).

--- 
## 📖 Documentação

A documentação completa dos endpoints será disponibilizada futuramente via **Swagger/OpenAPI**.  
Assim que estiver pronta, poderá ser acessada em:

> ⚠️ Em breve: esta seção será atualizada com detalhes e instruções de uso direto pelo Swagger.

## 🚀 Roadmap
- [ ] Implementar documentação Swagger/OpenAPI
- [ ] Adicionar testes automatizados
- [ ] Configurar Docker para deploy simplificado

## 👨‍💻 Autores
Desenvolvido por [@fcolucasvieira](https://github.com/fcolucasvieira) com colaboração de [@brnz4n](https://github.com/brnz4n).
