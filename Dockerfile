FROM openjdk:17-jdk-alpine

WORKDIR /app

# Kopiujemy cały folder demo do kontenera
COPY demo /app/demo

# Budujemy projekt Mavenem wewnątrz kontenera
RUN cd demo && ./mvnw clean package -DskipTests

# Uruchamiamy aplikację z wygenerowanego pliku jar
ENTRYPOINT ["java", "-jar", "/app/demo/target/demo-1.0.0.jar"]

# cd demo && ./mvnw clean package
# java -jar demo/target/demo-1.0.0.jar