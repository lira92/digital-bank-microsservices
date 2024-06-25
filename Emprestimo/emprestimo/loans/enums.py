"""Loans' enum fields for Emprestimo microservice."""

from enum import Enum


class LoanStatus(Enum):
    PENDING = 'pendente'
    APPROVED = 'aprovado'
    DISAPPROVED = 'reprovado'


class PortionStatus(Enum):
    PENDING = 'pendente'
    PAID = 'pago'
    EXPIRED = 'vencido'
