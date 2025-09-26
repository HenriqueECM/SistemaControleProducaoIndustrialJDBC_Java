# Sistema de Controle de Produção Industrial

## Desafio

Este projeto foi desenvolvido como parte de um desafio prático para criar um sistema CRUD integrado com JDBC, utilizando Java. O objetivo principal foi exercitar habilidades em programação estruturada, conexão e manipulação de banco de dados, além da aplicação correta da Orientação a Objetos (Model + DAO) e das regras de negócio.

## Descrição Geral

O sistema foi criado para gerenciar a produção industrial de fábricas, permitindo:

- **Cadastro de usuários** com autenticação via CPF e senha.
- **Cadastro e gerenciamento de produtos**, incluindo categoria e quantidade produzida.
- **Gestão de setores de produção**, com associação de máquinas.
- **Cadastro e controle de máquinas**, incluindo setor associado e status operacional (`OPERACIONAL` / `EM_PRODUCAO`).
- **Cadastro e controle de matérias-primas** e estoque.
- **Gestão de ordens de produção**, com funcionalidades para criar, executar, consumir matérias-primas, atualizar estoque e status de produção.

## Objetivos Avaliados

- Uso do **JDBC** para conexão e manipulação do banco de dados.
- Domínio de **comandos SQL** para resolver diferentes situações do sistema.
- Aplicação efetiva dos conceitos de **Orientação a Objetos** (Model e DAO).
- Implementação correta e estruturada das **regras de negócio** propostas.

## Funcionalidades

- **Produtos**: Cadastro, definição de categoria, acompanhamento de quantidade produzida.
- **Setores**: Cadastro e associação de máquinas a setores específicos.
- **Máquinas**: Cadastro, vinculação ao setor, controle do status operacional.
- **Matérias-Primas**: Cadastro e controle do estoque disponível.
- **Ordens de Produção**: Criação, execução, controle de consumo de matérias-primas, atualização de estoque e status da produção.

## Tecnologias Utilizadas

- **Java**
- **JDBC**
- **MySQL** (ou outro banco relacional)
- Programação estruturada e Orientada a Objetos
