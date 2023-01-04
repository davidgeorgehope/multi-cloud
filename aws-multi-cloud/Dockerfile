FROM openjdk:19-alpine 

# Add the application's jar to the container
ADD target/aws-multi-cloud-0.0.1-SNAPSHOT.jar aws-multi-cloud-0.0.1-SNAPSHOT.jar 
COPY application.properties /config/

# Run the jar file 
ENTRYPOINT ["java","-jar","/aws-multi-cloud-0.0.1-SNAPSHOT.jar"]
