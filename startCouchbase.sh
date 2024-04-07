docker rm db && docker run -it -d --name db -p 8091-8096:8091-8096 -p 11210-11211:11210-11211 -v ~/couchbase:/opt/couchbase/var couchbase:community-7.2.4
