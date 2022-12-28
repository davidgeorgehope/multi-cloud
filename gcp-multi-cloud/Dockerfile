FROM openjdk:19-alpine 

# Add the application's jar to the container
ADD target/multi-cloud-0.0.1-SNAPSHOT.jar multi-cloud-0.0.1-SNAPSHOT.jar 
COPY application.properties /config/

# Run the jar file 
ENTRYPOINT ["java","-jar","/multi-cloud-0.0.1-SNAPSHOT.jar"]
