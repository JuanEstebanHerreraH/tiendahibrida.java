🛒 Tienda Web — Java, Jakarta EE, JSP, Servlets, SQL Server & Swing Admin Panel

Aplicación web completa de e-commerce desarrollada con Java 17/22, Jakarta EE, JSP, Servlets, Maven, Tomcat 10, SQL Server y un panel de administración de escritorio en Java Swing.

Incluye:

✔ Autenticación con roles (cliente / admin)
✔ CRUD de productos
✔ Catálogo dinámico
✔ Agregar al carrito
✔ Administración vía Swing
✔ Arquitectura en capas
✔ Conexión a SQL Server con JDBC
✔ Migración completa de javax → jakarta
✔ Código limpio, mantenible y listo para escalar

🚀 Tecnologías Utilizadas
🧩 Backend

Java 17 / 22

Jakarta EE (Servlet API)

JSP + JSTL

JDBC

Maven

Tomcat 10.1.x

SQL Server 2019

🎨 Frontend

HTML5

CSS3

JSP + JSTL

Bootstrap (opcional)

🗄 Base de Datos

SQL Server

Driver JDBC: mssql-jdbc-13.x.jre11.jar

Autenticación Windows:

integratedSecurity=true


DLL requerida:

mssql-jdbc_auth-x64.dll → C:\Windows\System32

🗄 Estructura de Base de Datos (SQL Server)
🛍️ Tabla productos
CREATE TABLE productos (
  id INT PRIMARY KEY IDENTITY(1,1),
  nombre VARCHAR(100),
  precio_usd FLOAT,
  stock INT,
  categoria VARCHAR(50),
  imagen_url VARCHAR(300)
);

👤 Tabla usuarios
CREATE TABLE usuarios (
  id INT PRIMARY KEY IDENTITY(1,1),
  nombre VARCHAR(100),
  email VARCHAR(100),
  password VARCHAR(100),
  moneda_preferida VARCHAR(5),
  rol VARCHAR(20) NOT NULL DEFAULT 'cliente'
);

🧾 Tabla ventas
CREATE TABLE ventas (
  id INT PRIMARY KEY IDENTITY(1,1),
  id_usuario INT,
  id_producto INT,
  cantidad INT,
  fecha DATETIME,
  FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
  FOREIGN KEY (id_producto) REFERENCES productos(id)
);

⭐ Convertir un usuario en administrador
UPDATE usuarios SET rol = 'admin' WHERE email = 'admin@tienda.com';

🔌 Conexión a SQL Server (ConexionDB.java)
private static final String URL = "jdbc:sqlserver://localhost:1433;"
        + "databaseName=tienda_db;"
        + "encrypt=false;"
        + "trustServerCertificate=true;"
        + "integratedSecurity=true;";

public static Connection getConexion() {
    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(URL);
    } catch (Exception e) {
        System.out.println("Error al conectar: " + e.getMessage());
        return null;
    }
}


✔ Requiere agregar el driver .jar en Tomcat/lib
✔ Requiere mssql-jdbc_auth-x64.dll en C:\Windows\System32

🌐 Principales Controladores (Servlets)
🔐 LoginServlet — Autenticación + Roles

Redirección automática según rol:

if (u.getRol().equals("admin")) {
    response.sendRedirect("productos");    // Panel admin (CRUD)
} else {
    response.sendRedirect("catalogo.jsp"); // Vista cliente
}


Incluye validación para mostrar:

Botón de “Agregar al carrito” sólo si el usuario está logueado

Mensaje de advertencia si no tiene sesión

🎨 Vistas JSP — Ejemplo (productos.jsp)
<c:forEach var="p" items="${lista}">
    ${p.id}
    ${p.nombre}
    ${p.precioUSD}
    ${p.stock}
    ${p.categoria}
</c:forEach>

⚙️ Migración a Tomcat 10 (Jakarta EE)

Este proyecto ya utiliza:

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


❌ No funcionan imports javax. (Tomcat 9)
✔ Compatible 100% con Tomcat 10.1.x

▶️ Cómo Ejecutar el Proyecto
1️⃣ Importar en NetBeans / IntelliJ

Abrir como proyecto Maven.

2️⃣ Instalar dependencias
mvn clean install

3️⃣ Configurar Tomcat 10

Agregar servidor → seleccionar Jakarta EE.

4️⃣ Ejecutar en navegador
http://localhost:8080/TiendaWeb/
http://localhost:8080/TiendaWeb/login
http://localhost:8080/TiendaWeb/productos   (Admin)
http://localhost:8080/TiendaWeb/catalogo    (Cliente)

📌 Estado Actual del Proyecto

✔ Arquitectura en capas
✔ JSP + Servlets 100% funcionales
✔ CRUD de productos operativo
✔ Inicio de sesión + roles admin/cliente
✔ Carrito: botón de agregar funcionando
✔ Catálogo dinámico
✔ Conexión SQL Server estable
✔ Migración completa a Tomcat 10 (Jakarta)
✔ Panel Administrativo en Java Swing
✔ Sistema escalable y mantenible

🧩 Próximos Módulos

🔐 Autenticación más robusta
🧺 Carrito completo (ver / incrementar / eliminar / total)
💱 Conversión USD → COP automática
📦 Módulo de checkout
📊 Reportes de ventas
🛡 Filtros de seguridad avanzados (filtros + listeners)
⭐ Mejoras visuales con Bootstrap / Tailwind

📜 Licencia

Desarrollado por Juan Esteban Herrera Herrera
Código libre para estudio, práctica e investigación.
