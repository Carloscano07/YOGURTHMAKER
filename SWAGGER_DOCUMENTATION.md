# Documentación OpenAPI - Yogurt Maker API

## Estado: ✅ COMPLETADO

La aplicación Spring Boot ahora cuenta con documentación interactiva mediante Swagger UI y OpenAPI 3.0.

### Acceso a la Documentación

**Swagger UI (Interfaz Interactiva):**
- URL: `http://localhost:8080/swagger-ui/index.html`
- Permite probar todos los endpoints directamente desde el navegador

**OpenAPI JSON (Especificación técnica):**
- URL: `http://localhost:8080/v3/api-docs`
- Especificación completa en formato JSON OpenAPI 3.0.1

### Endpoints Documentados

#### Recetas (Recipes)
- `GET /api/recipes` - Listar recetas activas
- `POST /api/recipes` - Crear receta
- `GET /api/recipes/{id}` - Obtener receta por ID
- `PUT /api/recipes/{id}` - Actualizar receta
- `PATCH /api/recipes/{id}/activate` - Activar receta
- `PATCH /api/recipes/{id}/deactivate` - Desactivar receta
- `GET /api/recipes/search` - Buscar recetas

#### Lotes de Yogurt (Yogurt Batches)
- `GET /api/batches` - Obtener lotes (con filtro opcional por estado)
- `POST /api/batches` - Iniciar nuevo lote
- `GET /api/batches/{batchId}` - Obtener lote por ID
- `POST /api/batches/{batchId}/heating` - Iniciar calentamiento
- `POST /api/batches/{batchId}/inoculating` - Iniciar inoculación
- `POST /api/batches/{batchId}/incubation` - Iniciar incubación
- `POST /api/batches/{batchId}/refrigeration` - Iniciar refrigeración
- `POST /api/batches/{batchId}/temperature` - Registrar temperatura
- `POST /api/batches/{batchId}/complete` - Completar lote
- `POST /api/batches/{batchId}/fail` - Marcar lote como fallido

#### Monitoreo (Monitoring)
- `GET /api/monitoring/dashboard` - Panel de control de producción
- `GET /api/monitoring/batches/active` - Obtener lotes activos
- `GET /api/monitoring/batches/{batchId}/temperature` - Resumen de temperaturas
- `GET /api/monitoring/batches/{batchId}/temperature-logs` - Registros de temperatura

### Configuración

**Dependencia Principales:**
- Spring Boot 4.0.6
- Spring Web MVC 7.0.7
- Spring Data JPA
- Hibernate 7.2.12
- H2 Database
- springdoc-openapi-starter-webmvc-ui 2.7.0
- swagger-annotations 2.2.3
- Lombok

**Bases de Datos:**
- H2 en memoria (JDBC URL: `jdbc:h2:mem:yogurtdb`)
- Acceso a H2 Console: `http://localhost:8080/h2-console`

### Información de Contacto

- **Autor:** Cano7
- **Email:** contact@cano7.dev
- **GitHub:** https://github.com/cano7
- **Licencia:** Apache 2.0

### Resolución de Problemas

**Versión de springdoc:** Se actualizó a la versión 2.7.0 para garantizar compatibilidad total con Spring Boot 4.0.6 y resolver problemas de `NoSuchMethodError` en la inicialización de `ControllerAdviceBean`.

**Validación de Compilación:**
```bash
.\mvnw.cmd clean compile   # ✅ BUILD SUCCESS
.\mvnw.cmd test            # ✅ Todos los tests pasan
.\mvnw.cmd spring-boot:run # ✅ Aplicación ejecutándose en puerto 8080
```

### Archivo de Especificación OpenAPI

Se ha generado el archivo `openapi.json` con la especificación completa de la API. Este archivo contiene:
- Metadatos de la API (título, descripción, versión)
- Definición de todos los endpoints
- Esquemas de DTOs y entidades
- Respuestas HTTP esperadas
- Parámetros de entrada requeridos

---
**Última actualización:** 2026-05-04 21:35 UTC
