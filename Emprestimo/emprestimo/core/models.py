"""Base SQLAlchemy table models for Emprestimo microservice."""

import uuid

from sqlalchemy import Column, DateTime
from sqlalchemy.dialects.postgresql import UUID
from sqlalchemy.sql import func

from core.database import BaseModel


class UUIDBaseModel(BaseModel):
    """
    Base class for UUID-based models.
    """
    __abstract__ = True

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    created_at = Column(DateTime(timezone=True), nullable=False, server_default=func.now())
