# --- Etapa 1: Construcción (Build) ---
# Usamos una imagen oficial de Maven con la versión 3.9.11 y Java 17 (Temurin)
# La nombramos "build" para poder referenciarla después.
FROM maven:3.9.11-eclipse-temurin-17 AS build

# Establecemos el directorio de trabajo dentro del contenedor.
WORKDIR /app

# Copiamos solo el pom.xml para aprovechar la caché de Docker.
# Las dependencias solo se descargarán de nuevo si el pom.xml cambia.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el resto del código fuente del proyecto.
COPY src ./src

# Compilamos la aplicación y empaquetamos en un .jar, omitiendo los tests.
RUN mvn package -DskipTests

# --- Etapa 2: Ejecución (Runtime) ---
# Partimos de una imagen JRE (Java Runtime Environment) ligera de Temurin 17.
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copiamos únicamente el archivo .jar generado en la etapa de construcción.
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto 7070, que es el que se configura en application.yml.
EXPOSE 7070

# Definimos el comando que se ejecutará al iniciar el contenedor.
ENTRYPOINT ["java", "-jar", "app.jar"]