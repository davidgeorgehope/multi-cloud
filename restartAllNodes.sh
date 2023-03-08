kubectl -n aws scale deployment multi-cloud --replicas=0
kubectl -n aws scale deployment multi-cloud --replicas=1
kubectl -n gcp-1 scale deployment multi-cloud --replicas=0
kubectl -n gcp-1 scale deployment multi-cloud --replicas=1
kubectl -n gcp-2 scale deployment gcp-bigdata-consumer-multi-cloud --replicas=0
kubectl -n gcp-2 scale deployment gcp-bigdata-consumer-multi-cloud --replicas=1
