
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
    sender: int    

class ScheduledBoleto(Boleto):
    data_agendamento : date
    descricao : str  

class ScheduledBoletoStatus(ScheduledBoleto):
    realizado : bool = False
    value: float