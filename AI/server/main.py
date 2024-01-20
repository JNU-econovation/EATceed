import openai
from fastapi import FastAPI, HTTPException, Depends
from typing import Annotated
from pydantic import BaseModel