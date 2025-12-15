🛒 Tienda Web - E-commerce con Java, Jakarta EE, JSP, Servlets, SQL Server & Swing Admin Panel

Una aplicación web completa de comercio electrónico desarrollada con Java 17/22, Jakarta EE, JSP, Servlets, Maven, Tomcat 10, SQL Server, y un panel administrativo de escritorio en Java Swing.

Esta versión incluye mejoras en el carrito de compras, gestión de stock, filtrado por categorías y un frontend moderno y responsive.

✅ Funcionalidades Principales
1. Autenticación y Roles

Registro e inicio de sesión con validación.

Roles de usuario:

cliente

admin

Redirección automática según rol:

if (u.getRol().equals("admin"))
{

    response.sendRedirect("productos");  // Panel admin (CRUD)
    }
    else {
    response.sendRedirect("catalogo.jsp"); // Vista cliente
    
}

2. Gestión de Productos (CRUD)

Administración vía Swing Desktop y panel web.

CRUD completo: agregar, actualizar, eliminar productos.

Filtrado dinámico por categorías.

Validación de stock y cantidad en el carrito.

Imágenes guardadas como BLOB o como nombre de archivo.

3. Carrito de Compras

Agregar productos respetando el stock disponible.

Actualizar cantidades con límites según stock.

Eliminar productos individualmente.

Cálculo de subtotal por producto y total general.

Visualización dinámica en JSP con diseño moderno y responsive.

4. Arquitectura y Conexión

Arquitectura en capas: DAO, Entidades, Servlets, JSP.

Conexión a SQL Server con JDBC.

Migración completa de javax → jakarta para Tomcat 10.

Código limpio, mantenible y escalable.

🚀 Tecnologías Utilizadas
Backend:

Java 17/22

Jakarta EE (Servlet API)

JSP + JSTL

JDBC

Maven

Tomcat 10.1.x

SQL Server 2019

Frontend:

HTML5

CSS3

JSP + JSTL

Diseño responsive

Base de Datos:

SQL Server con autenticación Windows (integratedSecurity=true)

🗄 Base de Datos
SQL para la creación de tablas:

CREATE TABLE productos (

    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100),
    descripcion VARCHAR(200),
    precio FLOAT,
    stock INT,
    categoria VARCHAR(50),
    imagen VARCHAR(300),
    imagen_blob VARBINARY(MAX)
    
);

CREATE TABLE usuarios (

    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),
    rol VARCHAR(20) NOT NULL DEFAULT 'cliente'
    
);

CREATE TABLE Carrito (

    id_usuario INT,
    id_producto INT,
    cantidad INT,
    PRIMARY KEY(id_usuario, id_producto),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_producto) REFERENCES productos(id)
    
);

CREATE TABLE ventas (

    id INT PRIMARY KEY IDENTITY(1,1),
    id_usuario INT,
    id_producto INT,
    cantidad INT,
    fecha DATETIME,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_producto) REFERENCES productos(id)
    
);

Convertir un usuario en administrador:
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

🌐 Principales Servlets

LoginServlet: Autenticación + Roles

CarritoServlet: Gestión del carrito (agregar, actualizar, eliminar, vaciar, comprar)

HomeServlet / TiendaServlet: Listado de productos y filtrado por categorías

Validaciones importantes:

El botón "Agregar al carrito" solo es visible si el usuario está logueado.

Mensaje de advertencia si no tiene sesión activa.

Stock limitado y respetado en todo momento.

🎨 Vistas JSP

tienda.jsp / catalogo.jsp: Catálogo dinámico con filtrado por categoría y carga de imágenes desde BLOB.

verCarrito.jsp: Carrito dinámico con actualización de cantidades y opción para eliminar productos.

formularioNuevoProducto.jsp: Registro de productos con CSS moderno y responsive.

⚙ Migración a Tomcat 10

Todos los imports javax.* reemplazados por jakarta.*.

Compatible 100% con Tomcat 10.1.x (no funciona en Tomcat 9).

💻 Panel Administrativo Desktop

Aplicación en Java Swing para administración de productos.

Actualmente en desarrollo: aún no tiene archivo EXE.

Próximos módulos: BCRPY, File, y otras funcionalidades para administración avanzada.

▶️ Cómo Ejecutar el Proyecto

Importar el proyecto en NetBeans o IntelliJ como proyecto Maven.

Instalar dependencias:

mvn clean install


Configurar Tomcat 10.

Ejecutar en navegador:

Tienda Web

Login

Productos (Admin)

Catálogo (Cliente)

📌 Estado Actual

✅ Arquitectura en capas.

✅ JSP + Servlets funcionales.

✅ CRUD de productos operativo.

✅ Inicio de sesión + roles admin/cliente.

✅ Carrito de compras: agregar, actualizar, eliminar productos.

✅ Catálogo dinámico con filtrado por categoría.

✅ Stock respetado.

✅ Conexión a SQL Server estable.

✅ Migración completa a Tomcat 10.

✅ Panel Administrativo en Java Swing.

✅ Imágenes de productos cargadas desde BLOB.

🧩 Próximos Módulos

Conversión automática (API de tasas de cambio).

Reportes de ventas.

📜 Licencia

Desarrollado por Juan Esteban Herrera Herrera

Código libre para estudio, práctica e investigación.
