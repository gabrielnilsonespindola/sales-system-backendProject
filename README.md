# Projeto SalesSystem

# Sobre o Projeto

● Este projeto simula uma situação de venda, onde um cliente cadastrado pelo usuario, poderá realizar um pedido ("Order") contendo os dados necessários para sua realização.

● O sistema foi desenvolvido em Java, utilizando o framework Spring Boot, com foco em boas práticas de API REST, validações de dados, organização de entidades e clareza nas regras de domínio. 

● SalesSystem possui perfis roles, "basic" e "admin" com sua devidas atribuições e autorizações para as requisições API.

● Possui Token JWT para Autorização/Autenticação do sistema com SpringSecurity.

● Login e cadastro são livres, sem necessidade de autenticação. 

● Cadastro de cliente somente com CPF valido e sem duplicação.

● Venda somente se o estoque nao estiver vazio e cliente cadastrado.

● Testes de Unidade aplicado a camada "Service" do projeto, validando regras de negócio.

# Tecnologias Utilizadas 

● Java 21

● Spring Boot 3.5

● Spring Security + JWT 

● OAuth 2.0 

● Spring Data JPA 

● H2 DB

● Maven

● JUnit 5 / Mockito

● Postman

# Como executar o projeto
# Back end

● Pré-requisitos: 

● Java 21

● STS IDE

● Clonar repositório

git clone https://github.com/gabrielnilsonespindola/sales-system-backendProject.git

● Entrar na pasta do projeto back end

cd salesSystem

● Executar o projeto

./mvnw spring-boot:run

# Importando a collection do Postman
Para facilitar os testes dos endpoints da API, este projeto inclui uma collection do Postman.

# Como importar:

● Abra o Postman

● Clique em "Import" no canto superior esquerdo

● Selecione a aba "File"

● Clique em "Upload Files"

● Escolha o arquivo saleSystem.postman_collection.json (incluso neste repositório na pasta raiz do projeto, subpasta "postman")

● Clique em "Import"

# Testando aplicação via postman

● Iniciar login de usuario, podendo ser diretamente pelo perfil "admin" que possue todas as autorizações, ou cadastrar novo usuario do tipo "basic" e realizar login, lembrando que o perfil basic não tem acesso a todas as chamadas da aplicação.

● Após efetuar o login, no body response da requisição do postman, sera gerado a "acessToken" que devera ser copiada e colada no campo "Token" na aba "Authorization" - opção "Bearer Token" para realizar chamada da requisição.

● Login e cadastro de usuario não necessita de autenticação, por tanto fica livre a chamada da requisição.

# Testando aplicação via H2 BD Test

● Necessário a aplicação estar rodando, e com a porta certa, caso queira usar outra, devera alterar no application do projeto.

● Abra o navegador do seu computador, e no campo da URL inserir : http://localhost:8080/h2-console .

● Utilizar login e senha registrado em "application-test-properties" .

● Logando no H2, com a aplicação rodando, selecione a tabela desejada, confira os dados e execute comandos SQL para interação com o banco de dados.





# Autor : 

Gabriel Nilson Espindola

https://www.linkedin.com/in/gabriel-nilson-espindola-065694297/


