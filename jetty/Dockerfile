FROM clojure
WORKDIR /usr/src/app
COPY ./jetty .

ENV OTEL_TRACES_EXPORTER=otlp \
    OTEL_METRICS_EXPORTER=otlp \
    OTEL_LOGS_EXPORTER=otlp \
    OTEL_EXPORTER_OTLP_ENDPOINT='http://collector:4317/' \
    OTEL_SERVICE_NAME=clojure-app-jetty

EXPOSE 8080
CMD ["lein", "run"]