#!/bin/bash
set -eux

declare -r HOST="library-service-db:8091/ui/index.html"

wait-for-url() {
    echo "Testing $1"
    timeout -s TERM 30 bash -c \
    'while [[ "$(curl -s -o /dev/null -L -w ''%{http_code}'' ${0})" != "200" ]];\
    do echo "Waiting for ${0}" && sleep 2;\
    done' ${1}
    echo "OK!"
    curl -I $1
}
wait-for-url http://${HOST}