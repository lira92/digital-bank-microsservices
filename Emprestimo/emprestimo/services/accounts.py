"""Services' Account Client for Emprestimo microservice."""

import urllib.parse

from fastapi import status
import requests

from core.settings import ACCOUNTS_URL, ACCOUNT_DETAILS_ENDPOINT, ACCOUNT_CREDIT_ENDPOINT

from services.exceptions import AccountCommunicationException


requests.packages.urllib3.disable_warnings(requests.packages.urllib3.exceptions.InsecureRequestWarning)


class AccountClient:
    """
    Client API interface to communicate with the Accounts API.
    """

    def __init__(self, url=None, verify=False, timeout=60, **kwargs):
        self.url = ACCOUNTS_URL if url is None else url
        self.headers = {'Accept': 'application/json'}
        self.verify = verify
        self.timeout = timeout
        self.kwargs = kwargs

    def get_account_details(self, account_number, endpoint=None, params=None):
        endpoint = ACCOUNT_DETAILS_ENDPOINT if endpoint is None else endpoint
        endpoint = endpoint.format(numero=account_number)

        response = requests.get(
            urllib.parse.urljoin(self.url, endpoint),
            headers=self.headers,
            verify=self.verify,
            timeout=self.timeout,
            params=params,
            **self.kwargs,
        )
        if response.status_code != status.HTTP_200_OK:
            raise AccountCommunicationException('Error getting account details')

        return response.json()

    def send_loan_value(self, account_number, value, endpoint=None, params=None):
        endpoint = ACCOUNT_CREDIT_ENDPOINT if endpoint is None else endpoint
        endpoint = endpoint.format(numero=account_number)

        data = {
            'numero': account_number,
            'valor': value,
        }

        response = requests.patch(
            urllib.parse.urljoin(self.url, endpoint),
            data=data,
            headers=self.headers,
            verify=self.verify,
            timeout=self.timeout,
            params=params,
            **self.kwargs,
        )
        if response.status_code != status.HTTP_200_OK:
            raise AccountCommunicationException('Error sending loan value')
