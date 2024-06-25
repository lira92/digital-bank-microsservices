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
      "account_id": 1,
      "goal_name": "name",
      "target_value": 1000.50,
      "target_date": "2024-01-30",
      "deduction_date": 15
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
