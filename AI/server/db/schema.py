from pydantic import BaseModel

class EatHabitsCreate(BaseModel):
    flag: bool
    weight_prediction: str
    weight_advice: str