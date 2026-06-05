# MusicAPI — API REST con persistencia JPA

API REST desarrollada con Spring Boot para gestionar una plataforma musical con Artistas, Álbumes, Canciones y Géneros.

## Requisitos previos

- Java 21 o superior instalado
- No requiere Maven instalado (el proyecto incluye Maven Wrapper)

## Cómo arrancar la aplicación

**En Windows:**
```bash
mvnw.cmd spring-boot:run
```

**En Mac/Linux:**
```bash
./mvnw spring-boot:run
```

La API estará disponible en: `http://localhost:8080`

Al arrancar se crean las tablas automáticamente y se cargan datos de prueba.

## Consola H2 (ver la base de datos en directo)

URL: `http://localhost:8080/h2-console`

| Campo | Valor |
|-------|-------|
| JDBC URL | `jdbc:h2:mem:musicdb` |
| User Name | `sa` |
| Password | *(vacía)* |

## Seguridad

Los endpoints de **lectura (GET)** son públicos.

Los endpoints de **escritura (POST, PUT, DELETE)** requieren el header:
```
X-API-KEY: musicapi-secret-2024
```

## Endpoints disponibles

### Artistas — `/api/v1/artistas`
| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| GET | `/api/v1/artistas` | Listar todos los artistas | Público |
| GET | `/api/v1/artistas/{id}` | Obtener artista por ID | Público |
| POST | `/api/v1/artistas` | Crear artista | 🔒 |
| PUT | `/api/v1/artistas/{id}` | Actualizar artista | 🔒 |
| DELETE | `/api/v1/artistas/{id}` | Eliminar artista | 🔒 |
| GET | `/api/v1/artistas/buscar?nombre=beatles&sortBy=nombre&order=asc` | Buscar por nombre con sort dinámico | Público |

### Álbumes — `/api/v1/albumes`
| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| GET | `/api/v1/albumes` | Listar todos los álbumes | Público |
| GET | `/api/v1/albumes/{id}` | Obtener álbum por ID | Público |
| POST | `/api/v1/albumes` | Crear álbum | 🔒 |
| PUT | `/api/v1/albumes/{id}` | Actualizar álbum | 🔒 |
| DELETE | `/api/v1/albumes/{id}` | Eliminar álbum | 🔒 |
| GET | `/api/v1/albumes/{id}/artista` | Artista del álbum | Público |
| GET | `/api/v1/albumes/buscar?titulo=road&anio=1969&sortBy=anio&order=desc` | Buscar por título y año con sort dinámico | Público |

## Tecnologías utilizadas

- Java 21
- Spring Boot 4.x
- Spring Data JPA / Hibernate
- H2 Database (en memoria)
- Lombok
- Spring Validation
- Maven
