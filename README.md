# Prices API - Arquitectura Hexagonal

API REST para consulta de precios aplicables en un sistema de comercio electrónico.

## Tecnologías

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database (in-memory)
- Maven
- JUnit 5
- MockMvc & REST Assured

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

### Compilar

```bash
./mvnw clean install
```

### Ejecutar

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local 
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

1. Cambiar H2 por base de datos persistente (PostgreSQL, MySQL)
2. Externalizar configuración mediante variables de entorno
3. Añadir seguridad con Spring Security (JWT/OAuth2)
4. Implementar logs estructurados con formato JSON
5. Añadir métricas con Actuator y Micrometer
6. Dockerizar la aplicación
7. Implementar CI/CD con GitHub Actions

## Estructura del Proyecto

```
prices-api/
├── pom.xml
├── README.md
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
