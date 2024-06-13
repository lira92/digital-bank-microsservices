import uuid
from pydantic import BaseModel

class Payment(BaseModel):
    value: int
    sender: int
    receiver: int
