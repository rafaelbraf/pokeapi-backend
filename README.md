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
cd backendchallenger
```

2. Construa a imagem Docker executando o seguinte comando:
```bash
docker build -t <nome-da-imagem> .
```
- Obs: Em "<nome-da-imagem>" escolha o nome que deseja dar a imagem. Por exemplo: poke-api-backend.



