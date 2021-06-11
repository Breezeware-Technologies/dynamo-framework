#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE dynamo_webapp;
    GRANT ALL PRIVILEGES ON DATABASE dynamo_webapp TO dynamo_webapp_usr;
EOSQL

# populating dynamo
psql -U dynamo_webapp_usr dynamo_webapp < /tmp/psql_data/dynamo/dynamo_organization.sql
psql -U dynamo_webapp_usr dynamo_webapp < /tmp/psql_data/dynamo/dynamo_apps.sql
psql -U dynamo_webapp_usr dynamo_webapp < /tmp/psql_data/dynamo/dynamo_auth.sql
psql -U dynamo_webapp_usr dynamo_webapp < /tmp/psql_data/dynamo/dynamo_audit.sql
psql -U dynamo_webapp_usr dynamo_webapp < /tmp/psql_data/dynamo/dynamo_config.sql
psql -U dynamo_webapp_usr dynamo_webapp < /tmp/psql_data/dynamo/dynamo_inventory.sql
