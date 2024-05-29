import os
import pika
from contextlib import asynccontextmanager
from fastapi import FastAPI

RABBITMQ_HOST = os.getenv("RABBITMQ_HOST", "localhost")
RABBITMQ_PORT = os.getenv("RABBITMQ_PORT", "5672")


async def initialize_connection_with_rabbitmq(app_instance: FastAPI):
    """
    Asynchronously initializes a connection with RabbitMQ.

    Args:
        app (FastAPI): The FastAPI application instance.

    Returns:
        None
    """
    app_instance.state.rabbitmq_connection = await pika.connect_robust(
        f'amqp://guest:guest@{RABBITMQ_HOST}:{RABBITMQ_PORT}/'
    )


@asynccontextmanager
async def lifespan(app_instance: FastAPI):
    """
    An asynchronous context manager that initializes a connection with RabbitMQ and yields the app instance.

    Args:
        app_instance (FastAPI): The FastAPI application instance.

    Yields:
        None
    """
    await initialize_connection_with_rabbitmq(app_instance=app_instance)
    yield


app = FastAPI(lifespan=lifespan)
