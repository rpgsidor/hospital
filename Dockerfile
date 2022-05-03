FROM openjdk
COPY target/hospital.jar /var/
ENTRYPOINT ["java", "-jar", "/var/hospital.jar"]
