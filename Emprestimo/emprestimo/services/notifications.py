"""Services' Notification Client for Emprestimo microservice."""

import json
import urllib.parse

from fastapi import status
import requests

from core.settings import NOTIFICATIONS_URL, NOTIFICATIONS_ENDPOINT

from services.exceptions import NotificationCommunicationException


requests.packages.urllib3.disable_warnings(requests.packages.urllib3.exceptions.InsecureRequestWarning)


class NotificationClient:
    """
    Client API interface to communicate with the Notifications API.
    """

    def __init__(self, url=None, verify=False, timeout=60, **kwargs):
        self.url = NOTIFICATIONS_URL if url is None else url
        self.headers = {'Accept': 'application/json'}
        self.verify = verify
        self.timeout = timeout
        self.kwargs = kwargs

    def send_notification(self, recipients, subject, body, endpoint=None, params=None):
        endpoint = NOTIFICATIONS_ENDPOINT if endpoint is None else endpoint

        data = json.dumps({
            'messageRecipients': recipients,
            'messageSubject': subject,
            'messageBody': body,
        })

        response = requests.get(
            urllib.parse.urljoin(self.url, endpoint),
            data=data,
            headers=self.headers,
            verify=self.verify,
            timeout=self.timeout,
            params=params,
            **self.kwargs,
        )
        if response.status_code != status.HTTP_200_OK:
            raise NotificationCommunicationException('Error sending notification')
