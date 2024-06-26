from datetime import datetime
import time
import json
from fastapi.responses import JSONResponse
from typing import List, Union

from fastapi import FastAPI

from models.payment import Boleto, Payment, ScheduledBoleto, ScheduledBoletoStatus, ScheduledPayment, ScheduledPaymentStatus
import requests


CONTA_CORRENTE_BASE_URL = "http://contacorrente:3003/api/"
URL_NOTIFICACAO = "http://notifications-api:3002/notifications"

numero_boleto = "34191.79001 01043.510047 91020.150008 1 97450026000"

app = FastAPI()

import requests

#TODO adicionar nas funções de debitar, creditar e de agendamento a chamada da função de notificação
#ver como passar o email ou direto a conta e pegar o email aqui dentro
def enviar_notificacao_agendamento(conta, sender: int, value: float, data_agendamento: str):
    notification_payload = {
        "messageRecipients": [conta.json()['email']],
        "messageSubject": "Pagamento Agendado",
        "messageBody": f"<p>Um pagamento de R${value:.2f} foi agendado por {sender} em {data_agendamento}.</p>"
    }
    try:
        response = requests.post(URL_NOTIFICACAO, json=notification_payload)
        response.raise_for_status()
        print("Notificação de agendamento enviada com sucesso")
    except requests.exceptions.RequestException as e:
        raise RuntimeError(f"Erro ao enviar notificação de agendamento: {str(e)}")
#ver como passar o email ou direto a conta e pegar o email aqui dentro
def enviar_notificacao_debito(recipient, conta: int, value: float):
    notification_payload = {
        "messageRecipients": [recipient.json()['email']],
        "messageSubject": "Transferência Realizada",
        "messageBody": f"<p>Um débito de R${value:.2f} foi realizado em sua conta {conta}.</p>"
    }
    try:
        response = requests.post(URL_NOTIFICACAO, json=notification_payload)
        response.raise_for_status()
        print("Notificação de débito enviada com sucesso")
    except requests.exceptions.RequestException as e:
        raise RuntimeError(f"Erro ao enviar notificação de débito: {str(e)}")
#ver como passar o email ou direto a conta e pegar o email aqui dentro
def enviar_notificacao_credito(recipient, conta: int, value: float):
    notification_payload = {
        "messageRecipients": [recipient.email],
        "messageSubject": "Crédito Realizado",
        "messageBody": f"<p>Uma transferência de R${value:.2f} foi realizada em sua conta {conta}.</p>"
    }
    try:
        response = requests.post(URL_NOTIFICACAO, json=notification_payload)
        response.raise_for_status()
        print("Notificação de crédito enviada com sucesso")
    except requests.exceptions.RequestException as e:
        raise RuntimeError(f"Erro ao enviar notificação de crédito: {str(e)}")


def debitar_valor(numero_conta: int, valor: float) -> None:
    try:
        response = requests.patch(
            f"{CONTA_CORRENTE_BASE_URL}conta/debitar",
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
            f"{CONTA_CORRENTE_BASE_URL}conta/creditar",
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
        response = requests.get(f"{CONTA_CORRENTE_BASE_URL}conta/saldo/{numero_conta}")
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
        response = requests.get(f"{CONTA_CORRENTE_BASE_URL}conta/numero/{numero_conta}")

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
   - sender (int): Número da conta do rementente.
   - receiver (int): Número da conta do recebedor.


    Returns:
    - JSONResponse: Resposta com o resultado do débito.
    """
    if payment.value <= 0:
        return JSONResponse(status_code=400, content={"message": "Valor da transferência deve ser positivo"})
    if(payment.sender == payment.receiver):
        return JSONResponse(status_code=400, content={"message": "Conta remetente e conta recebedor devem ser diferentes"})

    print(obter_conta(payment.sender).json()['email'])

    try:
        if(obter_conta(payment.sender) is None):
           return JSONResponse(status_code=400, content={"message": "Conta de remetente inexistente"})
        if(obter_conta(payment.receiver) is None):
            return JSONResponse(status_code=400, content={"message": "Conta de recebedor inexistente"})
        if obter_saldo(payment.sender) < payment.value:
            return JSONResponse(status_code=400, content={"message": "Saldo do remetente insuficiente"})

        debitar_valor(payment.sender, payment.value)
        creditar_valor(payment.receiver, payment.value)

        enviar_notificacao_debito(obter_conta(payment.sender), payment.sender, payment.value)

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
def pagar_boleto(boleto: Boleto):
    """
    Endpoint para pagar um boleto na conta especificada.

    Args:
    - boleto (str): String do boleto com 47 dígitos.
    - numero_conta (int): Número da conta.

    Returns:
    - JSONResponse: Resposta com o resultado do débito.
    """
    try:
        valor = decode_boleto(boleto.boleto)
    except ValueError as e:
        return JSONResponse(status_code=400, content={"erro": str(e)})

    try:
        saldo = obter_saldo(boleto.sender)

    except ValueError as e:
        return JSONResponse(status_code=400, content={"erro": str(e)})
    except ConnectionError as e:
        return JSONResponse(status_code=500, content={"erro": str(e)})
    except RuntimeError as e:
        return JSONResponse(status_code=500, content={"erro": str(e)})

    if saldo < valor:
        return JSONResponse(status_code=400, content={"falha": "Saldo insuficiente", "saldo": formatar_valor(saldo), "valor": formatar_valor(valor)})
    if(valor == 0):
        return JSONResponse(status_code=400, content={"erro": "Boleto inválido"})
    try:
        debitar_valor(boleto.sender, valor)
    except ConnectionError as e:
        return JSONResponse(status_code=500, content={"erro": str(e)})
    except RuntimeError as e:
        return JSONResponse(status_code=500, content={"erro": str(e)})

    return JSONResponse(status_code=200, content={"mensagem": "Pagamento realizado com sucesso", "valor pago": formatar_valor(valor), "novo saldo": formatar_valor(obter_saldo(boleto.numero_conta))})


# Lista em memória para armazenar os agendamentos
agendamentos: List[ScheduledPaymentStatus] = []

def is_valid_date(date_string):
    try:
        datetime.strptime(date_string, "%Y-%m-%d")
        return  True
    except ValueError:
        return  False

@app.post("/agendar-transferencia")
def agendar_transferencia(pagamentomodel: ScheduledPayment):
    """
    Endpoint para agendar uma transferencia.

    Args:
    - valor (float): Valor da transferência.
    - sender (int): Número da conta do rementente.
    - receiver (int): Número da conta do recebedor
    - data_agendamento (date): Data de agendamento.
    - descricao (str): Descricão do agendamento.
    Returns:
    - JSONResponse: Resposta com o resultado do débito.
    """
    pagamento = ScheduledPaymentStatus(
        data_agendamento=pagamentomodel.data_agendamento,
        value=pagamentomodel.value,
        sender=pagamentomodel.sender,
        receiver=pagamentomodel.receiver,
        descricao=pagamentomodel.descricao,
        realizado = False
    )

    try:
        if not is_valid_date(str(pagamento.data_agendamento)):
            return  JSONResponse(status_code=400, content={"message": "Data de agendamento inválida"})

        if pagamento.data_agendamento < datetime.now().date():
            return  JSONResponse(status_code=400, content={"message": "Data de agendamento precisa ser uma data futura"})

        conta_sender = obter_conta(pagamento.sender)
        if(conta_sender is None):
            return  JSONResponse(status_code=400, content={"message": "Conta de rementente inexistente"})

        if(obter_conta(pagamento.receiver) is None):
            return  JSONResponse(status_code=400, content={"message": "Conta de recebedor inexistente"})


        agendamentos.append(pagamento)
        enviar_notificacao_agendamento(conta_sender, pagamento.sender, pagamento.value, str(pagamento.data_agendamento))
        if(pagamento.value > obter_saldo(pagamento.sender)):
            return {
                "Mensagem": "Transferência agendada, certifique-se de ter saldo suficiente na data de pagamento.",
                "Data": str(pagamentomodel.data_agendamento),
                "Valor": f"R$ {pagamentomodel.value:.2f}",
                "Descrição": pagamentomodel.descricao,
                "Situação": "Agendado"
            }
        else:
            return  {"mensagem": "Transferência agendada com sucesso",
                    "Data": str(pagamento.data_agendamento),
                    "Valor": formatar_valor(pagamento.value),
                    "Descricão": pagamento.descricao,
                    "Situação": "Agendado" if not pagamento.realizado else "Realizado"}


    except ValueError as e:
        return JSONResponse(status_code=400, content={"message": str(e)})
    except ConnectionError as e:
        return JSONResponse(status_code=500, content={"message": str(e)})
    except RuntimeError as e:
        return JSONResponse(status_code=500, content={"message": str(e)})


agendamentosboleto: List[ScheduledBoleto] = []

@app.post("/agendar-boleto")
def agendar_boleto(boleto: ScheduledBoleto):
    """
    Endpoint para agendar pagamento de um boleto.

    Args:
    - boleto (str): String do boleto com 47 dígitos.
    - sender (int): Número da conta do rementente.
    - data_agendamento (date): Data de agendamento.
    - descricao (str): Descricão do agendamento.
    Returns:
    - JSONResponse: Resposta com o resultado do débito.
    """

    boletoagendado = ScheduledBoletoStatus(
        boleto=boleto.boleto,
        sender=boleto.sender,
        data_agendamento=boleto.data_agendamento,
        descricao=boleto.descricao,
        value = 0
    )

    if not is_valid_date(str(boletoagendado.data_agendamento)):
            return  JSONResponse(status_code=400, content={"erro": "Data de agendamento inválida"})
    if boletoagendado.data_agendamento < datetime.now().date():
            return  JSONResponse(status_code=400, content={"message": "Data de agendamento precisa ser uma data futura"})

    try:
        boletoagendado.value = decode_boleto(boleto.boleto)
        print(boletoagendado.value)
    except ValueError as e:
        return JSONResponse(status_code=400, content={"erro": str(e)})

    try:
        boletoagendado.value = decode_boleto(boletoagendado.boleto)
    except ValueError as e:
        return JSONResponse(status_code=400, content={"erro": str(e)})
    if( boletoagendado.value == 0):
        return JSONResponse(status_code=400, content={"erro": "Boleto inválido"})
    conta = obter_conta(boletoagendado.sender)

    if(conta is None):
        return  JSONResponse(status_code=400, content={"message": "Conta inexistente"})

    conta.json()
    agendamentosboleto.append(boletoagendado)
    response =enviar_notificacao_agendamento(conta, boletoagendado.sender, boletoagendado.value, str(boletoagendado.data_agendamento))
    if(boletoagendado.value > obter_saldo(boletoagendado.sender)):
            if(response.status_code == 200):
                return {
                    "Mensagem": "Boleto agendado com sucesso, certifique-se de ter saldo suficiente na data de pagamento.",
                    "Data": str(boletoagendado.data_agendamento),
                    "Valor": f"R$ {boletoagendado.value:.2f}",
                    "Descrição": boletoagendado.descricao,
                    "Situação": "Agendado"
                }
            else:
                return  {"message": "Boleto agendado com sucesso",
                        "Data": str(boletoagendado.data_agendamento),
                        "Valor": f"R$ {boletoagendado.value:.2f}",
                        "Descricão": boletoagendado.descricao,
                        "Situação": "Agendado" if not boletoagendado.realizado else "Realizado"}
    else:
            return  {"mensagem": "Boleto agendado com sucesso",
                    "Data": str(boletoagendado.data_agendamento),
                    "Valor": f"R$ {boletoagendado.value:.2f}",
                    "Descricão": boletoagendado.descricao,
                    "Situação": "Agendado" if not boletoagendado.realizado else "Realizado"}


@app.get("/agendamentos")
def listagem_agendamentos():
    agendamentos_formatados = []
    for pagamento in agendamentos:
        agendamentos_formatados.append({
            "Data": str(pagamento.data_agendamento),
            "Valor": formatar_valor(pagamento.value),
            "Descrição": pagamento.descricao,
            "Situação": "Agendado" if not pagamento.realizado else "Pagamento Realizado"
        })
    return agendamentos_formatados
#função em andamento
@app.get("/agendamentos-boletos")
def listagem_agendamentos_boleto():
    agendamentos_boleto_formatados = []
    for boleto in agendamentosboleto:
        agendamentos_boleto_formatados.append({
            "Data": str(boleto.data_agendamento),
            "Valor": formatar_valor(boleto.value),
            "Descrição": boleto.descricao,
            "Situação": "Agendado" if not boleto.realizado else "Pagamento Realizado"
        })
    return agendamentos_boleto_formatados

# Verificar isso depois
def verificar_pagamentos_agendados():
    while True:
        agora = datetime.now().date()
        for pagamento in agendamentos:
            if pagamento.data_agendamento <= agora and not pagamento.realizado:
                try:
                    debitar_valor(pagamento.sender, pagamento.value)
                    creditar_valor(pagamento.receiver, pagamento.value)
                    pagamento.realizado = True
                    print(f"Pagamento agendado realizado: {pagamento.sender} -> {pagamento.receiver}, valor: {pagamento.value}")
                except Exception as e:
                    print(f"Erro ao realizar pagamento agendado para a conta {pagamento.sender}: {str(e)}")
        print("Verificando pagamentos agendados")
        time.sleep(10)
