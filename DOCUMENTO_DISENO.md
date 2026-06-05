# Documento de Diseño — MusicAPI

**Proyecto:** Actividad Evaluación Final UT6 — API REST con persistencia JPA  
**Dominio:** Plataforma musical (Artistas, Álbumes, Canciones, Géneros)  
**Base de datos:** H2 (en memoria)  
**Framework:** Spring Boot 4.x + Spring Data JPA

---

## 1. Diagrama Entidad-Relación (ER)

```
┌──────────────────┐
│     ARTISTAS     │
│──────────────────│
│ id       PK      │
│ nombre           │
│ pais             │
│ activo           │
└────────┬─────────┘
         │ 1
         │
         │ N  artista_id (FK)
┌────────▼─────────┐
│      ALBUMES     │
│──────────────────│
│ id       PK      │
│ titulo           │
│ anio             │
│ artista_id  FK──►│──► ARTISTAS.id
└────────┬─────────┘
         │ 1
         │
         │ N  album_id (FK)
┌────────▼─────────┐         ┌──────────────────┐
│    CANCIONES     │   N   N │     GENEROS      │
│──────────────────│◄───────►│──────────────────│
│ id       PK      │         │ id       PK      │
│ titulo           │         │ nombre           │
│ duracion         │         │ descripcion      │
│ album_id    FK──►│──► ALBUMES.id              │
└──────────────────┘         └──────────────────┘
         ▲                            ▲
         │                            │
         └────────────────────────────┘
                  CANCION_GENERO
              ┌─────────────────────┐
              │   cancion_genero    │
              │─────────────────────│
              │ cancion_id  FK ─► CANCIONES.id  │
              │ genero_id   FK ─► GENEROS.id    │
              └─────────────────────┘
```

### Tablas generadas por Hibernate
| Tabla | Descripción |
|-------|-------------|
| `artistas` | Almacena los artistas musicales |
| `albumes` | Álbumes con FK a artistas |
| `canciones` | Canciones con FK a álbumes |
| `generos` | Géneros musicales |
| `cancion_genero` | Tabla intermedia de la relación ManyToMany |

---

## 2. Relaciones entre entidades

| Tipo de relación | Entre | Anotación JPA | FK generada |
|---|---|---|---|
| OneToMany / ManyToOne | Artista → Album | `@OneToMany` / `@ManyToOne @JoinColumn(name="artista_id")` | `albumes.artista_id` |
| OneToMany / ManyToOne | Album → Cancion | `@OneToMany` / `@ManyToOne @JoinColumn(name="album_id")` | `canciones.album_id` |
| ManyToMany | Cancion ↔ Genero | `@ManyToMany @JoinTable(name="cancion_genero")` | Tabla `cancion_genero` |

---

## 3. Lista completa de Endpoints

Base URL: `http://localhost:8080/api/v1`

### 3.1 Artistas

| Método | Ruta | Respuesta | Auth | Módulo |
|--------|------|-----------|------|--------|
| GET | `/artistas` | 200 + lista JSON | Público | Núcleo |
| GET | `/artistas/{id}` | 200 JSON / 404 | Público | Núcleo |
| POST | `/artistas` | 201 Created | 🔒 X-API-KEY | Núcleo |
| PUT | `/artistas/{id}` | 200 JSON / 404 | 🔒 X-API-KEY | Núcleo |
| DELETE | `/artistas/{id}` | 204 No Content / 404 | 🔒 X-API-KEY | Núcleo |
| GET | `/artistas/buscar?nombre=x&sortBy=nombre&order=asc` | 200 + lista filtrada y ordenada | Público | Módulo B |

### 3.2 Álbumes

| Método | Ruta | Respuesta | Auth | Módulo |
|--------|------|-----------|------|--------|
| GET | `/albumes` | 200 + lista JSON | Público | Núcleo |
| GET | `/albumes/{id}` | 200 JSON / 404 | Público | Núcleo |
| POST | `/albumes` | 201 Created | 🔒 X-API-KEY | Núcleo |
| PUT | `/albumes/{id}` | 200 JSON / 404 | 🔒 X-API-KEY | Núcleo |
| DELETE | `/albumes/{id}` | 204 No Content / 404 | 🔒 X-API-KEY | Núcleo |
| GET | `/albumes/{id}/artista` | 200 + artista JSON | Público | Módulo A |
| GET | `/albumes/buscar?titulo=x&anio=2001&sortBy=anio&order=desc` | 200 + lista filtrada y ordenada | Público | Módulo B |

### 3.3 Canciones

| Método | Ruta | Respuesta | Auth | Módulo |
|--------|------|-----------|------|--------|
| GET | `/canciones` | 200 + lista JSON | Público | Núcleo |
| GET | `/canciones/{id}` | 200 JSON / 404 | Público | Núcleo |
| POST | `/canciones` | 201 Created | 🔒 X-API-KEY | Núcleo |
| PUT | `/canciones/{id}` | 200 JSON / 404 | 🔒 X-API-KEY | Núcleo |
| DELETE | `/canciones/{id}` | 204 No Content / 404 | 🔒 X-API-KEY | Núcleo |
| GET | `/canciones/{id}/album` | 200 + album JSON | Público | Módulo A |
| GET | `/canciones/buscar?titulo=x&albumId=1` | 200 + lista filtrada | Público | Módulo B |
| POST | `/canciones/{id}/generos/{generoId}` | 200 + cancion actualizada | 🔒 X-API-KEY | Módulo C |
| DELETE | `/canciones/{id}/generos/{generoId}` | 204 No Content | 🔒 X-API-KEY | Módulo C |
| GET | `/canciones/contar-por-genero/{generoId}` | 200 + `{"generoId": 1, "totalCanciones": 5}` | Público | Módulo C |

### 3.4 Géneros

| Método | Ruta | Respuesta | Auth | Módulo |
|--------|------|-----------|------|--------|
| GET | `/generos` | 200 + lista JSON | Público | Núcleo |
| GET | `/generos/{id}` | 200 JSON / 404 | Público | Núcleo |
| POST | `/generos` | 201 Created | 🔒 X-API-KEY | Núcleo |
| PUT | `/generos/{id}` | 200 JSON / 404 | 🔒 X-API-KEY | Núcleo |
| DELETE | `/generos/{id}` | 204 No Content / 404 | 🔒 X-API-KEY | Núcleo |
| GET | `/generos/{id}/canciones` | 200 + lista de canciones | Público | Módulo C |

---

## 4. Decisiones técnicas

### Sort dinámico en endpoints de búsqueda
Los endpoints `/artistas/buscar` y `/albumes/buscar` aceptan dos `@RequestParam` adicionales opcionales:
- `sortBy` (default: `"id"`) — campo por el que ordenar
- `order` (default: `"asc"`) — dirección: `asc` o `desc`

El servicio construye un objeto `Sort` de Spring Data y lo pasa al método derivado del repositorio. Esto permite tres llamadas distintas al mismo endpoint mostrando resultados diferentes, tal como requiere el Módulo B.

### Base de datos: H2 en memoria
H2 en modo `create-drop` fue elegida porque:
- No requiere instalación adicional
- La consola web integrada (`/h2-console`) permite visualizar tablas y datos en directo durante la defensa
- `spring.jpa.show-sql=true` muestra el SQL generado por Hibernate en la consola del servidor

### Seguridad: API Key Filter personalizado
Se implementó un `ApiKeyFilter` que extiende `OncePerRequestFilter` en lugar de Spring Security completo. Razón: es más transparente, fácil de explicar en la defensa, y suficiente para los requisitos del Módulo D.

- Endpoints GET → públicos (sin autenticación)
- Endpoints POST, PUT, DELETE → requieren header `X-API-KEY: musicapi-secret-2024`
- Sin API Key → 403 Forbidden con respuesta JSON

### @JsonIgnore — en qué lado y por qué
`@JsonIgnore` se aplica en el lado "uno" de las relaciones bidireccionales:
- `Artista.albumes` → para evitar ciclo infinito al serializar un Artista
- `Album.canciones` → mismo motivo
- `Genero.canciones` → mismo motivo

El lado "muchos" (que tiene la FK) sí se serializa, porque incluye el objeto padre como referencia simple.

### Optional — uso correcto
Todos los métodos `findById()` usan el patrón:
```java
return repository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Entidad con id " + id + " no encontrada"));
```
Nunca se llama `.get()` directamente sin comprobar antes.

### @Query JPQL — por qué no método derivado
El método `contarCancionesPorGenero` usa `@Query` con JPQL porque:
- Navega a través de la relación ManyToMany (`JOIN c.generos g`)
- Los métodos derivados de Spring Data no pueden expresar un `COUNT` que cruce una relación de colección indirecta
- JPQL opera sobre entidades Java (no sobre tablas SQL), por eso se escribe `FROM Cancion c` y no `FROM canciones`

### Validación con @Valid
Todos los endpoints POST y PUT tienen `@Valid` en el `@RequestBody`. Las anotaciones de validación en las entidades son:
- `@NotBlank` → campos String obligatorios
- `@Min(1900)` → año del álbum
- `@Positive` → duración de la canción

---

## 5. Estructura del proyecto

```
src/main/java/com/musicapi/
├── MusicApiApplication.java
├── model/
│   ├── Artista.java
│   ├── Album.java
│   ├── Cancion.java
│   └── Genero.java
├── repository/
│   ├── ArtistaRepository.java
│   ├── AlbumRepository.java
│   ├── CancionRepository.java
│   └── GeneroRepository.java
├── service/
│   ├── ArtistaService.java
│   ├── AlbumService.java
│   ├── CancionService.java
│   └── GeneroService.java
├── controller/
│   ├── ArtistaController.java
│   ├── AlbumController.java
│   ├── CancionController.java
│   └── GeneroController.java
├── exception/
│   ├── ResourceNotFoundException.java
│   └── GlobalExceptionHandler.java
└── security/
    └── ApiKeyFilter.java
```

---

## 6. Capturas de la base de datos

> Las capturas fueron tomadas desde la consola H2 en `http://localhost:8080/h2-console` con la aplicación en ejecución.

### Tabla `artistas`
![Tabla artistas](../imgAct/Captura%20de%20pantalla%202026-06-01%20095027.png)

### Tabla `albumes`
![Tabla albumes](../imgAct/Captura%20de%20pantalla%202026-06-01%20095033.png)

### Tabla `canciones`
![Tabla canciones](../imgAct/Captura%20de%20pantalla%202026-06-01%20095040.png)

### Tabla `cancion_genero` (ManyToMany)
![Tabla cancion_genero](../imgAct/Captura%20de%20pantalla%202026-06-01%20095057.png)
