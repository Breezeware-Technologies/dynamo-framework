#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    GRANT ALL PRIVILEGES ON DATABASE dynamo TO dynamo;
EOSQL

# populating dynamo
psql -U dynamo dynamo < /tmp/psql/dynamo/dynamo_organization.sql
psql -U dynamo dynamo < /tmp/psql/dynamo/dynamo_apps.sql
psql -U dynamo dynamo < /tmp/psql/dynamo/dynamo_auth.sql
psql -U dynamo dynamo < /tmp/psql/dynamo/dynamo_audit.sql
psql -U dynamo dynamo < /tmp/psql/dynamo/dynamo_config.sql
