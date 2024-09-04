# Базовий образ з Java та Maven
FROM maven:3.8.6-openjdk-18 AS build

# Встановлюємо робочу директорію для проекту
WORKDIR /app

# Копіюємо файли проекту та будуємо jar з допомогою Maven
COPY pom.xml ./
COPY src ./src
RUN mvn package -DskipTests

# Базовий "чистий" образ для запуску jar
FROM openjdk:18-alpine

# Копіюємо збудований jar в контейнер
COPY --from=build /app/target/*.jar /app.jar

# Встановлюємо робочу директорію та порт
WORKDIR /app
EXPOSE 8080

# Запускаємо jar при старті контейнера
ENTRYPOINT ["java", "-jar", "/app.jar"]
