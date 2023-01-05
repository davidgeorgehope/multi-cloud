#!/bin/bash
docker build --platform linux/x86_64 -t multi-cloud .

docker tag multi-cloud djhope99/cloud-repo:multi-cloud

docker push djhope99/cloud-repo:multi-cloud
