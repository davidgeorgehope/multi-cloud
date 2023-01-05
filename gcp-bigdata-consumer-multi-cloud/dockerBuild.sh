#!/bin/bash
docker build --platform linux/x86_64 -t gcp-bigdata-consumer-multi-cloud .

docker tag gcp-bigdata-consumer-multi-cloud djhope99/gcp-bigdata-consumer-multi-cloud:gcp-bigdata-consumer-multi-cloud

docker push djhope99/gcp-bigdata-consumer-multi-cloud:gcp-bigdata-consumer-multi-cloud
