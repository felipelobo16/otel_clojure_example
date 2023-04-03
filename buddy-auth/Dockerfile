FROM clojure
WORKDIR /usr/src/app
COPY ./buddy-auth .

ENV OTEL_TRACES_EXPORTER=otlp \
    OTEL_METRICS_EXPORTER=otlp \
    OTEL_LOGS_EXPORTER=otlp \
    OTEL_EXPORTER_OTLP_ENDPOINT='http://collector:4317/' \
    OTEL_SERVICE_NAME=clojure-app-buddyAuth

EXPOSE 8080
CMD ["lein", "run"]