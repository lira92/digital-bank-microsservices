#!/usr/bin/env python3
"""Main FastAPI app for 'Emprestimo' microservice."""

from fastapi import FastAPI


app = FastAPI()


@app.get("/")
async def root():
    return {"message": "Hello World"}
