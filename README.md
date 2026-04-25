<h1 align="center">🎓 AtivUFC API</h1>

![Java](https://img.shields.io/badge/java-21-red?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/spring_boot-brightgreen?style=for-the-badge&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/postgresql-database-blue?style=for-the-badge&logo=postgresql)
![JWT](https://img.shields.io/badge/JWT-security-black?style=for-the-badge&logo=jsonwebtokens)


Uma API para <b>simplificar e modernizar a gestão de horas complementares</b> na UFC.<br>
Segura, automatizada e transparente, conecta discentes, responsáveis e administradores em um único fluxo.<br>
Mais que um projeto acadêmico, é um avanço na experiência universitária e na valorização das atividades extracurriculares 🎓🚀

---

## 👨‍💻 Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Security (JWT)
- Flyway (versionamento do banco)
- PostgreSQL
- Swagger/OpenAPI (em implementação)
- Maven 

---

## ⚙️ Instalação e Configuração

###  📋 Pré-requisitos
- Java SDK 21+
- PostgreSQL (instalado e em execução)
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
    ```java
   spring.datasource.password=<sua_senha_postgres>
   ```
4. Crie o diretório para uploads:
    ```text
    ativufc-api/
    └── uploads/
        └── comprovantes/
    ```
5. Execute a classe `AtivufcApplication.java` na sua IDE.
* O **Flyway** irá criar as tabelas e usuários iniciais automaticamente.

## 🔐 Autenticação e Segurança

A API utiliza **JWT (JSON Web Token)** para autenticação e controle de acesso.  
Após o login, o token deve ser enviado em todas as requisições protegidas no _**header**_:
```bash
Authorization: Bearer <seu_token>
```

---

### 🔑 Login
**Endpoint:** `POST /auth/login`
- **Request:**
```json
{
  "email": "joao.silva@alu.ufc.br",
  "senha": "Senha@123"
}
```
- **Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "joao.silva@alu.ufc.br",
  "perfil": "DISCENTE",
  "id": 1,
  "identificador": "123456"
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

## 👥 Perfis de Usuário

A AtivUFC API adota **controle de acesso baseado em papéis (RBAC)**, garantindo que cada usuário tenha acesso apenas às funcionalidades compatíveis com sua função no sistema.

### 🎓 Discente
Responsável por registrar suas atividades complementares.
- Criar solicitações de horas
- Enviar comprovantes
- Acompanhar o status das solicitações

### 🧑‍🏫 Responsável
Responsável pela validação acadêmica das atividades.
- Visualizar solicitações pendentes
- Analisar comprovantes
- Aprovar ou rejeitar solicitações

### 🛠️ Administrador
Usuário com acesso completo ao sistema.
- Gerenciar usuários e permissões
- Manter tabelas auxiliares (cursos, instituições, atividades e subtipos)
- Acessar todos os dados da aplicação

---

## 🔗 Endpoints Principais

### 📄 Solicitações
| Método | Rota                               | Descrição             | Perfil Autorizado |
|--------|------------------------------------|-----------------------|-------------------|
| POST   | /solicitacoes                      | Cadastrar solicitação | Discente          |
| PUT    | /solicitacoes/{id}/status          | Atualizar status      | Responsável       |
| GET    | /solicitacoes/discente/{matricula} | Listar por matrícula  | Discente, Responsável, Admin |

### 📎 Comprovantes
| Método | Rota                               | Descrição             | Perfil Autorizado |
|--------|------------------------------------|-----------------------|-------------------|
| POST   | /comprovantes/{solicitacaoId}      | Upload de comprovante | Discente          |
| GET    | /comprovantes/{id}/download        | Download de comprovante | Discente, Responsável |

---

## 📘 Regulamento de Horas Complementares

A AtivUFC API é uma ferramenta de apoio e **não substitui a validação oficial das horas acadêmicas**.  

- Os **subtipos** de atividades seguem os limites e definições do regulamento do curso de **Engenharia da Computação da UFC**.  
- Os grupos de **atividades** não têm limites aplicados, já que cada curso possui regras próprias e aplicar limites automaticamente poderia ser imprudente.  

O sistema funciona **apenas como orientação**, enquanto a validação final das horas cabe à coordenação do curso.

---

## 📁 Documentação

A documentação completa dos endpoints será disponibilizada futuramente via **Swagger/OpenAPI**.
Assim que estiver pronta, poderá ser acessada em:
> ⚠️ Em breve: esta seção será atualizada com detalhes e instruções de uso direto pelo Swagger.

---

## 🚀 Roadmap
- [ ] Implementar Swagger/OpenAPI
- [ ] Adicionar testes automatizados
- [ ] Configurar Docker para deploy simplificado

---

## 👨‍💻 Autores
- [Lucas Vieira](https://github.com/fcolucasvieira) – Desenvolvedor principal
- [Breno Magalhães](https://github.com/brnz4n) – Colaborador

