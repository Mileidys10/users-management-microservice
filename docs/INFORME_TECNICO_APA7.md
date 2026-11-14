# Implementación y Despliegue de un Microservicio de Gestión de Usuarios bajo Arquitectura Hexagonal y Domain-Driven Design

**Mileidys Agamez Ospino**  
Facultad de Ingeniería, Fundación Universitaria Antonio de Arévalo (UNITECNAR / UNICOLOMBO)  
Electiva Profesional II: Arquitectura de Microservicios  
Docente John Carlos Arrieta Arrieta  
14 de noviembre de 2026  

---

## Introducción

El desarrollo moderno de microservicios empresariales exige patrones arquitectónicos que desacoplen la lógica esencial del negocio frente a los detalles de infraestructura, frameworks y protocolos de comunicación. La arquitectura hexagonal (puertos y adaptadores), articulada con los principios del Diseño Guiado por el Dominio (Domain-Driven Design o DDD), ofrece un marco idóneo para aislar el núcleo de la aplicación, garantizar la mantenibilidad y favorecer la testabilidad exhaustiva.

El presente informe documenta la implementación, configuración y verificación técnica del microservicio de gestión de usuarios (`users-management-microservice`), desarrollado sobre Java 21 LTS y el framework Spring Boot 3.3.5. A través del desarrollo práctico de la guía, se abordó la bifurcación (fork) del código fuente, la compilación modular mediante Apache Maven, la configuración del esquema relacional en MySQL, la securización de endpoints mediante autenticación basada en tokens JWT (JSON Web Tokens) y la validación de operaciones CRUD a través de interfaces OpenAPI (Swagger UI) y clientes HTTP.

El repositorio oficial que respalda la totalidad del código y los artefactos se encuentra alojado públicamente en GitHub:  
URL: https://github.com/Mileidys10/users-management-microservice

---

## Metodología y Entorno Tecnológico

La ejecución del taller se estructuró a partir de una metodología experimental y progresiva, asegurando la consistencia entre los artefactos declarados en los archivos de configuración y la ejecución en el entorno local.

### Pila Tecnológica Implementada

1. **Lenguaje y Entorno de Ejecución:** Java Development Kit (JDK 21 LTS, Oracle Corporation).
2. **Framework de Base:** Spring Boot 3.3.5 articulado como *single composition root* para inyección de dependencias.
3. **Gestión de Construcción:** Apache Maven 3.9.15 mediante `mvnw.cmd` y binario del sistema.
4. **Motor de Persistencia:** MySQL 8 / TiDB Cloud Serverless con pool de conexiones administrado por HikariCP.
5. **Seguridad y Control de Acceso:** Spring Security con filtro interceptor `JwtAuthenticationFilter` y algoritmo HMAC SHA-256.
6. **Mapeo y Validación:** Jakarta Bean Validation (Hibernate Validator) y DTOs inmutables implementados con Java Records.
7. **Documentación Interactiva:** Springdoc OpenAPI 3.0 con interfaz gráfica Swagger UI.

---

## Desarrollo de Actividades y Evidencias de Operación

### 1. Gestión del Control de Versiones y Estructura del Proyecto

El ciclo inició con la creación de una bifurcación (fork) del repositorio base hacia la cuenta personal del estudiante en GitHub. Posteriormente, se clonó el proyecto al sistema de archivos local y se verificaron las herramientas del entorno de compilación.

**Figura 1**  
*Configuración de remotos Git y bifurcación del repositorio en GitHub*  
[Insertar captura de pantalla de la terminal con el comando git remote -v y la página web del repositorio en GitHub]  
*Nota.* La salida en consola evidencia que el alias `origin` apunta a la cuenta personal de GitHub (Mileidys10), mientras que `upstream` enlaza el repositorio original de referencia. Elaboración propia.

### 2. Compilación y Construcción del Artefacto (Build Success)

Se ejecutó la fase de compilación integral mediante la herramienta de automatización Maven con la instrucción `mvn clean install -U`. El proceso descargó las dependencias declaradas en el archivo `pom.xml`, compiló 101 archivos fuente y ejecutó la totalidad de la suite de pruebas unitarias de arquitectura hexagonal.

**Figura 2**  
*Salida de consola de Maven confirmando la compilación exitosa (BUILD SUCCESS)*  
[Insertar captura de pantalla de la terminal mostrando BUILD SUCCESS y el total de pruebas superadas]  
*Nota.* La ejecución finalizó sin errores sintácticos ni fallas de compilación, generando el paquete JAR ejecutable en el directorio target. Elaboración propia.

### 3. Configuración de Persistencia Relacional y Ejecución del Script DDL

Para garantizar la persistencia de las entidades de dominio, se configuró el origen de datos dentro del archivo `application.properties` apuntando a la base de datos `crud_usuarios`. Se ejecutó el script `schema.sql` responsable de aprovisionar la tabla `users` e insertar el usuario administrativo inicial.

**Figura 3**  
*Estructura de la tabla users y registro administrativo inicial en la base de datos*  
[Insertar captura de pantalla del cliente de base de datos o MySQL Workbench visualizando las columnas de la tabla users]  
*Nota.* Esquema relacional con llaves primarias UUID, correo electrónico único y estados tipificados mediante enumeraciones. Elaboración propia.

### 4. Despliegue del Microservicio en Servidor Embebido

El microservicio se inició localmente ejecutando la instrucción `mvn spring-boot:run`. El framework inicializó el contenedor embebido Apache Tomcat en el puerto de red 8080 y levantó el contexto de Spring en 2.8 segundos.

**Figura 4**  
*Registro de inicialización del microservicio en Tomcat sobre el puerto 8080*  
[Insertar captura de pantalla de los logs de arranque mostrando Tomcat initialized with port 8080]  
*Nota.* La inicialización confirma la activación exitosa del pool de conexiones HikariCP y la disponibilidad de los controladores REST. Elaboración propia.

### 5. Documentación Interactiva de la API con Swagger UI

Con el microservicio en ejecución, se accedió a la ruta `http://localhost:8080/swagger-ui/index.html` para inspeccionar visualmente los contratos expuestos por los controladores REST (`auth-rest-controller` y `user-rest-controller`).

**Figura 5**  
*Consola interactiva de Swagger UI exponiendo los endpoints del microservicio*  
[Insertar captura de pantalla del navegador web con la interfaz Swagger UI abierta]  
*Nota.* Catálogo de operaciones HTTP disponibles para autenticación, consulta, creación, actualización y eliminación de usuarios. Elaboración propia.

### 6. Autenticación y Emisión de Token de Acceso JWT

Para consumir los endpoints protegidos del microservicio, se envió una solicitud `POST /api/auth/login` con las credenciales del usuario administrador (`admin@example.com` / `Admin1234!`). El servicio validó el hash BCrypt y retornó un token Bearer JWT con tiempo de expiración de 900 segundos.

**Figura 6**  
*Petición de inicio de sesión exitosa con código de estado HTTP 200 y respuesta JWT*  
[Insertar captura de pantalla en Swagger o Postman mostrando la respuesta JSON con accessToken]  
*Nota.* Respuesta del servidor que contiene el token de acceso requerido para la cabecera Authorization en peticiones posteriores. Elaboración propia.

### 7. Verificación del Consumo de Recursos Protegidos (CRUD de Usuarios)

Utilizando el token de acceso emitido en la cabecera `Authorization: Bearer <token>`, se ejecutaron las operaciones sobre el recurso de usuarios:

1. **Creación de usuario (`POST /api/users`):** Se envió la estructura JSON con los datos del nuevo usuario, obteniendo código `201 Created` y estado inicial `PENDING`.
2. **Listado general (`GET /api/users`):** Retorno de la colección completa de usuarios con código `200 OK`.
3. **Actualización de estado (`PUT /api/users/{id}`):** Modificación del estado del usuario a `ACTIVE` y verificación de consistencia en base de datos.

**Figura 7**  
*Creación y actualización de usuario con respuestas HTTP 201 y HTTP 200*  
[Insertar captura de pantalla de la petición POST /api/users y PUT /api/users/{id}]  
*Nota.* Validación de reglas de negocio en la capa de aplicación y respuesta estructurada en formato JSON. Elaboración propia.

---

## Conclusiones

1. La adopción de la arquitectura hexagonal combinada con Domain-Driven Design asegura que las reglas de negocio del microservicio de usuarios permanezcan completamente desacopladas de librerías externas y de la infraestructura de persistencia.
2. La parametrización externa de propiedades en `application.properties` y variables de entorno (`.env`) garantiza la portabilidad del microservicio, facilitando su transición fluida entre bases de datos locales, motores remotos en la nube (Aiven y TiDB) y entornos de despliegue PaaS (Render).
3. El uso de autenticación sin estado mediante tokens JWT provee un mecanismo seguro y escalable para proteger los recursos de la API REST, cumpliendo con los estándares modernos de desarrollo de software distribuido.
