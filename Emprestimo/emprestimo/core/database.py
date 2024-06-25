"""SQLAlchemy database configuration for Emprestimo microservice."""

from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, declarative_base

from core.settings import POSTGRES_URL, POSTGRES_TIMEZONE


engine = create_engine(
    f'postgresql+psycopg2://{POSTGRES_URL}',
    connect_args={
        'options': f'-c timezone={POSTGRES_TIMEZONE}',
    },
)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
BaseModel = declarative_base()


async def Database():
    database = SessionLocal()
    try:
        yield database
    finally:
        database.close()
