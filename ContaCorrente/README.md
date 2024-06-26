Sobre o projeto de microsserviços, nosso grupo ficou responsável pelo desenvolvimento da Conta Corrente. O projeto já esta na pasta que o professor criou, com o Docker pronto, basta rodar os comandos e ele estará rodando na porta 3003, ouvindo a porta do mysql 3308. Sobre os métodos existentes, nós temos um Swagger que lista todos os endpoints (http://localhost:3003/swagger-ui/index.html). Mas resumindo, não limitando-se, os principais:

CONTAS:
/api/conta (GET): Busca e retorna todas as contas
/api/conta/{id} (GET): Busca e retorna conta pelo seu id
/api/conta/numero/{numero} (GET): Busca e retorna conta pelo seu número
/api/conta/saldo/{numero} (GET): Busca e retorna o saldo de uma conta pelo seu número
/api/conta (POST): Salva e retorna a conta salva. Para salvar uma conta, vocês devem fornecer no corpo da requisição: 
- nome (STRING): Obrigatório;
- cpf (STRING): Obrigatório e único;
- email (STRING): Obrigatório e único;
- data_de_nascimento (STRING): Obrigatório;
- telefone (STRING): Obrigatório e único;
- senha (STRING): Obrigatório;
- saldo (DOUBLE): Opcional. Caso não seja fornecida, conta será cadastrada com saldo 0.
o NUMERO da conta é gerado no cadastro e não deve ser fornecido no corpo da requisição. É também executada um encrypt na senha ao cadastrar a conta. 
/api/conta/debitar (PATCH): Retira dinheiro de uma conta e retorna esta
/api/conta/creditar (PATCH): Adiciona dinheiro em uma conta e retorna esta
Para esses dois últimos endpoints, deve ser fornecido os seguintes campos no corpo da requisição:
- numero (LONG): Obrigatório;
- valor (DOUBLE): Obrigatório;
- nome (STRING): Opcional. Caso não seja fornecido, será gerado a movimentação com nome nulo.

MOVIMENTAÇÕES:
/api/movimentacao (GET): Busca e retorna uma lista de movimentações. Para esse endpoint, podem ser preenchidos os seguintes RequestParam para filtragem:
* numero (da conta)
- nome (da movimentação)
- tipo (CREDITO ou DEBITO)
- data_igual
- data_maior_que
- data_menor_que
Para os parâmetros de data, elas devem ser preenchidas no formato YYYY-MM-DD HH:MM:SS 

Os endpoints /api/conta (GET) e /api/movimentação (GET) suportam paginação. Elas são passadas como RequestParam com o nome page, listando 10 itens para cada página. Caso não seja passada, será considerada para a requisição a primeira página.