from typing import Union

from fastapi import FastAPI

from models.payment import Payment
import requests


BASE_URL = "http://172.18.113.235:3003/api/"

numero_boleto = "34191.79001 01043.510047 91020.150008 1 97450026000"

app = FastAPI()


@app.post("/transferir")
def transferir(payment: Union[Payment, str] = None):

    requests.patch(
        BASE_URL + "conta/creditar",
        json={
                "nome": "Pagamento",
                "numero": payment.receiver,
                "valor": payment.value
            }
        )
    requests.patch(
        BASE_URL + "conta/debitar",
        json={
                "nome": "Pagamento",
                "numero": payment.sender,
                "valor": payment.value
            }
        )

    # send notification
    return {"message": "Pagamento realizado com sucesso"}

@app.post("/pagar-boleto")
def pagar_boleto(boleto: str):
    requests.patch(
        BASE_URL + "boleto/pagar",
        json={
            "boleto": boleto
        }
    )

    return {"message": "Boleto pago com sucesso"}
