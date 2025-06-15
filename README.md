# Sistema de Autenticação JWT com Spring Boot

## Visão Geral

Este projeto implementa um sistema de autenticação seguro usando Spring Boot 3.5.0, Spring Security 6.5.0 e JWT (JSON Web Tokens).

ATENÇÃO, o projeto está utilizando o h2 console, portanto, caso a aplicação seja reiniciada , os dados são perdidos e o fluxo precisa ser Refeito
*Ao fazer o login do Usuário, sempre utilize o token em uma variavel de ambiente no postman , e passe 

## Tecnologias Utilizadas

* Java 17
* Spring Boot 3.5.0
* Spring Security 6.5.0
* JWT (JSON Web Tokens)
* H2 Database (memória)
* Lombok
* Bean Validation

## Configuração do Ambiente

**Pré-requisitos:** Java 17+, Maven 3.6.3+, IDE (IntelliJ/Eclipse/VS Code)

1. Clone o repositório:

   ```bash
   ```

git clone \[URL\_DO\_REPOSITÓRIO]
cd trabalhoJwt

````
2. Execute a aplicação:
   ```bash
mvn spring-boot:run
````

3. Acesse:

   * API: `http://localhost:8080`
   * H2 Console: `http://localhost:8080/h2-console` (JDBC `jdbc:h2:mem:testdb`, usuário `sa`, senha em branco)

## Endpoints da API

### Autenticação

* **POST /auth/register**: registra usuário

  ```bash
  ```

curl -X POST [http://localhost:8080/auth/register](http://localhost:8080/auth/register)&#x20;
-H "Content-Type: application/json"&#x20;
-d '{"login":"sla@example.com","password":"senha123","role":"ADMIN"}'



- **POST /auth/login**: faz login
  ```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"login":"sla@example.com","password":"senha123"}'


**Resposta:** `{ "Bearer token": "<JWT>" }`

### Usuários

* **GET /api/users/me**: perfil do usuário (Bearer JWT)
* **GET /api/users**: lista todos (ADMIN)
* **PATCH /api/users/{id}**: atualiza (dono ou ADMIN)
* **DELETE /api/users/{id}**: deleta (ADMIN)

Exemplo PATCH:

`bash
curl -X PATCH http://localhost:8080/api/users/123 \
  -H "Authorization: Bearer <JWT>" \
  -H "Content-Type: application/json" \
  -d '{"login":"novo@ex.com"}'


## Fluxo de Teste

1. Registrar ADMIN: `role=ADMIN`
2. Login para obter JWT
3. Usar JWT para acessar `/api/users/me` e `/api/users`

## Segurança

* JWT expira em 10 minutos
* Senhas com BCrypt
* Autorização por roles (USER, ADMIN)

## Troubleshooting

* **403 Forbidden:** token ausente/expirado/role incorreta
* **401 Unauthorized:** credenciais inválidas
* **500 Internal Error:** ver logs, checar DB

## Estrutura do Projeto


src/
├ main/
│ ├ java/com/raul/trabalhoJwt/
│ │ ├ config/
│ │ ├ controllers/
│ │ ├ domain/
│ │ ├ repositories/
│ │ ├ security/
│ │ └ services/
│ └ resources/
│   ├ application.properties
│   └ db/migration/
└ test/



## Licença

MIT

*Desenvolvido com ❤️ por \[Seu Nome]*
