# Projeto de Microsserviços - Banco digital

## Como configurar parcelamento para empréstimo

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

## Como criar um possível cliente

