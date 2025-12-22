🛒 Tienda Web Pro: Full-Stack Cloud E-commerce
Arquitectura empresarial distribuida con Java 17/22, Docker y Despliegue en AWS

Este proyecto representa la evolución de una aplicación monolítica local hacia una infraestructura Cloud-Native, integrando servicios gestionados de Amazon Web Services y orquestación por contenedores.

🏗️ Arquitectura del Ecosistema
El sistema utiliza una topología híbrida para garantizar seguridad y escalabilidad:

Capa Web (Cloud): Backend en Jakarta EE (JSP/Servlets) desplegado en AWS Elastic Beanstalk bajo contenedores Docker.

Capa Administrativa (Desktop): Panel de control desarrollado en Java Swing que interactúa mediante JDBC con el endpoint de producción.

Capa de Datos (Managed): Instancia de Amazon RDS (MS SQL Server) configurada con reglas de firewall específicas en Security Groups.

🌟 Logros de Ingeniería (Cloud & DevOps)
☁️ Infraestructura como Servicio (AWS)
Elastic Beanstalk: Gestión automatizada del ciclo de vida de la aplicación y auto-escalado en instancias EC2.

Amazon RDS: Migración y mantenimiento de esquemas relacionales en la nube, eliminando la dependencia de servidores locales.

Networking: Configuración de conectividad remota segura a través del puerto 1433.

🐳 Contenedores y Automatización
Dockerization: Implementación de Dockerfile para estandarizar el entorno de ejecución en Tomcat 10+.

AWS Orchestration: Uso de Dockerrun.aws.json para la definición de despliegues multi-contenedor.

Scripts de Soporte: Utilización de Python para tareas de automatización en el CMD/Terminal durante la fase de instalación.

🛡️ Seguridad de Grado Profesional
Ocultación de Credenciales: Implementación de Variables de Entorno (System.getenv) para inyectar datos sensibles en tiempo de ejecución, protegiendo el código fuente en GitHub de posibles fugas de información.

Migración Jakarta: Transición exitosa de librerías javax.* a jakarta.* para garantizar compatibilidad con servidores de aplicaciones modernos.

⚙️ Configuración y Ejecución Local
Para vincular el panel administrativo con la base de datos en AWS, es necesario configurar las siguientes variables de entorno en el sistema operativo:

Bash

# Credenciales de conexión (No modificar en el código)
export DB_HOST="tienda-db.ch4qkweu698j.us-east-2.rds.amazonaws.com"
export DB_PORT="1433"
export DB_NAME="tienda_db"
export DB_USER="tienda_app"
export DB_PASS="tu_contraseña_segura"
Flujo de Compilación
Maven: mvn clean install para generar el artefacto .war actualizado.

Docker: docker build -t tienda-web-app .

🚀 Roadmap de Desarrollo
[ ] Integración de BCrypt para el hash de credenciales de usuario.

[ ] Implementación de pasarela de pagos simulada.

[ ] Dashboard de analítica de ventas con reportes en PDF.

Desarrollado por Juan Esteban Herrera Herrera Ingeniería de Software | Cloud & Java Developer

Instrucciones para pegar:
En tu GitHub, entra a editar el README.md.

Borra todo el contenido anterior.

Pega este bloque completo.

Presiona "Commit changes...".
