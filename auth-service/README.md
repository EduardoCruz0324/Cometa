# Auth Service API

Microservicio de autenticación desarrollado con **Spring Boot** y **Spring Security**.  
Permite registrar usuarios, autenticarse y obtener un **JSON Web Token (JWT)** para acceder a endpoints protegidos.

---

## Tecnologías

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- JWT (JSON Web Token)
- Maven

---

## Flujo de autenticación

1. El usuario se registra en el sistema.
2. El usuario inicia sesión con su `username` y `password`.
3. El servidor valida las credenciales.
4. Si son correctas, se genera un **JWT**.
5. El cliente envía el token en las siguientes peticiones.
