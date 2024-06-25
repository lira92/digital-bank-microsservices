"""Loans' Pydantic schemas for Emprestimo microservice."""

import datetime

from typing import Optional, List
from pydantic import BaseModel

from core.schemas import RequestSchema, ResponseSchema

from loans.enums import LoanStatus


### Model Schemas ###

class Loan(BaseModel):
    id: str
    numero_conta: int
    valor: float
    status: LoanStatus
    created_at: datetime.datetime
    validated_at: Optional[datetime.datetime]


### List Schemas ###

class LoanListRequestSchema(RequestSchema):
    numero_conta: Optional[int] = None
    status: Optional[LoanStatus] = None

    class Config(RequestSchema.Config):
        schema_extra = {
            'example': {
                'numero_conta': 123456789,
                'status': 'APROVADO',
            },
        }


class LoanListResponseSchema(ResponseSchema):
    emprestimos: List[Loan]

    class Config(ResponseSchema.Config):
        schema_extra = {
            'example': {
                'emprestimos': [
                    {
                        'id': '00000000-0000-0000-0000-000000000001',
                        'numero_conta': 123456789,
                        'valor': 1000.00,
                        'status': 'PENDENTE',
                        'created_at': '2021-01-01T00:00:00',
                        'validated_at': None,
                    },
                    {
                        'id': '00000000-0000-0000-0000-000000000002',
                        'numero_conta': 987654321,
                        'valor': 2000.00,
                        'status': 'APROVADO',
                        'created_at': '2021-01-02T00:00:00',
                        'validated_at': '2021-01-03T00:00:00',
                    },
                ],
            },
        }


### Create Schemas ###

class LoanCreateRequestSchema(RequestSchema):
    numero_conta: int
    parcelas: int
    valor: float

    class Config(RequestSchema.Config):
        schema_extra = {
            'example': {
                'numero_conta': 123456789,
                'valor': 1000.00,
            },
        }


class LoanCreateResponseSchema(ResponseSchema):
    id: str
    numero_conta: int
    valor: float
    status: LoanStatus
    created_at: datetime.datetime
    validated_at: Optional[datetime.datetime]

    class Config(ResponseSchema.Config):
        schema_extra = {
            'example': {
                'id': '00000000-0000-0000-0000-000000000001',
                'numero_conta': 123456789,
                'valor': 1000.00,
                'status': 'APROVADO',
                'created_at': '2021-01-01T00:00:00',
                'validated_at': '2021-01-02T00:00:00',
            },
        }


### Validate Schemas ###

class LoanValidateRequestSchema(RequestSchema):
    id: str
    aprovado: bool

    class Config(RequestSchema.Config):
        schema_extra = {
            'example': {
                'id': '00000000-0000-0000-0000-000000000001',
                'aprovado': True,
            },
        }


class LoanValidateResponseSchema(ResponseSchema):
    pass
