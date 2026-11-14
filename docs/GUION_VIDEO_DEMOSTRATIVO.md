# Guion Técnico para Sustentación en Video: Microservicio Spring Boot Hexagonal

- **Estudiante:** Mileidys Agamez Ospino
- **Asignatura:** Electiva Profesional II: Arquitectura de Microservicios
- **Docente:** John Carlos Arrieta Arrieta
- **Duración Estimada:** 4 a 5 minutos
- **Herramientas de Apoyo Recomendadas:** OBS Studio, Recordly o grabador de pantalla de Windows (Win + Alt + R).

---

## Estructura del Video por Bloques de Tiempo

### Bloque 1: Presentación y Repositorio en GitHub (0:00 - 0:45)

**En Pantalla:**  
Navegador web abierto en la página principal de tu repositorio forkeado:  
`https://github.com/Mileidys10/users-management-microservice`

**Locución (Voz del Estudiante):**  
> "Cordial saludo, profesor John Carlos Arrieta y compañeros. Mi nombre es Mileidys Agamez Ospino, estudiante de Ingeniería de Sistemas en la asignatura Electiva Profesional II.
> 
> En este video presento la sustentación técnica de la implementación, configuración y pruebas del microservicio de gestión de usuarios desarrollado con Spring Boot 3 y Arquitectura Hexagonal.
> 
> Como podemos observar en pantalla, el proyecto se encuentra alojado en mi cuenta personal de GitHub bajo el repositorio `Mileidys10/users-management-microservice`, con su rama `main` completamente sincronizada y con el historial de commits ordenado siguiendo las buenas prácticas de desarrollo."

---

### Bloque 2: Entorno de Desarrollo y Arquitectura Hexagonal en el IDE (0:45 - 1:40)

**En Pantalla:**  
El editor de código (VS Code o IntelliJ IDEA) con el árbol del proyecto desplegado en la vista lateral izquierda. Abrir brevemente la carpeta `src/main/java/com/jcaa/usersmanagement`.

**Locución (Voz del Estudiante):**  
> "Pasando al entorno de desarrollo local, observamos la estructura de directorios diseñada bajo los principios del Diseño Guiado por el Dominio (DDD) y la Arquitectura Hexagonal:
> 
> 1. En la capa de **Dominio** encontramos las entidades centrales como `UserModel`, los objetos de valor inmutables como `UserEmail` y las excepciones de negocio, libres de cualquier dependencia de frameworks externos.
> 2. En la capa de **Aplicación** se ubican los casos de uso, las interfaces de puertos de entrada y salida, y la orquestación del flujo.
> 3. En la capa de **Infraestructura** residen los adaptadores: los controladores REST en el punto de entrada, el servicio de seguridad JWT, y el adaptador de persistencia relacional configurado con JDBC y pools HikariCP.
> 
> Asimismo, el archivo `schema.sql` en `resources` contiene las sentencias DDL para la creación de la base de datos `crud_usuarios`, la tabla `users` y la inserción del usuario administrador inicial."

---

### Bloque 3: Compilación, Pruebas Unitarias y Cobertura de Código (1:40 - 2:30)

**En Pantalla:**  
Terminal integrada en el IDE. Ejecutar en vivo el comando:  
`mvn test` o `mvn verify`

**Locución (Voz del Estudiante):**  
> "A continuación, ejecutamos la suite de pruebas unitarias mediante Apache Maven para comprobar la robustez y la integridad de cada componente antes del despliegue.
> 
> Como se aprecia en la consola, la construcción finaliza con éxito con el mensaje `BUILD SUCCESS`. Se ejecutaron un total de 210 pruebas unitarias con cero fallos y cero errores, validando las capas de dominio, aplicación, seguridad y controladores REST.
> 
> Además, el análisis de cobertura automatizado con JaCoCo confirma un 96.90% de cobertura de instrucciones, superando ampliamente el umbral del 90% exigido en los requerimientos del taller."

---

### Bloque 4: Arranque del Microservicio en Servidor Embebido (2:30 - 3:00)

**En Pantalla:**  
En la terminal integrada, ejecutar el comando:  
`mvn spring-boot:run`  
Esperar a que aparezca el banner y los logs finales de inicio.

**Locución (Voz del Estudiante):**  
> "Procedemos ahora a levantar el microservicio de forma local ejecutando `mvn spring-boot:run`.
> 
> En los logs podemos verificar que Spring Boot 3.3.5 inicia el contexto en 2.8 segundos, establece la conexión con el motor de base de datos a través de HikariPool e inicializa el servidor web embebido Apache Tomcat en el puerto por defecto 8080."

---

### Bloque 5: Demostración Funcional en Swagger UI (3:00 - 4:30)

**En Pantalla:**  
Cambiar al navegador web y abrir la dirección:  
`http://localhost:8080/swagger-ui/index.html`

**Locución (Voz del Estudiante):**  
> "Nos dirigimos al navegador e ingresamos a la interfaz interactiva de Swagger UI en `localhost:8080/swagger-ui/index.html`.
> 
> Primero, consultamos el endpoint de salud `GET /health`, el cual responde públicamente con código 200 OK y estado UP.
> 
> Ahora realizaremos el proceso de autenticación para consumir los recursos protegidos:
> 1. Desplegamos el controlador `auth-rest-controller` y seleccionamos `POST /api/auth/login`.
> 2. Enviamos las credenciales por defecto del usuario administrador: `admin@example.com` y contraseña `Admin1234!`.
> 3. Ejecutamos la petición y observamos la respuesta HTTP 200 OK que nos entrega el token de acceso JWT con vigencia de 900 segundos.
> 
> Copiamos este token, subimos al botón verde `Authorize` en la parte superior derecha de Swagger, ingresamos la cabecera `Bearer [pegamos el token]` y autorizamos la sesión.
> 
> Con la sesión activa probamos las operaciones CRUD:
> - Ejecutamos `GET /api/users` para listar los usuarios: el servidor responde 200 OK y devuelve el arreglo JSON con el administrador activo.
> - Ejecutamos `POST /api/users` para crear un nuevo usuario con ID `0001`, nombre `El Profe`, email `arrietajohn@gmail.com` y rol `ADMIN`. El servidor responde 201 Created con el usuario en estado `PENDING`.
> - Finalmente, actualizamos su estado a `ACTIVE` mediante `PUT /api/users/0001`, comprobando la persistencia y la respuesta HTTP 200 OK."

---

### Bloque 6: Despliegue en la Nube y Conclusiones (4:30 - 5:00)

**En Pantalla:**  
Mostrar brevemente el archivo `render.yaml` y `compose.yaml` en el IDE, o el panel de Render/TiDB si se tiene abierto.

**Locución (Voz del Estudiante):**  
> "Para finalizar, el microservicio cuenta con su archivo `render.yaml` y su `Dockerfile` multi-stage optimizado para despliegue continuo en la nube sobre la plataforma Render, admitiendo conexiones tanto a MySQL remoto en Aiven como a bases de datos NewSQL de alta disponibilidad en TiDB Cloud Serverless.
> 
> Asimismo, el proyecto incluye la colección completa de peticiones exportada en formato JSON para herramientas como Postman o Boomerang, permitiendo reproducir estas pruebas de integración en cualquier momento.
> 
> Con esto damos por cumplidos los objetivos técnicos del taller. Muchas gracias por su atención."
