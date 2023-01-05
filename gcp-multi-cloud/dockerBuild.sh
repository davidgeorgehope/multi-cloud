#!/bin/bash
docker build --platform linux/x86_64 -t gcp-multi-cloud .

docker tag gcp-multi-cloud djhope99/gcp-multi-cloud:gcp-multi-cloud

docker push djhope99/gcp-multi-cloud:gcp-multi-cloud
