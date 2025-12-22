# 🛒 Tienda Web Pro

Full‑Stack Cloud E‑commerce — arquitectura empresarial distribuida con Java 17/22, Docker y despliegue en AWS.

Este proyecto es la evolución de una aplicación monolítica local hacia una arquitectura Cloud‑Native, usando servicios gestionados de AWS y contenedores Docker. Incluye backend en Jakarta EE (JSP/Servlets) desplegado en Elastic Beanstalk, panel administrativo desktop en Java Swing y base de datos en Amazon RDS (MS SQL Server).

---

## Índice
- [Resumen](#resumen)
- [Arquitectura](#arquitectura)
- [Características principales](#características-principales)
- [Requisitos](#requisitos)
- [Configuración local](#configuración-local)
- [Variables de entorno](#variables-de-entorno)
- [Construcción y ejecución](#construcción-y-ejecución)
- [Docker & Elastic Beanstalk](#docker--elastic‑beanstalk)
- [Conexión a Amazon RDS (MS SQL Server)](#conexión-a-amazon-rds-ms-sql-server)
- [Seguridad y manejo de secretos](#seguridad-y-manejo-de-secretos)
- [Migración Jakarta / Tomcat 10+](#migración-jakarta--tomcat-10)
- [Scripts y automatización (Python)](#scripts-y-automatización-python)
- [Panel Administrativo (Java Swing)](#panel-administrativo-java-swing)
- [Roadmap](#roadmap)
- [Licencia y autor](#licencia-y-autor)

---

## Resumen
Se migró la aplicación tradicional (JSP/Servlets + SQL Server) hacia una solución preparada para la nube:
- Backend en Jakarta EE (compatible con Tomcat 10+).
- Contenerizada con Docker y orquestada/desplegada en AWS Elastic Beanstalk.
- Base de datos gestionada en Amazon RDS (SQL Server).
- Panel administrativo de escritorio en Java Swing que se conecta mediante JDBC al endpoint de producción.
- Variables de entorno para inyectar credenciales sensibles en tiempo de ejecución (NO incluir credenciales en el repo).

---

## Arquitectura
- Capa Web (Cloud): Jakarta EE (Servlets/JSP) desplegada en contenedor Tomcat 10+ sobre Elastic Beanstalk (Docker).
- Capa Datos: Amazon RDS (MS SQL Server) con Security Group que restringe accesos (p. ej. puerto 1433).
- Capa Admin: Aplicación Java Swing (desktop) que consume directamente la base de datos por JDBC (usar IP autorizada / túnel seguro).
- Orquestación: Elastic Beanstalk (Dockerrun/Dockerfile). Posible migración a ECS/EKS si se requiere orquestación más avanzada.

---

## Características principales
- Autenticación con roles (cliente / admin).
- CRUD de productos (panel web y Swing).
- Carrito de compras con control de stock.
- Imágenes de productos (nombre de archivo o BLOB).
- Migración completa de `javax.*` → `jakarta.*` para compatibilidad Tomcat 10+.
- Variables de entorno para todas las credenciales sensibles.

---

## Requisitos
- Java 17 o 22 (JDK)
- Maven
- Docker
- AWS CLI y EB CLI (si despliegas desde CLI)
- Cuenta AWS con permisos para Elastic Beanstalk y RDS
- SQL Server JDBC Driver (com.microsoft.sqlserver:mssql-jdbc)

---

## Configuración local

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/tu-repo.git
   cd tu-repo
   ```

2. Configurar variables de entorno (ver sección siguiente).

3. Compilar:
   ```bash
   mvn clean install
   # Genera target/tu-app.war
   ```

---

## Variables de entorno

IMPORTANTE: No guardar credenciales en el código. Usar variables de entorno, Elastic Beanstalk environment variables o AWS Secrets Manager.

Ejemplos (replicar en la máquina de desarrollo o en EB):

- Bash (Linux / macOS / WSL):
  ```bash
  export DB_HOST="tienda-db.ch4qkweu698j.us-east-2.rds.amazonaws.com"
  export DB_PORT="1433"
  export DB_NAME="tienda_db"
  export DB_USER="tienda_app"
  export DB_PASS="tu_contraseña_segura"
  ```

- PowerShell (temporal en la sesión):
  ```powershell
  $env:DB_HOST="tienda-db.ch4qkweu698j.us-east-2.rds.amazonaws.com"
  $env:DB_PORT="1433"
  $env:DB_NAME="tienda_db"
  $env:DB_USER="tienda_app"
  $env:DB_PASS="tu_contraseña_segura"
  ```

- CMD (temporal en la sesión) / Para persistencia usar setx:
  ```cmd
  set DB_HOST=tienda-db.ch4qkweu698j.us-east-2.rds.amazonaws.com
  set DB_PORT=1433
  set DB_NAME=tienda_db
  set DB_USER=tienda_app
  set DB_PASS=tu_contraseña_segura
  ```

En código Java, recobrar variables:
```java
String host = System.getenv("DB_HOST");
String port = System.getenv("DB_PORT");
String name = System.getenv("DB_NAME");
String user = System.getenv("DB_USER");
String pass = System.getenv("DB_PASS");
```

---

## Construcción y ejecución (local con Docker)

1. Genera el WAR:
   ```bash
   mvn clean package
   ```

2. Docker build (ejemplo Dockerfile abajo):
   ```bash
   docker build -t tienda-web-app .
   ```

3. Ejecutar localmente:
   ```bash
   docker run --rm -p 8080:8080 \
     -e DB_HOST="$DB_HOST" -e DB_PORT="$DB_PORT" -e DB_NAME="$DB_NAME" \
     -e DB_USER="$DB_USER" -e DB_PASS="$DB_PASS" \
     tienda-web-app
   ```

---

## Dockerfile (ejemplo)
```dockerfile
# Usa Tomcat 10 con JDK 17
FROM tomcat:10-jdk17
# Elimina la aplicación ROOT default si existe
RUN rm -rf /usr/local/tomcat/webapps/ROOT
# Copia el WAR generado por Maven
COPY target/tienda-web.war /usr/local/tomcat/webapps/ROOT.war
# Expone puerto de Tomcat
EXPOSE 8080
# Comando por defecto ya definido por la imagen tomcat
```

---

## Dockerrun.aws.json (ejemplos)

- Dockerrun v1 (single container) — subir al root del .zip para Elastic Beanstalk:
```json
{
  "AWSEBDockerrunVersion": "1",
  "Image": {
    "Name": "tu-usuario/tienda-web-app:latest",
    "Update": "true"
  },
  "Ports": [
    {
      "ContainerPort": "8080"
    }
  ],
  "Logging": "/var/log/nginx"
}
```

- Si usas Dockerrun v2 (multi-container / ECS) o `Dockerrun.aws.json` con definición más compleja, revisa la documentación de EB sobre Dockerrun v2 y ECS.

---

## Despliegue en AWS Elastic Beanstalk (resumen)
1. Inicializa:
   ```bash
   eb init -p docker tienda-web-pro --region us-east-2
   ```
2. Crea entorno:
   ```bash
   eb create tienda-web-env
   ```
3. Sube variables de entorno y despliega:
   - Usar `eb setenv DB_HOST=... DB_USER=...` o en la consola AWS Elastic Beanstalk -> Configuration -> Software -> Environment properties.
4. Desplegar:
   ```bash
   eb deploy
   ```

Consejo: Para credenciales sensibles, usa AWS Secrets Manager y referencia el secreto desde variables de entorno (o configurar un init script que recupere el secreto durante el arranque).

---

## Conexión a Amazon RDS (MS SQL Server)

JDBC connection string recomendado:
```text
jdbc:sqlserver://<DB_HOST>:<DB_PORT>;databaseName=<DB_NAME>;encrypt=false;trustServerCertificate=true;user=<DB_USER>;password=<DB_PASS>;
```

Ejemplo en Java:
```java
String url = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;encrypt=false;trustServerCertificate=true;",
    System.getenv("DB_HOST"), System.getenv("DB_PORT"), System.getenv("DB_NAME"));
Connection conn = DriverManager.getConnection(url, System.getenv("DB_USER"), System.getenv("DB_PASS"));
```

Notas:
- Asegura que el Security Group de RDS permita acceso desde el entorno de Elastic Beanstalk o desde la IP de administración (para Swing).
- No uses IntegratedSecurity con RDS en la nube; usa autenticación SQL.

---

## Seguridad y manejo de secretos
- Nunca subir credenciales al repositorio.
- Usar:
  - Variables de entorno en Elastic Beanstalk.
  - AWS Secrets Manager (recomendado) + IAM Role con permisos mínimos.
  - Parameter Store (SSM) como alternativa.
- Revisión: Revisa el historial Git y elimina secretos con herramientas como git-filter-repo si exponiste credenciales.

---

## Migración Jakarta / Tomcat 10
- Reemplazar imports `javax.*` → `jakarta.*`.
- Verificar dependencias (JSP/EL/Jakarta EE) compatibles con Tomcat 10.
- Probar localmente con la imagen `tomcat:10-jdk17`.

---

## Scripts y automatización (Python)
- Se incluyeron scripts en `scripts/` para tareas repetitivas (instalador de herramientas, despliegue local, etc.). Si uno de los scripts instala una herramienta en CMD (Windows), revisa `scripts/install_tool.py` y actualiza el PATH según tu sistema.
- Ejemplo (invocar):
  ```bash
  python3 scripts/setup_local_env.py
  ```

---

## Panel Administrativo (Java Swing)
- Aplicación desktop que realiza operaciones CRUD sobre la misma base de datos (requiere acceso remoto permitido por RDS Security Group).
- Conexión JDBC estándar usando las variables de entorno o un archivo de configuración local (no comiteado).
- Para producción: considera exponer una API segura en vez de dar acceso directo a la base de datos desde desktop.

---

## Buenas prácticas
- Usar HTTPS (ALB / CloudFront) frente al entorno EB.
- Aplicar Least Privilege a roles IAM.
- Monitoreo: CloudWatch + alarmas para latencia, errores y uso de recursos.
- Backups RDS y pruebas de restauración periódicas.
- Hash de contraseñas: integrar BCrypt (roadmap).

---

## Roadmap
- [ ] Integración de BCrypt para el hash de credenciales de usuario.
- [ ] Implementación de pasarela de pagos simulada (sandbox).
- [ ] Dashboard de analítica de ventas con reportes en PDF.
- [ ] Migración a ECS/EKS (opcional) para un control más fino de la orquestación.
- [ ] Integración con AWS Secrets Manager para credenciales DB.
- [ ] Automatizar CI/CD (GitHub Actions → EB Deploy).

---

## Cómo actualizar este README en GitHub
1. Entra al repositorio en GitHub.
2. Abre `README.md`.
3. Haz clic en el lápiz (editar).
4. Selecciona todo el contenido anterior y bórralo.
5. Pega el contenido completo de este README.
6. Escribe un mensaje de commit y presiona "Commit changes".

---

## Licencia y autor
Desarrollado por Juan Esteban Herrera Herrera — Ingeniería de Software | Cloud & Java Developer  
Código libre para estudio, práctica e investigación.

---
