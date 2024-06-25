"""Loans' FastAPI router endpoints for Emprestimo microservice."""

from fastapi import APIRouter, Depends, HTTPException
from pydantic import parse_obj_as
from sqlalchemy.orm import Session

from core.database import Database

from loans.enums import Status
from loans.models import Loan
from loans.schemas import (
    LoanListRequestSchema, LoanListResponseSchema,
    LoanCreateRequestSchema, LoanCreateResponseSchema,
    LoanValidationRequestSchema, LoanValidationResponseSchema,
)


router = APIRouter()


@router.get('/emprestimos', response_model=LoanListResponseSchema)
async def get_loans(
    request: LoanListRequestSchema = LoanListRequestSchema(),
    database: Session = Depends(Database),
):


    loans = database.query(Loan)
    # loans = loans.filter(
    #     Loan.status==request.status,
    #     Loan.account_number==request.numero_conta,
    # )
    loans = loans.order_by(Loan.created_at.desc())

    return {
        'emprestimos': [
            {
                'id': str(loan.id),
                'numero_conta': loan.account_number,
                'valor': loan.value,
                'status': loan.status,
                'created_at': loan.created_at,
                'validated_at': loan.validated_at,
            }
            for loan in loans.all()
        ],
    }


@router.post('/emprestimos', response_model=LoanCreateResponseSchema)
async def get_loans(
    request: LoanCreateRequestSchema,
    database: Session = Depends(Database),
):
    loan = Loan(
        account_number=request.numero_conta,
        value=request.valor,
        status=Status.PENDING,
    )

    database.add(loan)
    database.commit()
    database.refresh(loan)

    return {
        'id': str(loan.id),
        'numero_conta': loan.account_number,
        'valor': loan.value,
        'status': loan.status,
        'created_at': loan.created_at,
        'validated_at': loan.validated_at,
    }
