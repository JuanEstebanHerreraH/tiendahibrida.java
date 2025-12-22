🛒 Tienda Web Pro - E-commerce Full Stack & Cloud Deploy
Una aplicación robusta de comercio electrónico de extremo a extremo. Combina un Backend en Java (Jakarta EE), una interfaz administrativa Swing, y una arquitectura moderna desplegada en la nube utilizando AWS (Elastic Beanstalk, RDS, EC2) y Docker.

🚀 Novedades de Infraestructura (Cloud & DevOps)
Esta versión marca la transición de un entorno local a una arquitectura Cloud Native:

Despliegue en AWS: Implementación exitosa en AWS Elastic Beanstalk utilizando instancias EC2.

Dockerización: Inclusión de Dockerfile y Dockerrun.aws.json para despliegues consistentes y escalables en contenedores.

Base de Datos Gestionada: Migración de SQL Server local a Amazon RDS (MS SQL Server).

Seguridad Inyectada: Uso de Variables de Entorno para proteger credenciales sensibles (DB_HOST, DB_PASS), evitando el "hardcoding" de contraseñas en el código fuente.

Automatización: Uso de Python y scripts de terminal para la automatización de tareas de instalación y configuración en el entorno de servidor.

✅ Funcionalidades Principales
1. Autenticación y Seguridad Cloud
Inicio de sesión con roles diferenciados (Admin/Cliente).

Conexión cifrada a la base de datos RDS mediante cadenas de conexión dinámicas.

2. Gestión Administrativa (Híbrida)
Panel Web: CRUD de productos desde el navegador.

Panel Desktop (Swing): Administración avanzada desde el escritorio, conectada por JDBC al endpoint de AWS.

3. Carrito y Stock Inteligente
Gestión de inventario en tiempo real sincronizada en la nube.

Persistencia de carrito por usuario en SQL Server RDS.

🛠 Tecnologías Utilizadas
Lenguajes: Java 17/22, SQL, Python (scripts de automatización).

Web: Jakarta EE, JSP, Servlets, JSTL, Tomcat 10.1.x.

Cloud & DevOps:

AWS: Elastic Beanstalk, RDS (SQL Server), EC2.

Containers: Docker, Multi-container Docker (Dockerrun.aws.json).

Herramientas: Maven, Git, SQL Server Management Studio (SSMS).

🗄 Migración de Base de Datos (AWS RDS)
Para replicar el entorno, utiliza el script de migración de tablas incluyendo IDENTITY para el manejo de IDs autoincrementables:

SQL

-- Tabla de productos con soporte para imágenes BLOB
CREATE TABLE productos (

    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100),
    precio FLOAT,
    stock INT,
    imagen_blob VARBINARY(MAX)
    
);
🔌 Configuración de Conexión Segura
El archivo ConexionDB.java ahora es seguro para compartir en GitHub, ya que consume las variables configuradas en el Security Group y el panel de Elastic Beanstalk:

Java

// Ejemplo de lectura de variables de entorno en AWS/Local
private static final String HOST = System.getenv("DB_HOST");
private static final String PASS = System.getenv("DB_PASS");
private static final String URL = "jdbc:sqlserver://" + HOST + ":1433;databaseName=tienda_db;";
📦 Despliegue con Docker y AWS
El proyecto incluye los archivos necesarios para la orquestación en la nube:


Dockerfile: Define la imagen de Tomcat y la inyección del archivo .war.

Dockerrun.aws.json: Archivo de configuración para que AWS Elastic Beanstalk sepa cómo desplegar los contenedores y gestionar los puertos.

pom.xml: Configuración de Maven para la compilación de dependencias de Jakarta EE y drivers de SQL Server.

▶️ Cómo Ejecutar
Variables de Entorno: Configura en tu sistema (o en el panel de AWS) las variables: DB_HOST, DB_NAME, DB_USER, DB_PASS, DB_PORT.

Compilar: Ejecuta mvn clean install para generar el archivo .war.

Docker: docker build -t tienda-web .

AWS: Sube el archivo .war o el Dockerrun.aws.json a tu entorno de Elastic Beanstalk.

Desarrollado por Juan Esteban Herrera Herrera Proyecto
enfocado en la implementación de arquitecturas Java empresariales y despliegue profesional en la nube.
