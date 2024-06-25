"""Services' Exceptions for Emprestimo microservice."""

class AccountCommunicationException(Exception):
    """Exception raised when communicating with account fails."""
    pass


class ConfigurationCommunicationException(Exception):
    """Exception raised when communicating with configurations fails."""
    pass


class NotificationCommunicationException(Exception):
    """Exception raised when communicating with notifications fails."""
    pass
