🛒 Tienda Web — Java, Jakarta EE, JSP, Servlets, SQL Server & Swing Admin Panel

Aplicación web completa de e-commerce desarrollada con Java 17/22, Jakarta EE, JSP, Servlets, Maven, Tomcat 10, SQL Server y un panel de administración de escritorio en Java Swing.

Esta versión incluye mejoras en el carrito de compras, gestión de stock, filtrado por categorías, y un frontend más amigable.

✅ Funcionalidades Principales
Autenticación y Roles

Registro e inicio de sesión.

Roles: cliente / admin.

Redirección automática según rol:

if (u.getRol().equals("admin")) {
    response.sendRedirect("productos");    // Panel admin (CRUD)
} else {
    response.sendRedirect("catalogo.jsp"); // Vista cliente
}

Gestión de Productos (CRUD)

Administración vía Swing (Desktop).

Panel web con JSP para clientes.

CRUD completo: agregar, actualizar, eliminar.

Filtrado por categorías dinámicas.

Validación de stock y cantidad en carrito.

Carrito de Compras

Agregar productos al carrito respetando stock disponible.

Actualizar cantidades con límites según stock.

Eliminar productos individualmente.

Cálculo de total por producto y total general.

Visualización dinámica en JSP con diseño moderno.

Arquitectura y Conexión

Arquitectura en capas: DAO, Entidades, Servlets, JSP.

Conexión a SQL Server con JDBC.

Migración completa javax → jakarta para Tomcat 10.

Código limpio, mantenible y escalable.

🚀 Tecnologías Utilizadas
Backend

Java 17 / 22

Jakarta EE (Servlet API)

JSP + JSTL

JDBC

Maven

Tomcat 10.1.x

SQL Server 2019

Frontend

HTML5

CSS3 (formulario de registro y carrito estilizados)

JSP + JSTL

Base de Datos

SQL Server

Driver JDBC: mssql-jdbc-13.x.jre11.jar

Autenticación Windows: integratedSecurity=true

DLL requerida: mssql-jdbc_auth-x64.dll → C:\Windows\System32

🗄 Estructura de Base de Datos
Tabla productos
CREATE TABLE productos (
  id INT PRIMARY KEY IDENTITY(1,1),
  nombre VARCHAR(100),
  descripcion VARCHAR(200),
  precio FLOAT,
  stock INT,
  categoria VARCHAR(50),
  imagen VARCHAR(300)
);

Tabla usuarios
CREATE TABLE usuarios (
  id INT PRIMARY KEY IDENTITY(1,1),
  nombre VARCHAR(100),
  email VARCHAR(100),
  password VARCHAR(100),
  rol VARCHAR(20) NOT NULL DEFAULT 'cliente'
);

Tabla carrito
CREATE TABLE Carrito (
  id_usuario INT,
  id_producto INT,
  cantidad INT,
  PRIMARY KEY(id_usuario, id_producto),
  FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
  FOREIGN KEY (id_producto) REFERENCES productos(id)
);

Tabla ventas
CREATE TABLE ventas (
  id INT PRIMARY KEY IDENTITY(1,1),
  id_usuario INT,
  id_producto INT,
  cantidad INT,
  fecha DATETIME,
  FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
  FOREIGN KEY (id_producto) REFERENCES productos(id)
);

Convertir un usuario en administrador
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

🌐 Principales Controladores (Servlets)

LoginServlet — Autenticación + Roles.

CarritoServlet — Gestión del carrito (agregar, actualizar, eliminar, vaciar).

HomeServlet / TiendaServlet — Listado de productos y filtrado por categorías.

Validaciones:

Botón "Agregar al carrito" solo visible si el usuario está logueado.

Mensaje de advertencia si no tiene sesión.

Stock limitado y respetado en todo momento.

🎨 Vistas JSP

Tienda.jsp / Catalogo.jsp — Catálogo dinámico y filtrado por categoría.

VerCarrito.jsp — Carrito dinámico con actualización de cantidades, stock respetado y botón para eliminar productos.

FormularioNuevoProducto.jsp — Registro de productos con CSS moderno y responsive.

⚙️ Migración a Tomcat 10 (Jakarta EE)

Todos los imports javax reemplazados por jakarta.

Compatible 100% con Tomcat 10.1.x.

No funciona en Tomcat 9.

▶️ Cómo Ejecutar el Proyecto

Importar en NetBeans / IntelliJ como proyecto Maven.

Instalar dependencias:

mvn clean install


Configurar Tomcat 10.

Ejecutar en navegador:

http://localhost:8080/TiendaWeb/
http://localhost:8080/TiendaWeb/login
http://localhost:8080/TiendaWeb/productos   (Admin)
http://localhost:8080/TiendaWeb/catalogo    (Cliente)

📌 Estado Actual del Proyecto

✅ Arquitectura en capas

✅ JSP + Servlets 100% funcionales

✅ CRUD de productos operativo

✅ Inicio de sesión + roles admin/cliente

✅ Carrito de compras: botón de agregar, actualizar, eliminar productos

✅ Catálogo dinámico con filtrado por categoría

✅ Stock respetado en todo momento

✅ Conexión SQL Server estable

✅ Migración completa a Tomcat 10 (Jakarta)

✅ Panel Administrativo en Java Swing

✅ CSS moderno y responsive

🧩 Próximos Módulos

🔐 Autenticación más robusta (hash de contraseñas, sesiones seguras)

🧺 Carrito completo con checkout y persistencia

💱 Conversión automática USD → COP

📦 Módulo de checkout

📊 Reportes de ventas

🛡 Filtros de seguridad avanzados (filters, listeners)

⭐ Mejoras visuales con Bootstrap / Tailwind

📜 Licencia

Desarrollado por Juan Esteban Herrera Herrera
Código libre para estudio, práctica e investigación.
