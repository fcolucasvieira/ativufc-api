# 🏛️ AtivUFC API

Projeto desenvolvido no contexto da disciplina de **Engenharia de Software** da Universidade Federal do Ceará (UFC).

Esta API RESTful foi construída com **Spring Boot** e tem como objetivo gerenciar atividades acadêmicas e solicitações institucionais realizadas por discentes. O sistema segue boas práticas de arquitetura, organização em camadas, validação de dados e segurança.

---

## 🚀 Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security (em breve)
- PostgreSQL (em breve)
- Maven

---

## 📦 Estrutura do projeto

src/ └── main/ └── java/ └── br.ufc.ativufc/ ├── controller/ ├── model/ ├── repository/ ├── service/ └── config/

---

## 📌 Funcionalidades atuais

- Cadastro de discentes, instituições, subtipos de atividade
- Registro de solicitações de atividades
- Validação de dados via DTOs
- Persistência em banco H2 (temporário)
- Estrutura pronta para autenticação e perfis de usuário

---

## 📚 Endpoints principais

| Método | Endpoint               | Descrição                          |
|--------|------------------------|------------------------------------|
| POST   | `/discentes`           | Cadastra um novo discente          |
| POST   | `/instituicoes`        | Cadastra uma instituição           |
| POST   | `/subtipos`            | Cadastra um subtipo de atividade   |
| POST   | `/solicitacoes`        | Registra uma nova solicitação      |

> Endpoints de leitura, atualização e exclusão serão adicionados em breve.

---

## 🔐 Segurança

A estrutura está preparada para autenticação via **JWT** e definição de perfis:
- `DISCENTE` → pode cadastrar e consultar suas solicitações
- `RESPONSAVEL` → pode deferir, indeferir e listar todas as solicitações

---

## 👥 Contribuição

Este projeto está em desenvolvimento colaborativo. Para contribuir:

1. Faça um fork do repositório
2. Crie uma branch: `git checkout -b feature/nome-da-feature`
3. Commit suas alterações: `git commit -m 'Adiciona nova feature'`
4. Push para sua branch: `git push origin feature/nome-da-feature`
5. Abra um Pull Request

---

## 📄 Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

---

## ✉️ Contato

Desenvolvido por Lucas Vieira e colaboradores da UFC.  
Para dúvidas ou sugestões, entre em contato via GitHub Issues.
