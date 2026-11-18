# Tienda híbrida · Java

**Tienda web con un panel de administración de escritorio y base de datos SQL Server.**

Proyecto de desarrollo full stack con dos módulos Maven: una aplicación web JSP/Servlets y un cliente Java Swing para administración. Permite explorar catálogo, cuentas, carrito y ventas, junto con las operaciones de gestión del panel.

## Arquitectura del repositorio

| Módulo | Tecnología | Resultado de compilación |
| --- | --- | --- |
| [TiendaWeb](TiendaWeb/) | Java 17, Jakarta Servlet 6, JSP, JDBC | WAR para un contenedor compatible con Jakarta, como Tomcat 10.1. |
| [TiendaAdminDesktop](TiendaAdminDesktop/) | Java 22, Swing, JDBC | JAR de escritorio con dependencias. |
| Base de datos | Microsoft SQL Server | Instancia propia con el esquema de la aplicación. |

Ambos módulos se conectan a SQL Server. La separación permite comparar un flujo web con uno de administración de escritorio; el acceso a datos se concentra en clases DAO.

## Configuración local

Necesitas Maven, JDK 17 para el módulo web, JDK 22 para escritorio y una instancia de SQL Server con las tablas que utiliza la aplicación.

~~~sh
git clone https://github.com/JuanEstebanHerreraH/tiendahibrida.java.git
cd tiendahibrida.java
~~~

Configura las variables de entorno del proceso que ejecutará cada aplicación. Ejemplo en PowerShell:

~~~powershell
$env:DB_HOST = "localhost"
$env:DB_PORT = "1433"
$env:DB_NAME = "TU_BASE_DE_DATOS"
$env:DB_USER = "TU_USUARIO_DE_APLICACION"
$env:DB_PASS = "TU_CONTRASENA_LOCAL"
~~~

Usa tus propios valores y permisos mínimos. Las clases de conexión consultan las variables de entorno; crear un archivo .env por sí solo no garantiza que el proceso las reciba.

## Compilar la web

Con JDK 17 seleccionado:

~~~sh
cd TiendaWeb
mvn clean package
~~~

El módulo genera `target/TiendaWeb.war` según su pom.xml. Despliega ese WAR en tu contenedor Jakarta compatible y configura las variables de base de datos en el proceso del servidor.

## Compilar el panel de escritorio

Desde la raíz y con JDK 22 seleccionado:

~~~sh
cd TiendaAdminDesktop
mvn clean package
~~~

La clase principal declarada es `com.mycompany.tiendaadmindesktop.TiendaAdminDesktop`. El empaquetado Maven incluye dependencias mediante Shade; revisa los JAR generados en `target/`.

## Límites y estado

- La aplicación requiere preparar la base de datos y sus permisos; compilar no crea automáticamente el esquema.
- El módulo web y el de escritorio usan versiones distintas de Java: revisa JAVA_HOME para cada compilación.
- El repositorio incluye artefactos y una distribución de Tomcat de apoyo al entorno de desarrollo. El código propio está en los dos módulos indicados arriba.
- Un despliegue en AWS/RDS necesita una cuenta y una configuración propias; esta documentación no anuncia una instancia pública operativa.
- Las funciones de pago, sincronización y despliegue automático no se presentan como verificadas por esta guía.

## Autor

[Juan Esteban Herrera Herrera](https://github.com/JuanEstebanHerreraH). Proyecto de práctica de desarrollo web, escritorio y persistencia con Java.
