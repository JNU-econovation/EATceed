from pydantic import BaseModel

class EatHabits(BaseModel):
    flag: bool
    weight_prediction: str
    advice_carbo: str
    advice_protein: str
    advice_fat: str
    synthesis_advice: str