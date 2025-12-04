🛒 Tienda Web — Java, Jakarta EE, JSP, Servlets, SQL Server & Swing Admin Panel

Aplicación web completa de tienda online desarrollada con Java 17/22, Jakarta EE, JSP, Servlets, Maven, Tomcat 10, SQL Server y un panel de administración de escritorio en Swing.

Incluye autenticación con roles, CRUD de productos, catálogo para clientes, carrito (en desarrollo), arquitectura en capas y conexión a base de datos mediante JDBC.

Proyecto moderno, mantenible y listo para escalar.

🚀 Tecnologías Utilizadas
🧩 Backend

Java 17 / 22

Jakarta EE (Servlet API)

JSP + JSTL

Maven

Tomcat 10.1.x (migración desde javax → jakarta)

JDBC

SQL Server 2019

🎨 Frontend

JSP + JSTL

HTML5

CSS3

Bootstrap (opcional)

🗄 Base de Datos

SQL Server

Controlador JDBC: mssql-jdbc-13.x.jre11.jar

Autenticación Windows mediante:

integratedSecurity=true

mssql-jdbc_auth-x64.dll en C:\Windows\System32

📂 Estructura del Proyecto
TiendaWeb/
│── src/
│   └── main/
│       ├── java/
│       │   ├── com.tienda.modelo.entidades/
│       │   │   ├── Producto.java
│       │   │   ├── Usuario.java
│       │   │   └── Venta.java
│       │   │
│       │   ├── com.tienda.modelo.dao/
│       │   │   ├── ConexionDB.java
│       │   │   ├── ProductoDAO.java
│       │   │   └── UsuarioDAO.java
│       │   │
│       │   ├── com.tiendawweb.controladores/
│       │   │   ├── Login.java
│       │   │   ├── Logout.java
│       │   │   ├── ProductoServlet.java
│       │   │   └── TiendaServlet.java
│       │
│       └── webapp/
│           ├── index.jsp
│           ├── login.jsp
│           ├── productos.jsp
│           └── catalogo.jsp
│
└── pom.xml

🗄 Script de Base de Datos (SQL Server)
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
UPDATE usuarios 
SET rol = 'admin' 
WHERE email = 'admin@tienda.com';

🔌 Conexión SQL Server (ConexionDB.java)
private static final String URL =
    "jdbc:sqlserver://localhost:1433;"
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


✔ Requiere agregar el .jar del driver a Tomcat/lib
✔ Requiere mssql-jdbc_auth-x64.dll en System32

🌐 Controladores Principales (Servlets)
🔐 LoginServlet

Autenticación por roles (admin → CRUD / cliente → tienda).

if (u.getRol().equals("admin")) {
    response.sendRedirect("productos");   // Panel admin (CRUD)
} else {
    response.sendRedirect("catalogo.jsp"); // Vista cliente
}

🎨 Vistas JSP — Ejemplo productos.jsp
<c:forEach var="p" items="${lista}">
<tr>
    <td>${p.id}</td>
    <td>${p.nombre}</td>
    <td>${p.precioUSD}</td>
    <td>${p.stock}</td>
    <td>${p.categoria}</td>
    <td><img src="${p.imagenURL}" width="80"></td>
</tr>
</c:forEach>

⚙️ Migración a Tomcat 10

Este proyecto utiliza Jakarta EE:

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


No funcionarían imports javax.* (propios de Tomcat 9).

▶️ Cómo Ejecutar el Proyecto
1️⃣ Importar en NetBeans / IntelliJ

Abrir como proyecto Maven.

2️⃣ Instalar dependencias

mvn clean install

3️⃣ Configurar Tomcat 10

Agregar servidor cuenta como Jakarta EE.

4️⃣ Ejecutar
http://localhost:8080/TiendaWeb/
http://localhost:8080/TiendaWeb/login
http://localhost:8080/TiendaWeb/productos   (Admin)
http://localhost:8080/TiendaWeb/catalogo    (Cliente)

📌 Estado Actual del Proyecto

✔ Arquitectura en capas
✔ JSP + Servlets funcionando
✔ CRUD de productos
✔ Autenticación con roles (admin/cliente)
✔ Conexión SQL Server
✔ Tomcat 10
✔ Panel Admin Swing
✔ Estable y listo para expandirse

🧩 Próximos Módulos

🔐 Login 100% final
🧺 Carrito de compras
💱 Conversión USD → COP
📦 Checkout
📊 Reportes de ventas
🛡 Filtros de seguridad avanzados

📜 Licencia

Desarrollado por Juan Esteban Herrera Herrera
Uso libre para estudio, práctica e investigación.
