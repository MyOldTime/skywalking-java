#!/bin/bash

cd "$(dirname "$0")"

rm -f config/agent.config
cp config/agent.config.template config/agent.config

APP_NAME_REPLACE=$1
BACKEND_SERVICES_REPLACE=$2
BOOTSTRAP_SERVERS_REPLACE=$3

sed -i 's/APP_NAME_REPLACE/'$APP_NAME_REPLACE'/g' config/agent.config
sed -i 's/BACKEND_SERVICES_REPLACE/'$BACKEND_SERVICES_REPLACE'/g' config/agent.config
sed -i 's/BOOTSTRAP_SERVERS_REPLACE/'$BOOTSTRAP_SERVERS_REPLACE'/g' config/agent.config
