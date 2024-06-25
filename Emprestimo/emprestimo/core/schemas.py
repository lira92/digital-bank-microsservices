"""Base Pydantic schemas for Emprestimo microservice."""

from pydantic import BaseModel


class BaseSchema(BaseModel):
    class Config:
        smart_union = True
        orm_mode = True


class RequestSchema(BaseSchema):
    pass


class ResponseSchema(BaseSchema):
    pass


class ErrorResponseSchema(ResponseSchema):
    message: str
