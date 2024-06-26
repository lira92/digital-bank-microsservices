"""Loans' FastAPI router endpoints for Emprestimo microservice."""

import datetime

from fastapi import APIRouter, BackgroundTasks, Depends, HTTPException, status
from sqlalchemy.orm import Session

from core.database import Database

from loans.enums import LoanStatus
from loans.models import Loan
from loans.schemas import (
    LoanListRequestSchema, LoanListResponseSchema,
    LoanCreateRequestSchema, LoanCreateResponseSchema,
    LoanValidateRequestSchema, LoanValidateResponseSchema,
)
from loans.tasks import send_loan_value, send_loan_notification
from services.configurations import ConfigurationClient


router = APIRouter()


@router.get('/emprestimos', response_model=LoanListResponseSchema)
async def get_loans(
    request: LoanListRequestSchema = LoanListRequestSchema(),
    database: Session = Depends(Database),
):
    loans = database.query(Loan)
    if request.status:
        loans = loans.filter(Loan.status==request.status)
    if request.numero_conta:
        loans = loans.filter(Loan.account_number==request.numero_conta)
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
    configuration_client = ConfigurationClient()
    configurations = configuration_client.get_current_configurations()
    if (
        request.parcelas < configurations['minimo_parcelamento']
        or request.parcelas > configurations['maximo_parcelamento']
    ):
        raise HTTPException(detail='Número inválido de parcelas', status_code=status.HTTP_400_BAD_REQUEST)

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


@router.post('/validar_emprestimo', response_model=LoanValidateResponseSchema)
async def validate_loan(
    request: LoanValidateRequestSchema,
    background_tasks: BackgroundTasks,
    database: Session = Depends(Database),
):
    loan = database.query(Loan).filter(Loan.id==request.id).first()
    if loan.status != LoanStatus.PENDING:
        raise HTTPException(
            detail='Não é possível validar um emprestimo que não está pendente',
            status_code=status.HTTP_400_BAD_REQUEST,
        )

    loan.status = LoanStatus.APPROVED if request.aprovado else LoanStatus.DISAPPROVED
    loan.validated_at = datetime.datetime.now()

    if loan.status == LoanStatus.APPROVED:
        send_loan_value(loan)
        background_tasks.add_task(send_loan_notification, loan)

    database.commit()
    database.refresh(loan)

    return {
        'id': str(loan.id),
        'numero_conta': loan.account_number,
        'valor': loan.value,
        'parcelas': loan.portion_amount,
        'status': loan.status,
        'created_at': loan.created_at,
        'validated_at': loan.validated_at,
    }
