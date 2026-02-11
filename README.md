```markdown
# 📋 Sistema de Gestión de Tareas (Task Management App)

Solución de prueba técnica Full Stack que implementa un sistema para crear, listar, editar y eliminar tareas. El proyecto consta de una API REST en **Java (Spring Boot)** y un cliente frontend en **Vue.js 3**.

---

## 🛠️ Tecnologías Utilizadas

- **Backend:** Java 17, Spring Boot 3, Maven.
- **Base de Datos:** MySQL.
- **Documentación API:** Swagger (OpenAPI 3).
- **Frontend:** Vue.js 3 (Composition API), Vite, Axios.
- **Estilos:** CSS3 nativo (Diseño responsivo).

---

## ⚙️ Requisitos Previos

Asegúrate de tener instalado:
- **Java JDK 21** o superior.
- **Node.js 20** o superior.
- **MySQL Server** corriendo en el puerto `3306`.
- **Maven** (opcional si usas el wrapper `mvnw` incluido).

---

## 🚀 Instrucciones de Despliegue Local

### 1. Configuración de Base de Datos (MySQL)
Crea una base de datos vacía en tu servidor MySQL. La aplicación creará la tabla automáticamente al iniciar (Hibernate `ddl-auto: update`).

```sql
CREATE DATABASE task_manager_db;
```

> **Nota:** Verifica que las credenciales en `src/main/resources/application.properties` del backend coincidan con tu instalación local de MySQL (usuario/password).

### 2. Ejecutar el Backend (API)
Navega a la carpeta del backend y ejecútalo:

```bash
Abrir con el IDE la carpeta  pruebaTask/demo, complicar y ejecutar el jar generado
```
*El servidor iniciará en: `http://localhost:8081` (o el puerto configurado).*

### 3. Ejecutar el Frontend (Cliente)
En una nueva terminal, navega a la carpeta del frontend:

```bash
cd mi-app-tareas
npm install
npm run dev
```
*La aplicación web estará disponible en: `http://localhost:5173` (aproximadamente).*

---

## 📚 Documentación de la API (Swagger)

Una vez iniciado el backend, puedes consultar y probar los endpoints directamente desde Swagger UI:

👉 **URL Swagger:** [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

### Endpoints Principales

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/tasks` | Crear una nueva tarea. |
| `GET` | `/tasks` | Listar todas las tareas. |
| `GET` | `/tasks/{id}` | Obtener detalle de una tarea. |
| `PUT` | `/tasks/{id}` | Actualizar una tarea existente. |
| `DELETE` | `/tasks/{id}` | Eliminar una tarea. |

---

## 🗄️ Modelo de Datos

La tabla principal es `tasks` con la siguiente estructura:

| Columna | Tipo | Restricción | Descripción |
| :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | `PK, AI` | Identificador único. |
| `title` | `VARCHAR` | `NOT NULL` | Título de la tarea. |
| `description` | `VARCHAR` | `NOT NULL` | Descripción detallada. |
| `completed` | `BOOLEAN` | `NOT NULL` | Estado de la tarea (true/false). |

---

## ✅ Características Implementadas

- [x] CRUD completo de tareas.
- [x] Validación de datos.
- [x] Interfaz de usuario moderna y responsiva.
- [x] Conexión a API RESTful.
- [x] Documentación automática con Swagger.
```
