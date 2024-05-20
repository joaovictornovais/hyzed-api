<h1 align="center" style="font-weight: bold;">Hyzed-API 👕</h1>

<p align="center">
 <a href="#technologies">Tecnologias</a> • 
 <a href="#practices">Práticas adotadas</a> • 
 <a href="#started">Começando</a> • 
 <a href="#routes">API Endpoints</a>
</p>

<p align="center">
    <b>API de um e-commerce</b>
</p>

<h2 id="technologies">💻 Tecnologias</h2>

- Java;
- Spring;
- PostgreSQL.

<h2 id="practices">🧭 Práticas adotadas</h2>

- API Rest;
- SOLID;
- Testes unitários;
- Consultas com Spring Data JPA;
- Tratamento de Exceções.
- Documentação Swagger com SpringDoc OpenAPI 3

<h2 id="started">🚀 Começando</h2>

Você pode acessar o repositório do Front-ent da aplicação a partir [deste link](https://github.com/joaovictornovais/hyzed/)

<h3>Pré-requisitos</h3>

- [Java](https://www.java.com/pt-BR/)
- [PostgreSQL](https://www.postgresql.org)

<h3>Clonando</h3>

```bash
git clone git@github.com:joaovictornovais/hyzed-api.git
```

<h3>Configurando variáveis de ambiente</h2>

```yaml
SECRET={secret_senha}
```

<h3>Iniciando</h3>

```bash
$ cd hyzed-api
$ ./mvnw clean package
```

Executar a aplicação
```bash
$ java -jar target/hyzed-api-0.0.1-SNAPSHOT.jar
```

<h2 id="routes">📍 API Endpoints</h2>
​

| Rota                                                 | Descrição                                          
|------------------------------------------------------|-----------------------------------------------------
| <kbd>GET /swagger-ui/index.html                      | Página da documentação do SWAGGER
| <kbd>POST /auth/login</kbd>                          | Retorna token de acesso
| <kbd>POST /auth/register</kbd>                       | Registra nova conta e retorna token de acesso
| <kbd>POST /orders</kbd>                              | Cria uma nova ordem de pedido ao usuário logado
| <kbd>PUT /orders/{id}/status</kbd>                   | Atualiza o status de uma ordem de pedido
| <kbd>GET /products/{id}</kbd>                        | Busca um produto por ID
| <kbd>GET /products?name={name}</kbd>                 | Busca um produto pelo nome
| <kbd>POST /products </kbd>                           | Salva um produto na base de dados
| <kbd>PUT /products/{id}</kbd>                        | Atualiza as informações de um produto
| <kbd>DELETE /products/{id}</kbd>                     | Deleta um produto da base de dados
| <kbd>POST /products/{id}/images</kbd>                | Adiciona uma nova imagem ao produto
| <kbd>DELETE /products/{id}/images</kbd>              | Deleta a imagem de um produto
| <kbd>POST /products/{id}/sizes</kbd>                 | Adiciona estoque de um tamanho ao produto
| <kbd>DELETE /products/{id}/sizes</kbd>               | Remove o estoque de um tamanho do produto
| <kbd>GET /users/{id}</kbd>                           | Retorna informações mínimas de um usuário
| <kbd>GET /users/{id}/orders</kbd>                    | Retorna todas as ordens de pedido de um usuário
