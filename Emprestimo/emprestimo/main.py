#!/usr/bin/env python3
"""Main FastAPI app for Emprestimo microservice."""

from fastapi import FastAPI

import loans.router


app = FastAPI()
app.include_router(loans.router.router, tags=['Emprestimos'])


@app.get('/health_check')
async def health_check():
    return {'status': 'OK'}
