# RPG WayTSystem World
## :world_map: Índice

- [Sobre o Projeto](#information_source-sobre-o-projeto)
- [Funcionalidades](#hammer-funcionalidades-do-projeto)
- [Demonstração de uso](#video_camera-demonstração-de-uso)
- [Instalação](#hammer_and_wrench-instalação)
- [Tecnologias e Recursos](#open_book-tecnologias-e-recursos-utilizados)
- [Pessoas Desenvolvedoras do Projeto](#smile-autores)
## :information_source: Sobre o projeto
![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=CONCLUÍDO&color=GREEN&style=for-the-badge)<br>
Este projeto é a resolução de um desafio proposto na formação Java Fullstack da Proway em parceria como a T-Systems.<br>
O objetivo é fazer rpg de turno aplicando os conceitos de POO.
## :hammer: Funcionalidades do projeto

- Permite criar diferentes personagens, sendo em um novo jogo ou carregando um jogo salvo.
- É possível escolher um de seus personagens a qualquer momento navegando pelos menus.
- Personagem ganha experiência ao derrotar um monstro, conforme vai evoluindo seu nível, a quantidade necessária para o próximo nível aumenta de forma escalável.
- Personagem pode equipar items, dividos no momento em duas categorias: armor e weapon, um personagem só pode equipar uma arma de sua classe, monstros podem equipar qualquer arma.
- Shop com opção de compra ou venda de itens.
- Classes e personagens tem fraquezas contra certos tipos de habilidades, o dano é aumentado quando se usa x habilidade contra inimigo que tem x fraqueza.
- Monstros com niveis de monstro, mini chefe, chefe.
- Inimigos tem probabilidade de droparem items ao serem derrotados, podendo ser armor ou weapon, sua raridade é dividida em: COMMON, RARE, SUPER_RARE, e EPIC. Quanto mais raro o item, melhor são seus atributos.
- Seu personagem pode subir de nível conforme derrota monstros, ele também pode lançar magias que causam dano e debuffs nos inimigos.
- Inimigos podem fugir ao atingir uma certa porcentagem de vida ou utilizar cura.
- Inimigos são spawnados entre 1 e 5 por busca, os niveis deles variam entre 5 acima ou abaixo do nivel do personagem atual.
- É possível adicionar novos efeitos, classes ou monstros com certa facilidade, também é possível balancear o jogo conforme o gosto, implementar um sistema de shop/loja, de escolha de dificuldade, etc..
- Banco de dados em memória para armazenar seu progresso, sem a necessidade de novas instalações, hospedagem ou precocupação com consultas.

## :video_camera: Demonstração de uso

<details>
    <summary> Iniciando novo jogo</summary>
      <img src="https://github.com/getwlad/rpg-turno/assets/102919718/74a64aa4-e714-43b8-a737-499e19001fd8" alt="novo">
</details>

<details>
    <summary>Criando e excluindo personagem</summary>
    <img src="https://github.com/getwlad/rpg-turno/assets/102919718/98197611-24f1-4d1c-8811-5744b5dd28e48" alt="criando">
</details>

<details>
    <summary>Iniciando uma batalha</summary>
    <img src="https://github.com/getwlad/rpg-turno/assets/102919718/9ba9a63b-44bb-406c-ba9d-1427e5d0b877" alt="iniciando">
</details>

<details>
    <summary>Ativando magias, monstro fugindo(probabilidade configurável, porcentagem de vida configurável)</summary>
    <img src="https://github.com/getwlad/rpg-turno/assets/102919718/4966a2bc-7dee-464e-a051-31728802d15b" alt="ativando">
</details>

<details>
    <summary>Equipando item</summary>
    <img src="https://github.com/getwlad/rpg-turno/assets/102919718/cbb71350-6b40-48c0-b5da-c680e41090c2" alt="equipando">
</details>

<details>
    <summary>Desequipando item</summary>
    <img src="https://github.com/getwlad/rpg-turno/assets/102919718/c19e3a3c-1059-437e-a390-8da7dfb258ff" alt="desequipando">
</details>

<details>
    <summary>Resistindo a magia</summary>
    <img src="https://github.com/getwlad/rpg-turno/assets/102919718/b5fc3133-6216-43c1-aaae-7653f1b929f7" alt="resistindo">
</details>


## :hammer_and_wrench: Instalação
Você pode obter uma cópia do projeto através do comando
```bash
git clone https://github.com/getwlad/rpg-turno.git
```
Utilize uma IDE compatível com java para executar a aplicação a partir da classe Main, a aplicação será executada no terminal.

## :open_book: Tecnologias e Recursos Utilizados

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/pt-BR/)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
[![Git](https://img.shields.io/badge/GIT-E44C30?&style=for-the-badge&logo=git&logoColor=white)](https://git-scm.com/doc)
[![GitHub](https://img.shields.io/badge/GitHub-100000?&style=for-the-badge&logo=github&logoColor=white)](https://github.com/)
![SQLite](https://img.shields.io/badge/sqlite-%2307405e.svg?style=for-the-badge&logo=sqlite&logoColor=white)

## :smile: Autores
[<img loading="lazy" src="https://avatars.githubusercontent.com/u/102919718?v=4" width=115><br><sub>Wladmir Rodriuges</sub>](https://github.com/getwlad) 
