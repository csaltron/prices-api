# Prices API

## Descripción General

API REST empresarial diseñada para gestionar la consulta de precios en un sistema de comercio electrónico. El sistema permite determinar el precio final aplicable a un producto específico considerando múltiples tarifas vigentes en diferentes rangos de fechas.

### Propósito

En un entorno de comercio electrónico, los precios de los productos pueden variar según diferentes factores: promociones especiales, horarios específicos, campañas de marketing o estrategias comerciales. Esta API resuelve el problema de determinar qué precio debe aplicarse a un producto en un momento dado, considerando que pueden existir múltiples tarifas superpuestas en el tiempo.

### Caso de Uso Principal

Cuando un cliente consulta el precio de un producto en la tienda online, el sistema debe:

1. Recibir la fecha/hora de la consulta
2. Identificar el producto consultado
3. Determinar la marca/cadena del grupo empresarial
4. Buscar todas las tarifas aplicables en ese momento
5. Aplicar la tarifa de mayor prioridad si existen múltiples opciones
6. Devolver el precio final con sus fechas de validez

### Ejemplo Práctico

Un producto con ID 35455 de la marca XYZS (ID: 1) puede tener:

- Una tarifa base (35.50 EUR) válida todo el año
- Una promoción especial (25.45 EUR) válida de 15:00 a 18:30 del 14 de junio
- Una oferta matutina (30.50 EUR) válida de 00:00 a 11:00 del 15 de junio
- Una tarifa premium (38.95 EUR) válida desde las 16:00 del 15 de junio hasta fin de año

Si un cliente consulta el precio a las 16:00 del 14 de junio, el sistema aplicará automáticamente la promoción especial de 25.45 EUR por tener mayor prioridad.

### Características Principales

- Consulta de precios por fecha, producto y marca
- Resolución automática de conflictos mediante sistema de prioridades
- Validación de parámetros de entrada
- Manejo robusto de errores con mensajes descriptivos
- Documentación interactiva con OpenAPI/Swagger (Solo para entorno de desarrollo)
- Arquitectura escalable y mantenible

## Tecnologías

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database (in-memory)
- Maven
- JUnit 5
- MockMvc
- Docker

## Arquitectura

El proyecto sigue los principios de Arquitectura Hexagonal (Ports & Adapters):

```
prices-api/
├── domain/              # Lógica de negocio pura
│   ├── model/          # Entidades de dominio
│   ├── port/           # Interfaces (puertos)
│   └── exception/      # Excepciones de dominio
├── application/         # Casos de uso
│   ├── service/        # Servicios de aplicación
│   ├── dto/            # DTOs de entrada/salida
│   └── mapper/         # Mapeo entre capas
└── infrastructure/      # Adaptadores
    ├── adapter/
    │   ├── rest/       # Controladores REST
    │   └── persistence/ # Repositorios JPA
    └── config/         # Configuración Spring
```

## Principios Aplicados

- Clean Architecture
- SOLID (inyección por constructor, bajo acoplamiento)
- API First
- Inmutabilidad en objetos de dominio
- Separación de responsabilidades por capas

## Estructura del Proyecto

```
prices-api/
├── pom.xml
├── README.md
├── Dockerfile
├── .dockerignore
├── docker-compose.yml
├── .env
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/company/prices/
    │   │       ├── domain/
    │   │       ├── application/
    │   │       └── infrastructure/
    │   └── resources/
    │       ├── application.yml
    │       ├── application-local.yml
    │       ├── application-test.yml
    │       ├── schema.sql
    │       └── data.sql
    └── test/
        └── java/
            └── com/company/prices/
                ├── unit/
                ├── integration/
                └── system/
```

## Endpoints

### GET /api/v1/prices

Consulta el precio aplicable según fecha, producto y marca.

#### Request Parameters

| Parámetro | Tipo | Descripción | Ejemplo |
|-----------|------|-------------|---------|
| applicationDate | LocalDateTime | Fecha de aplicación (ISO 8601) | 2020-06-14T10:00:00 |
| productId | Long | ID del producto | 35455 |
| brandId | Long | ID de la marca | 1 |

#### Response 200 OK

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50
}
```

#### Response 404 Not Found

```json
{
  "status": 404,
  "message": "No price found for product 35455, brand 1 at 2020-01-01T10:00",
  "timestamp": "2024-12-18T10:30:00"
}
```

## Ejecución

### Requisitos

- JDK 17
- Maven 3.6+
- Docker (opcional)

### Compilar

```bash
./mvnw clean install
```

### Ejecutar Localmente

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

### Ejecutar con Docker

#### Usando Docker Compose (recomendado)

Construir y ejecutar:
```bash
docker-compose up -d
```

Ver logs:
```bash
docker-compose logs -f
```

Detener:
```bash
docker-compose down
```

#### Usando Docker directamente

Construir imagen:
```bash
docker build -t prices-api:1.0.0 .
```

Ejecutar contenedor con perfil local:
```bash
docker run -d --name prices-api -p 8080:8080 -e SPRING_PROFILES_ACTIVE=local prices-api:1.0.0
```

Ver logs:
```bash
docker logs -f prices-api
```

Detener contenedor:
```bash
docker stop prices-api
```

Eliminar contenedor:
```bash
docker rm prices-api
```

Detener y eliminar en un solo paso:
```bash
docker stop prices-api && docker rm prices-api
```

La aplicación estará disponible en `http://localhost:8080`

### Documentación OpenAPI

Acceder a la documentación interactiva Swagger UI:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`
- OpenAPI YAML: `http://localhost:8080/api-docs.yaml`

### Consola H2

Acceder a `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:pricesdb`
- User: `sa`
- Password: (vacío)

## Testing

### Ejecutar todos los tests

```bash
./mvnw test
```

### Tipos de tests

1. **Unit Tests** (`PriceServiceTest`): Pruebas unitarias de la lógica de negocio
2. **Integration Tests** (`PriceControllerIntegrationTest`): Pruebas de integración de la API


### Tests Requeridos

- Test 1: 2020-06-14 10:00 → Precio: 35.50 (Lista 1)
- Test 2: 2020-06-14 16:00 → Precio: 25.45 (Lista 2)
- Test 3: 2020-06-14 21:00 → Precio: 35.50 (Lista 1)
- Test 4: 2020-06-15 10:00 → Precio: 30.50 (Lista 3)
- Test 5: 2020-06-16 21:00 → Precio: 38.95 (Lista 4)

## Ejemplos de Uso

### cURL

```bash
curl -X GET "http://localhost:8080/api/v1/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

### HTTPie

```bash
http GET "http://localhost:8080/api/v1/prices" \
  applicationDate=="2020-06-14T10:00:00" \
  productId==35455 \
  brandId==1
```

## Decisiones Técnicas

### Arquitectura Hexagonal

Separación clara entre dominio, aplicación e infraestructura para facilitar testing y mantenibilidad.

### Query JPQL con Prioridad

La consulta ordena por prioridad descendente y limita a 1 resultado, garantizando que siempre se obtiene el precio con mayor prioridad.

### LocalDateTime

Uso de API moderna de fechas de Java 8+ evitando tipos legacy.

### Builder Pattern

Construcción de objetos inmutables en dominio, mejorando la legibilidad y evitando estados inconsistentes.

### Exception Handling

GlobalExceptionHandler centraliza el manejo de errores, proporcionando respuestas consistentes.

### H2 con Scripts SQL

Inicialización de esquema y datos mediante scripts SQL estándar, facilitando migración a otras bases de datos.

## Consideraciones de Despliegue

### Containerización

La aplicación incluye configuración Docker lista para producción:

- Multi-stage build para optimizar tamaño de imagen
- Imagen base Amazon Corretto Alpine (ligera y segura)
- Health checks configurados
- Variables de entorno para configuración

### Recomendaciones para Producción

1. Cambiar H2 por base de datos persistente (PostgreSQL, MySQL)
2. Externalizar configuración mediante variables de entorno
3. Añadir seguridad con Spring Security (JWT/OAuth2)
4. Implementar logs estructurados con formato JSON
5. Añadir métricas con Actuator y Micrometer
6. Configurar límites de recursos en Kubernetes/Docker
7. Implementar CI/CD con GitHub Actions
8. Configurar backups automáticos de base de datos
9. Implementar rate limiting para protección contra abusos
10. Configurar SSL/TLS para conexiones seguras

### Variables de Entorno Docker

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| JAVA_OPTS | Opciones JVM | -Xmx512m -Xms256m |
| SPRING_PROFILES_ACTIVE | Perfil Spring activo | local |



