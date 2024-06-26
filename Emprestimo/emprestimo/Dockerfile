FROM python:3.12.3-slim-bookworm

ENV DEBIAN_FRONTEND noninteractive
ENV PYTHONUNBUFFERED 1
ENV PYTHONDONTWRITEBYTECODE 1
WORKDIR "/usr/src/app"
STOPSIGNAL SIGINT

COPY ["requirements.txt", "/tmp/requirements.txt"]
RUN pip3 install --no-input --no-cache-dir --upgrade pip setuptools wheel && \
    pip3 install --no-input --no-cache-dir -r "/tmp/requirements.txt" && \
    rm "/tmp/requirements.txt"

ADD [".", "/usr/src/app"]
RUN mkdir -p "/var/log/integrations"

EXPOSE 3001

ENTRYPOINT ["sh", "/usr/src/app/docker/entrypoint.sh"]
