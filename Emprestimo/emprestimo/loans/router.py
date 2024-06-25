"""Loans' FastAPI router endpoints for Emprestimo microservice."""

import datetime

from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from core.database import Database

from loans.enums import LoanStatus
from loans.models import Loan
from loans.schemas import (
    LoanListRequestSchema, LoanListResponseSchema,
    LoanCreateRequestSchema, LoanCreateResponseSchema,
    LoanValidateRequestSchema, LoanValidateResponseSchema,
)
from loans.tasks import send_loan


router = APIRouter()


@router.get('/emprestimos', response_model=LoanListResponseSchema)
async def get_loans(
    request: LoanListRequestSchema = LoanListRequestSchema(),
    database: Session = Depends(Database),
):
    loans = database.query(Loan)
    loans = loans.filter(
        Loan.status==request.status,
        Loan.account_number==request.numero_conta,
    )
    loans = loans.order_by(Loan.created_at.desc())

    return {
        'emprestimos': [
            {
                'id': str(loan.id),
                'numero_conta': loan.account_number,
                'valor': loan.value,
                'parcelas': loan.portion_amount,
                'status': loan.status,
                'created_at': loan.created_at,
                'validated_at': loan.validated_at,
            }
            for loan in loans.all()
        ],
    }


@router.post('/emprestimos', response_model=LoanCreateResponseSchema)
async def create_loan(
    request: LoanCreateRequestSchema,
    database: Session = Depends(Database),
):
    loan = Loan(
        account_number=request.numero_conta,
        portion_amount=request.parcelas,
        value=request.valor,
        status=LoanStatus.PENDING,
    )

    database.add(loan)
    database.commit()
    database.refresh(loan)

    # TODO: Task para gerar parcelas

    return {
        'id': str(loan.id),
        'numero_conta': loan.account_number,
        'valor': loan.value,
        'parcelas': loan.portion_amount,
        'status': loan.status,
        'created_at': loan.created_at,
        'validated_at': loan.validated_at,
    }


@router.post('/validar_empresitmo', response_model=LoanValidateResponseSchema)
async def validate_loan(
    request: LoanValidateRequestSchema,
    database: Session = Depends(Database),
):
    loan = database.query(Loan).filter(Loan.id==request.id).first()
    loan.status = LoanStatus.APPROVED if request.aprovado else LoanStatus.DISAPPROVED
    loan.validated_at = datetime.datetime.now()

    database.commit()
    database.refresh(loan)

    send_loan(loan)

    return {
        'id': str(loan.id),
        'numero_conta': loan.account_number,
        'valor': loan.value,
        'parcelas': loan.portion_amount,
        'status': loan.status,
        'created_at': loan.created_at,
        'validated_at': loan.validated_at,
    }
