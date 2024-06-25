"""Loans' tasks for Emprestimo microservice."""

from services.accounts import AccountClient
from services.notifications import NotificationClient


def send_loan(loan):
    account_client = AccountClient()
    notification_client = NotificationClient()

    account_details = account_client.get_account_details(loan.account_number)

    recipients = [account_details['email']]
    subject = 'Emprestimo aprovado'
    body = f'Olá, seu emprestimo com valor de R$ {loan.value} foi aprovado.'
    notification_client.send_notification(recipients, subject, body)

    account_client.send_loan_value(loan.account_number, loan.value)
