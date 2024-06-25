"""Loans' enum fields for Emprestimo microservice."""

from enum import Enum


class LoanStatus(Enum):
    PENDING = 'PENDENTE'
    APPROVED = 'APROVADO'
    DISAPPROVED = 'REPROVADO'


class PortionStatus(Enum):
    PENDING = 'PENDENTE'
    PAID = 'PAGO'
    EXPIRED = 'VENCIDO'
