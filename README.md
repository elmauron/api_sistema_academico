# API del Sistema Académico

Este proyecto es el trabajo final de la materia Laboratorio III de la Universidad Tecnologica Nacional, Facultad Regional Bahía Blanca. Consiste en la implementación de un conjunto de API REST utilizando Java y el framework Spring Boot, siguiendo una arquitectura en capas para gestionar un sistema académico.

## Funcionalidades

La API permite las siguientes operaciones:

- **Materias:**
  - Crear, modificar y eliminar materias.
  - Obtener materias con filtros por nombre y ordenamiento por nombre o código.

- **Carreras:**
  - Crear, modificar y eliminar carreras.

- **Alumnos:**
  - Crear, modificar y eliminar alumnos.
  - Gestionar la inscripción, aprobación o pérdida de regularidad en asignaturas para un alumno.

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.0.0**
- **Maven**
- **H2 Database** (base de datos en memoria para pruebas)
- **JUnit 5** (para pruebas unitarias)

## Arquitectura

El proyecto sigue una arquitectura en capas que incluye:

1. **Capa de Presentación (Controllers):** Maneja las solicitudes HTTP y define los endpoints de la API.
2. **Capa de Negocio (Services):** Contiene la lógica de negocio y las reglas del sistema.
3. **Capa de Persistencia (Repositories):** Gestiona el acceso a la base de datos y las operaciones CRUD.
4. **Capa de Modelo (Entities):** Define las entidades del dominio como Alumno, Materia, Carrera, Profesor y Asignatura.
5. **Data Transfer Objects (DTOs):** Facilitan la transferencia de datos entre las capas y hacia el cliente.
