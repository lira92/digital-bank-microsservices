"""Loans' enum fields for Emprestimo microservice."""

from enum import Enum


class Status(Enum):
    PENDING = 'PENDENTE'
    APPROVED = 'APROVADO'
    DISAPPROVED = 'REPROVADO'
