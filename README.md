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
      "user_id": 1,
      "goal_name": "name",
      "goal_amount": 1000.50,
      "limit_date": "2024-01-30",
      "discount_date": "2025-05-04" 
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
curl ...
```

## Como pagar um boleto

```
curl ...
```
