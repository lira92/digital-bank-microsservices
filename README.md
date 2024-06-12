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
curl ...
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