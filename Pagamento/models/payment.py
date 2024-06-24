
import uuid
from pydantic import BaseModel
from datetime import date

class Payment(BaseModel):
    value: float
    sender: int
    receiver: int

class ScheduledPayment(Payment):
    data_agendamento : date
    descricao : str
 
    
class ScheduledPaymentStatus(ScheduledPayment):
    realizado : bool = False

class Boleto(BaseModel):
    boleto: str
    numero_conta: int

class ScheduledBoleto(Boleto):
    valor : float
    realizado : bool = False
    data_agendamento : date
    descricao : str