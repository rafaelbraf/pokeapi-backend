# Poké API - BackEnd

## Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas em seu sistema:

- Git: [Instruções de Instalação do Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
- Docker: [Instruções de Instalação do Docker](https://docs.docker.com/get-docker/)

## Clonando o Repositório

Para começar, clone este repositório para sua máquina local. Abra o terminal e execute o seguinte comando:

```bash
git clone https://github.com/rafaelbraf/pokeapi-backend.git
```

## Gerando a Imagem Docker

Agora que você possui o repositório em sua máquina, siga estas etapas para gerar a imagem Docker:

1. Navegue para o diretório do projeto:
```bash
cd pokeapi-backend
```

2. Construa a imagem Docker executando o seguinte comando:
```bash
docker build -t <nome-da-imagem> .
```
- Obs: Em "nome-da-imagem" escolha o nome que deseja dar a imagem. Por exemplo: poke-api-backend.

   O ponto final . indica que você está executando o comando no diretório atual, onde está localizado o Dockerfile.

3. Uma vez que a construção seja concluída, você pode executar um contêiner baseado na imagem gerada com o seguinte comando:
```bash
docker run -p 8080:8080 <nome-da-imagem>
```

## Fazendo requisições a API

4. Use o Postman ou Insomnia

 Agora você pode usar o Postman ou o Insomnia para fazer requisições à API da aplicação:
   1. Abra o Postman ou o Insomnia.
   2. Crie uma nova requisição.
   3. Selecione GET como método da requisição.
   4. Digite a URL "http://localhost:8080/pokemons".
     - Tipos de Requisições:
       - http://localhost:8080/pokemons (Retorna todos os Pokémons)
       - http://localhost:8080/pokemons?name={namePokemon} (Retorna os Pokémos de acordo o nome que foi passado como parâmetro)
       - http://localhost:8080/pokemons?sort={typeSort} (Retorna todos os Pokémons ordenados como o usuário decidir, podendo ser de A a Z ou de Z a A)
       - http://localhost:8080/pokemons?name={namePokemon}&sort={typeSort} (Retorna os Pokémons filtrados de acordo com o nome que foi passado e ordenados como o usuário decidir)
       - http://localhost:8080/pokemons/highlight (Retorna todos os Pokémons)
       - http://localhost:8080/pokemons/highlight?name={namePokemon} (Retorna os Pokémons filtrados de acordo com o nome que foi passado com highlights)
       - http://localhost:8080/pokemons/highlight?name={namePokemon}&sort={typeSort} (Retorna todos os Pokémons filtrados e ordenados como o usuário decidir com highlights)
   5. Envie a requisição e veja a resposta retornada pela API.
