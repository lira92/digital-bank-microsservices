# API de Notificações

## Descrição
Esta é a API de Notificações, que permite o envio de notificações por email. A API roda na porta `3002` e fornece um único endpoint para envio das notificações.

## Endpoint
- **URL:** `/notifications`
- **Método:** `POST`

## Como Rodar
> Esse projeto conta com um Docker para rodar. Nesse caso, cerifique-se da instalação correta dele.

Inicialmente, entre através de um terminal na pasta raiz do projeto de microsserviços, caso já não esteja nela.

Em seguida, rode o comando:
```
docker compose up
```
Isso levantará o contêiner do rabbitmq e da api de notificações.

Feito isso, o projeto está pronto para ser testado.

## Como Testar
Para testar esta API, envie uma requisição HTTP POST para o endpoint acima com as seguintes informações obrigatórias no payload:

### Payload
```json
{
  "messageRecipients": ["email1@exemplo.com", "email2@exemplo.com"],
  "messageSubject": "Assunto do email",
  "messageBody": "Corpo da mensagem. Suporta tags HTML."
}
```

- `messageRecipients` (array): Lista com os emails dos destinatários.
- `messageSubject` (string): Assunto do email.
- `messageBody` (string): Corpo da mensagem. Suporta tags HTML.

### Exemplo de Requisição
```bash
curl -X POST http://localhost:3002/notifications \
-H "Content-Type: application/json" \
-d '{
  "messageRecipients": ["email1@exemplo.com", "email2@exemplo.com"],
  "messageSubject": "Assunto do email",
  "messageBody": "<p>Corpo da mensagem</p>"
}'
```

### Resposta
O endpoint irá retornar `200 OK` caso a mensagem seja colocada na fila para envio.

## Status Codes
- `200 OK`: A mensagem foi colocada na fila para envio.
- Outros códigos de status podem ser retornados em casos de erro.

## Porta
A API roda na porta `3002`.
