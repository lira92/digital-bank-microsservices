"""Settings for Emprestimo microservice."""

import os


# PostgreSQL settings

POSTGRES_HOST = os.getenv('POSTGRES_HOST', 'emprestimo_db')
POSTGRES_PORT = os.getenv('POSTGRES_PORT', 5432)
POSTGRES_DB = os.getenv('POSTGRES_DB', 'emprestimo')
POSTGRES_USER = os.getenv('POSTGRES_USER', 'emprestimo')
POSTGRES_PASSWORD = os.getenv('POSTGRES_PASSWORD', 'emprestimo')
POSTGRES_URL = os.getenv(
    'POSTGRES_URL',
    f'{POSTGRES_USER}:{POSTGRES_PASSWORD}@{POSTGRES_HOST}:{POSTGRES_PORT}/{POSTGRES_DB}',
)

POSTGRES_TIMEZONE = 'America/Sao_Paulo'


# Redis settings

REDIS_URL = os.getenv('REDIS_URL', 'redis://emprestimo_redis:6379/0')
