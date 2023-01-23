FROM openjdk:19-alpine 

# Add the application's jar to the container
ADD target/gcp-bigdata-consumer-multi-cloud-0.0.1-SNAPSHOT.jar gcp-bigdata-consumer-multi-cloud-0.0.1-SNAPSHOT.jar
COPY application.properties /config/
COPY elastic-product-marketing-e145e13fbc7c.json /bigdata-creds/

# Run the jar file 
ENTRYPOINT ["java","-jar","/gcp-bigdata-consumer-multi-cloud-0.0.1-SNAPSHOT.jar"]
