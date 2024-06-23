import uuid
from pydantic import BaseModel
class Payment(BaseModel):
    value: float
    sender: int
    receiver: int
