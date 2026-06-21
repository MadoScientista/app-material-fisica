# App Material de Física

 Autor: Samuel Cortés

 Aplicación de 10 microservicios construidos utilizando Java, Spring Boot, Flyway, Open Feign y MySQL, que en su conjunto conforman una aplicación para la creación, gestión y distribución de material de física.

 Cuenta con un **Eureka Server** para descubrimiento de servicios y un **API Gateway** como punto de entrada único.


## Microservicios

| # | Microservicio | Puerto | Descripción |
|---|---------------|--------|-------------|
| 1 | `eureka-server` | 8761 | Servicio de descubrimiento (Eureka) |
| 2 | `api-gateway` | 8080 | Punto de entrada único a todos los servicios |
| 3 | `generador-ejercicios` | 8081 | Genera ejercicios de física coherentes |
| 4 | `historial` | 8082 | Registro de eventos |
| 5 | `logros` | 8083 | Sistema de logros |
| 6 | `material` | 8084 | Compilación de ítems de ejercicios |
| 7 | `notificador` | 8085 | Notificaciones push y web |
| 8 | `suscripciones` | 8086 | Gestión de suscripciones |
| 9 | `usuarios` | 8087 | Usuarios y orquestación central |
| 10 | `valoraciones` | 8088 | Valoración de ejercicios |
| 11 | `logos` | 8089 | Gestión de imágenes/logos |
| 12 | `comunidades` | 8090 | Comunidades de usuarios |

### Software requerido para la ejecución

- VSCode con extensiones 
- MySQL con MySQL Workbench
- Postman

## Pasos de ejecución

 1. Clonar el repositorio
 2. Instalar en VSCode las extensiones Java Extensión Pack y Sprinb Boot Extensión Pack.
 3. Se requiere de MySQL instalado junto a MySQL Workbench.
 4. Para preparar el espacio de trabajo se deben crear usuarios para la administración de cada microservicios con el script `utilidadDB/admin_app.sql` en MySQL Workbench.
 5. Levantar los microservicios en VSCode desde la pestaña Run and Debug en el siguiente orden:
    - `EurekaServerApplication` (puerto 8761)
    - Los 10 microservicios (puertos 8081-8090)
    - `ApiGatewayApplication` (puerto 8080)
 6. Importar a Postman la colección `postman/app-material-fisica.postman_collection.json`
 7. Todas las peticiones se realizan a través del API Gateway en `localhost:8080`

 ## Flujo de Trabajo

 Los principales flujo de trabajo consideran la generación de ejercicios, la creación de comunidades y la creación de material.

 ### 1. Generación de ejercicios

 Para la generación de ejercicios ocurren los siguientes eventos:

 1. Usuario solicita generación de un ejercicio
 2. Suscripciones indica el tipo de suscripción del usuario. Una suscripción gratuita permite máximo 3 ejercicios, y una suscripción premium "no tiene" límite.
 3. Generador de ejercicios calcula valores, busca plantillas y devuelve a Usuario un ejercicio bajo los parámetros solicitados.
 4. Usuario guarda el ejercicio y comunica el evento a Historial y Logros.
 5. Historial almacena el evento e informa al Notificador en caso de ser necesario (solo para tipos de eventos marcados para notificar)
 6. Notificador solicita información a Usuario para personalizar una plantilla de notificación de varios canales. Actualmente son plantillas de notificaciones push y web.
 7. Por su parte Logros aumenta el recuento de ejercicios creados y valida si se ha cumplido un logro.
 8. Logros notifica el evento a Historial e Historial a Notificador.

 ### 1.1 Solicitudes en Postman para flujo generación de ejercicios

 Para probar el primer flujo de trabajo puede ejecutar paso a paso las consultas de la carpeta 1-flujo-generacion-ejercicios de la colección importada de postman. Opcionalmente puede ir verificando el flujo ejecutando as consultas de forma desordenada para veriicar los códigos y mensajes de error que entrega la app.

 ### 2.Creación y usos de una comunidad

 Cada usuario puede crear y pertenecer a una comunidad. La comunidad permite visualizar todos los ejercicios almacenados por los usuarios

 1. Usuario crea una comunidad
 2. Varios usuarios generan ejercicios
 3. Agregar usuarios a la comunidad
 4. Solicitar los ejercicios creados por los usuarios miembros

 ### 2.1 Solicitudes Postman para creación y usos de una comunidad

 Para probar el primer flujo de trabajo puede ejecutar paso a paso las consultas de la carpeta 2-flujo-usus-de-comunidad de la colección importada de postman. Opcionalmente puede ir verificando el flujo ejecutando as consultas de forma desordenada para veriicar los códigos y mensajes de error que entrega la app.

 ### 3. Creación de material

 Un material está construido por varios items de ejercicios, por lo que para generar un material primero se debe crear al menos 1 item de ejercicio. 

 Los items de ejercicios no almacenan ejercicios completos de los ejercicios generados por el microservicio generador-ejercicios sino que el usuario es queién indica el texto de ls ejercicios para cada item.

 1. Solicitudes de creación de item de ejercicios
 2. Solicitud de creación de material con los ID de los item de ejercicios
 3. Verificación de la creación del material

 ### 3.1 Solicitudes Postman para creación de material

 Para probar el primer flujo de trabajo puede ejecutar paso a paso las consultas de la carpeta 3-flujo-creacion-material de la colección importada de postman. Opcionalmente puede ir verificando el flujo ejecutando as consultas de forma desordenada para veriicar los códigos y mensajes de error que entrega la app.

 ### 4. Valoración de un ejercicio

 Los ejercicios pueden ser valorados en una escala de 1 a 5 junto a un comentario.

 1. Solicitud valoración
 2. Solicitud de promedio de valoración

 ### 4.1 Solicitudes Postman para valoraciones

 Para probar el primer flujo de trabajo puede ejecutar paso a paso las consultas de la carpeta 4-flujo-valoracion de la colección importada de postman. Opcionalmente puede ir verificando el flujo ejecutando as consultas de forma desordenada para veriicar los códigos y mensajes de error que entrega la app.
