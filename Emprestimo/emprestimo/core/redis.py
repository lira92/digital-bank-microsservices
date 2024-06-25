"""Redis utils for Emprestimo microservice."""

import redis as _redis

from core.settings import REDIS_URL


redis = _redis.from_url(REDIS_URL)
