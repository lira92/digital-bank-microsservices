"""Services' Configuration Client for Emprestimo microservice."""

import urllib.parse

from fastapi import status
import requests

from core.settings import CONFIGURATIONS_URL, CONFIGURATIONS_ENDPOINT

from services.exceptions import ConfigurationCommunicationException


requests.packages.urllib3.disable_warnings(requests.packages.urllib3.exceptions.InsecureRequestWarning)


class ConfigurationClient:
    """
    Client API interface to communicate with BackOffice's Configurations API.
    """

    def __init__(self, url=None, verify=False, timeout=60, **kwargs):
        self.url = CONFIGURATIONS_URL if url is None else url
        self.headers = {'Accept': 'application/json'}
        self.verify = verify
        self.timeout = timeout
        self.kwargs = kwargs

    def fetch_configurations(self, endpoint=None, params=None):
        endpoint = CONFIGURATIONS_ENDPOINT if endpoint is None else endpoint
        response = requests.get(
            urllib.parse.urljoin(self.url, endpoint),
            headers=self.headers,
            verify=self.verify,
            timeout=self.timeout,
            params=params,
            **self.kwargs,
        )

        if response.status_code == status.HTTP_404_NOT_FOUND:
            raise ConfigurationCommunicationException('Configurations are not defined')

        if response.status_code != status.HTTP_200_OK:
            raise ConfigurationCommunicationException('Error fetching configurations')

        return response.json()

    def get_current_configurations(self):
        configurations = self.fetch_configurations()

        # TODO: Adicionar caching para fallback

        return {
            'taxa_juros': configurations['juros'],
            'minimo_parcelamento': configurations['minimo_parcelamento'],
            'maximo_parcelamento': configurations['maximo_parcelamento'],
        }
