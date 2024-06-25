# API de Investimentos

## Descrição
Esta é a API de Investimentos, que permite a criação de investimentos, resgate diário e aporte ao investimento. A API roda na porta `3007`.

## Endpoints
- **URL:** `/goals`
- **Método:** `POST`

```
### Payload
```json
{
	"target_date": "2026-04-04",
	"name": "viagem",
	"target_value": 12000,
	"deduction_day": 25,
	"account_number": 50445
}
```

- `target_date` (YYYY-MM-DD): Data final do investimento.
- `name` (string): nome do investimento.
- `target_value` (number): valor pretendido do investimento.
- `deduction_day` (number): dia de resgate da conta corrente para o investimento.
- `account_number` (number): número da conta bancária.

##

- **URL:** `/goals/contribute`
- **Método:** `POST`
- **Rota para tirar dinheiro da conta corrente e enviar para o investimento:**
```
### Payload
```json
{
	"goal_id":1,
	"amount": 500
}
```

- `goal_id`: ID do investimento `goal`.
- `amount`: (float) quantidade a ser resgatada.

##


- **URL:** `/goals/redeem`
- **Método:** `POST`
- **Rota para tirar dinheiro do investimento e enviar para a conta corrente:**
```
### Payload
```json
{
	"goal_id":1,
	"amount": 500
}
```

- `goal_id`: ID do investimento `goal`.
- `amount`: (float) quantidade a ser resgatada.


## Como Rodar
> Esse projeto conta com um Docker para rodar. Nesse caso, cerifique-se da instalação correta dele.

Inicialmente, entre através de um terminal na pasta raiz do projeto de microsserviços, caso já não esteja nela.

Em seguida, rode o comando:
```
docker compose up
```
Isso levantará o contêiner da aplicação e seu banco de dados.