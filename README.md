# Desafio de QA Automation - Accenture #

Este repositório contém a solução do desafio técnico de QA Automation para a Accenture.

### Parte 1: API

## Ferramentas Utilizadas
- [Postman](https://www.postman.com/): Para testar as APIs.

## Estrutura
- `/src/api/collection.json`: Coleção com as requisições utilizadas no desafio.
- `/src/api/environment.json`: Variáveis de ambiente.

## Como Usar
1. Importe o arquivo `collection.json` no Postman.
2. Importe o arquivo `environment.json` no Postman.
3. Execute as requisições na ordem especificada.

## Descrição das Requisições
1. **Criar Usuário**: Requisição para `POST /Account/v1/User`.
2. **Gerar Token**: Requisição para `POST /Account/v1/GenerateToken`.
3. **Confirmar Autorização**: Requisição para `POST /Account/v1/Authorized`.
4. **Listar Livros**: Requisição para `GET /BookStore/v1/Books`.
5. **Reservar Livros**: Requisição para `POST /BookStore/v1/Books`.
6. **Listar Detalhes do Usuário**: Requisição para `GET /Account/v1/User/{userID}`.

### Parte 2: FrontEnd

## Estrutura do Projeto
- `/src/drivers`: Contém o `chromedriver.exe`.
- `/src/test/java/com/accenture/br/`: Código Java organizado em pacotes.
- `/src/test/resources`: Arquivo `formdata.txt` para upload no formulário.

## Requisitos
- JDK 11 ou superior
- Maven (ou Gradle)
- Google Chrome
- ChromeDriver compatível com a versão do Chrome

## Como Executar
1. Clone o repositório:
   ```bash
   git clone https://github.com/juazneto/qa-automation-accenture.git
   cd qa-automation-accenture


## Autor
- Nome: Juarez Neto
- Contato: juazneto@gmail.com
