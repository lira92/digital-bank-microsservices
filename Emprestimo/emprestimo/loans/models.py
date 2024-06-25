"""Loans' SQLAlchemy database models for Emprestimo microservice."""

from sqlalchemy import BigInteger, Column, Date, DateTime, Enum, Float, ForeignKey, Integer
from sqlalchemy.dialects.postgresql import UUID

from core.models import UUIDBaseModel
from loans.enums import LoanStatus, PortionStatus


class Loan(UUIDBaseModel):
    __tablename__ = 'loans'

    account_number = Column(BigInteger, nullable=False)
    value = Column(Float, nullable=False)
    portion_amount = Column(Integer, nullable=False)
    status = Column(Enum(LoanStatus), nullable=False, default=LoanStatus.PENDING)
    validated_at = Column(DateTime(timezone=True), nullable=True)


class Portion(UUIDBaseModel):
    __tablename__ = 'portions'

    loan_id = Column(UUID(as_uuid=True), ForeignKey('loans.id'), nullable=False)
    value = Column(Float, nullable=False)
    interest_rate = Column(Float, nullable=False)
    due_date = Column(Date, nullable=False)
    status = Column(Enum(PortionStatus), nullable=False, default=PortionStatus.PENDING)
