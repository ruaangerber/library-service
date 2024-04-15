#!/bin/sh

couchbase-cli cluster-init \
    -c library-service-db \
    --cluster-username administrator \
    --cluster-password password \
    --services data,index,query \
    --cluster-ramsize 2048 \
    --cluster-index-ramsize 256

couchbase-cli bucket-create \
    --cluster library-service-db \
    --username administrator \
    --password password \
    --bucket library \
    --bucket-type couchbase \
    --bucket-ramsize 512 \
    --enable-flush 0

couchbase-cli user-manage \
    --cluster library-service-db \
    --username administrator \
    --password password \
    --set \
    --auth-domain local \
    --rbac-username default \
    --rbac-password password \
    --roles bucket_full_access[library]

sleep 5

cbq -u administrator -p password -e "http://library-service-db:8093" --script "CREATE PRIMARY INDEX ON \`default\`:\`library\`"