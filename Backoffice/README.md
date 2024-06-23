# Como rodar
``` docker compose build ```

Se você quer rodar com o modo debug(binding.pry) ativado:

``` docker compose up -d && docker attach $(docker compose ps -q api) ```

Se não rode apenas:

``` docker compose up ```
## Configs
- Para alterar os endpoints da aplicação, basta modificar o arquivo config/application.yml.
- Todos os endpoints externos foram feitos nesse projeto, afim de utilizar como testes para testar o funcionamento da requisição, então para ter o uso completo será necessário alterar o endpoint do application.yml.
- Em ambiente de Dev e utilizando os endpoints de teste, o sistema pode acabar quebrando por ter atingido o máximo de threads, sendo necessário rodar docker compose down e docker compose up denovo.

# Tecnologias
- Rails 7.1.3.4
- Ruby 3.2.2
- Postgresql

Principais Gems:
- Foreman
- Faraday
- Figaro
- Puma
