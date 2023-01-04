FROM openjdk:19-alpine 

# Add the application's jar to the container
ADD target/gcp-multi-cloud-0.0.1-SNAPSHOT.jar gcp-multi-cloud-0.0.1-SNAPSHOT.jar 
COPY application.properties /config/

# Run the jar file 
ENTRYPOINT ["java","-jar","/gcp-multi-cloud-0.0.1-SNAPSHOT.jar"]
