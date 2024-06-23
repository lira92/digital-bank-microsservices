from fastapi.responses import JSONResponse
from typing import Union

from fastapi import FastAPI

from models.payment import Payment
import requests


BASE_URL = "http://192.168.1.110:3003/api/"

numero_boleto = "34191.79001 01043.510047 91020.150008 1 97450026000"

app = FastAPI()

def notificar_acao(acao: str) -> None:
    pass
def debitar_valor(numero_conta: int, valor: float) -> None:
    try:
        response = requests.patch(
            f"{BASE_URL}conta/debitar",
            json={
                "numero": numero_conta,
                "valor": valor,
                "nome": "Pagamento"
            }
        )
        response.raise_for_status()
    except requests.exceptions.RequestException as e:
        raise RuntimeError(f"Erro ao realizar débito: {str(e)}")
    
def creditar_valor(numero_conta: int, valor: float) -> None:
    try:
        response = requests.patch(
            f"{BASE_URL}conta/creditar",
            json={
                "numero": numero_conta,
                "valor": valor,
                "nome": "Pagamento"
            }
        )
        response.raise_for_status()
    except requests.exceptions.RequestException as e:
        raise RuntimeError(f"Erro ao realizar crédito: {str(e)}")

def decode_boleto(boleto: str) -> float:
    if len(boleto) != 47:
        raise ValueError("Boleto inválido, código deve conter 47 dígitos")
    # últimos 10 dígitos como o valor do boleto
    valor_str = boleto[-10:]
    try:
       valor = float(valor_str[:-2] + '.' + valor_str[-2:])
    #    print("valor" + valor_str)    
    except ValueError:
        raise ValueError("Formato de boleto inválido")
    return valor

def obter_saldo(numero_conta: int) -> float:
    try:
        response = requests.get(f"{BASE_URL}conta/saldo/{numero_conta}")
    except requests.exceptions.ConnectionError:
        raise ConnectionError("Não foi possível conectar ao serviço da conta")
    except requests.exceptions.RequestException as e:
        raise JSONResponse(f"Erro ao consultar saldo: {str(e)}")
    
    if response.status_code == 400:
        raise ValueError("Conta inexistente")
    if response.status_code != 200:
        raise JSONResponse("Erro ao consultar saldo")
    
    try:
        saldo = response.json()
        if saldo is None:
            raise JSONResponse("Resposta inválida do serviço de saldo")
    except ValueError:
        try:
            saldo = float(response.text)
        except ValueError:
            raise JSONResponse("Resposta inválida do serviço de saldo")    
    return saldo

def obter_conta(numero_conta: int):
    try:
        response = requests.get(f"{BASE_URL}conta/numero/{numero_conta}")
    
    except requests.exceptions.ConnectionError:
        raise ConnectionError("Não foi possível conectar ao serviço da conta")
    except requests.exceptions.RequestException as e:
        raise JSONResponse(f"Erro ao consultar conta: {str(e)}")
    
    if response.status_code == 400:
        return 
    
    return response

def formatar_valor(valor: float) -> str:
    """
    Formata um valor float como moeda brasileira.
    
    Args:
    - valor (float): Valor a ser formatado.
    
    Returns:
    - str: Valor formatado como moeda brasileira.
    """
    return f"R$ {valor:,.2f}".replace(",", "X").replace(".", ",").replace("X", ".")

@app.post("/transferir")
def transferir(payment: Payment):
    """
    Endpoint para transferir saldo de uma conta para outra.
    
    Args:
   - valor (float): Valor da transferência.
   - numero_conta (int): Número da conta do rementente.
   - numero_conta (int): Número da conta do recebedor.

    
    Returns:
    - JSONResponse: Resposta com o resultado do débito.
    """
    if payment.value <= 0:
        return JSONResponse(status_code=400, content={"message": "Valor da transferência deve ser positivo"})
    if(payment.sender == payment.receiver):
        return JSONResponse(status_code=400, content={"message": "Conta remetente e conta recebedor devem ser diferentes"})
    
    try:
        if(obter_conta(payment.sender) is None):
            return JSONResponse(status_code=400, content={"message": "Conta de remetente inexistente"})
        if(obter_conta(payment.receiver) is None):
            return JSONResponse(status_code=400, content={"message": "Conta de recebedor inexistente"})
      
        if obter_saldo(payment.sender) < payment.value:
            return JSONResponse(status_code=400, content={"message": "Saldo do remetente insuficiente"})
      
        debitar_valor(payment.sender, payment.value)
        creditar_valor(payment.receiver, payment.value)

    except ValueError as e:
        return JSONResponse(status_code=400, content={"message": str(e)})
    except ConnectionError as e:
        return JSONResponse(status_code=500, content={"message": str(e)})
    except RuntimeError as e:
        return JSONResponse(status_code=500, content={"message": str(e)})

    return JSONResponse(status_code=200, content={"Aviso": "Transferência realizada com sucesso",
                                                "Valor transferido": formatar_valor(payment.value),
                                                "Novo Saldo": formatar_valor(obter_saldo(payment.sender)),})

#34191.7900101043.51004791020.150008690000026000
@app.post("/pagar-boleto")
def pagar_boleto(boleto: str, numero_conta: int):
    """
    Endpoint para pagar um boleto na conta especificada.
    
    Args:
    - boleto (str): String do boleto com 47 dígitos.
    - numero_conta (int): Número da conta.
    
    Returns:
    - JSONResponse: Resposta com o resultado do débito.
    """
    try:
        valor = decode_boleto(boleto)
    except ValueError as e:
        return JSONResponse(status_code=400, content={"erro": str(e)})

    try:
        saldo = obter_saldo(numero_conta)

    except ValueError as e:
        return JSONResponse(status_code=400, content={"erro": str(e)})
    except ConnectionError as e:
        return JSONResponse(status_code=500, content={"erro": str(e)})
    except RuntimeError as e:
        return JSONResponse(status_code=500, content={"erro": str(e)})
    
    if saldo < valor:
        return JSONResponse(status_code=400, content={"falha": "Saldo insuficiente"})
    try:
        debitar_valor(numero_conta, valor)
    except ConnectionError as e:
        return JSONResponse(status_code=500, content={"erro": str(e)})
    except RuntimeError as e:
        return JSONResponse(status_code=500, content={"erro": str(e)})

    return JSONResponse(status_code=200, content={"mensagem": "Débito realizado com sucesso", "valor pago": formatar_valor(valor), "novo saldo": formatar_valor(obter_saldo(numero_conta))})


