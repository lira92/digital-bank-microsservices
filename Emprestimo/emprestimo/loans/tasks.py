"""Loans' tasks for Emprestimo microservice."""

from services.accounts import AccountClient
from services.notifications import NotificationClient


def send_loan_value(loan):
    account_client = AccountClient()
    account_client.send_loan_value(loan.account_number, loan.value)

def send_loan_notification(loan):
    account_client = AccountClient()
    notification_client = NotificationClient()

    account_details = account_client.get_account_details(loan.account_number)

    recipients = [account_details['email']]
    subject = 'Emprestimo pendente'
    body = f'Olá, seu emprestimo com valor de R$ {loan.value} está pendente de aprovação.'
    notification_client.send_notification(recipients, subject, body)
