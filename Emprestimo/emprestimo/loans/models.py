"""Loans' SQLAlchemy database models for Emprestimo microservice."""

from sqlalchemy import BigInteger, Column, Enum, String, DateTime
from sqlalchemy.sql import func

from core.models import UUIDBaseModel
from loans.enums import Status


class Loan(UUIDBaseModel):
    __tablename__ = 'loans'

    account_number = Column(BigInteger, nullable=False)
    value = Column(String(256), nullable=False)
    status = Column(Enum(Status), nullable=False)
    validated_at = Column(DateTime(timezone=True), nullable=True)
