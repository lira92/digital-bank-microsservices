# Projeto de Microsserviços - Banco digital

## Como configurar parcelamento e taxa de juros para empréstimo

```
curl --request POST \
  --url /configuracao/emprestimo \
  --header 'Content-Type: application/json' \
  --data '{
	"juros": 2.5,
	"minimo_parcelamento": 2,
	"maximo_parcelamento": 24
}'
```

## Como configurar taxa de juros para investimento

```
curl ...
```

## Como criar um prospect (que poderá vir a ter uma conta corrente)

```
curl ...
```

## Como solicitar um empréstimo

```
curl ...
```

## Como criar um objetivo de investimento

```
curl --request POST \
  --url /goals \
  --header 'Content-Type: application/json' \
  --data '{
	"target_date": "2026-04-04",
	"name": "viagem",
	"target_value": 12000,
	"deduction_day": 25,
	"account_number": 50445
  }'
```

## Como colocar dinheiro em um investimento

```
curl --request POST \
  --url /goals/contribute \
  --header 'Content-Type: application/json' \
  --data '{
	"goal_id": 2,
	"amount": 500.5
  }'
```
## Como retirar dinheiro de um investimento

```
curl --request POST \
  --url /goals/redeem \
  --header 'Content-Type: application/json' \
  --data '{
	"goal_id": 2,
	"amount": 500.5
  }'
```


## Como aprovar/negar um empréstimo

```
curl ...
```

## Como aprovar/negar um prospect

```
curl ...
```

## Como consultar extrato de uma conta

```
curl -X 'GET' \
  'http://localhost:3003/api/movimentacao?numero=00000' \
  -H 'accept: application/json'
```

## Como pagar um boleto

```
curl ...
```

### Como consultar o saldo de uma conta

```
curl -X 'GET' \
  'http://localhost:3003/api/conta/saldo/1' \
  -H 'accept: application/json'
```