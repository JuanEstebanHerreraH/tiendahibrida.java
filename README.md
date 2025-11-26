🛒 Tienda Web Híbrida — Java EE + JSP + Servlets + SQL Server

Aplicación web de tienda desarrollada con Java (Jakarta EE 8), JSP, Servlets, Maven, Tomcat 10 y SQL Server.
Incluye arquitectura en capas (DAO/Modelo), operaciones CRUD, vistas JSP y conexión JDBC a base de datos.

Este repositorio representa una base sólida lista para extender hacia:
🛍️ Catálogo de productos
🛒 Carrito de compras
🔐 Login y sesiones
🧾 Ventas
🖥️ Panel administrativo en Swing

🚀 Tecnologías Utilizadas
🧩 Backend

Java 17 / 22

Jakarta EE 8 (Servlet API)

JDBC

Maven

Tomcat 10

SQL Server 2019

🎨 Frontend

JSP + HTML5

CSS3

JSP Scriptlets

JSTL (planeado para versiones futuras)

🗄️ Base de Datos

SQL Server

Tablas:

productos

usuarios

ventas

📂 Estructura del Proyecto
TiendaWeb/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com.tienda.modelo.entidades/
│   │   │   │   ├── Producto.java
│   │   │   │   ├── Usuario.java
│   │   │   │   └── Venta.java
│   │   │   ├── com.tienda.modelo.dao/
│   │   │   │   ├── ConexionDB.java
│   │   │   │   └── ProductoDAO.java
│   │   │   └── com.tiendawweb.controladores/
│   │   │       └── ProductoServlet.java
│   │   ├── webapp/
│   │   │   ├── index.jsp
│   │   │   └── productos.jsp
│   │   └── resources/
│   │
│   ├── test/
│
├── pom.xml
└── README.md

🗄️ Script de Base de Datos
Tabla productos
CREATE TABLE productos (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100),
    precio_usd FLOAT,
    stock INT,
    categoria VARCHAR(50),
    imagen_url VARCHAR(300)
);

Tabla usuarios
CREATE TABLE usuarios (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),
    moneda_preferida VARCHAR(5)
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

🔌 Conexión con SQL Server (ConexionDB.java)
private static final String URL =
"jdbc:sqlserver://localhost:1433;databaseName=tienda_db;encrypt=false;trustServerCertificate=true;";

public static Connection getConexion() {
    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(URL);
    } catch (Exception e) {
        System.out.println("Error al conectar: " + e.getMessage());
        return null;
    }
}


✔ Usa autenticación de Windows, sin usuario ni contraseña.

🌐 Servlet Principal — ProductoServlet
@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Producto> productos = dao.listar();
        req.setAttribute("lista", productos);

        req.getRequestDispatcher("productos.jsp").forward(req, resp);
    }
}

🎨 Vista productos.jsp
<h1>Productos</h1>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Precio (USD)</th>
        <th>Stock</th>
        <th>Categoría</th>
        <th>Imagen</th>
    </tr>

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
</table>

▶️ Cómo Ejecutar el Proyecto

Importar en NetBeans o IntelliJ
Abrir como proyecto Maven.

Instalar dependencias
Maven las descarga automáticamente.

Configurar Tomcat 10

Ejecutar el proyecto
Desde IDE o usando:

mvn clean install


Abrir en navegador:

http://localhost:8080/TiendaWeb/

http://localhost:8080/TiendaWeb/productos

📌 Estado Actual del Proyecto

✔ Arquitectura en capas
✔ JSP + Servlet funcionando
✔ CRUD de productos terminado
✔ Conexión a SQL Server funcionando
✔ Proyecto estable y expandible

🧩 Próximos módulos

Login de usuario

Sesiones

Carrito de compras

API de moneda

Panel admin en Swing

Seguridad

📜 Licencia

Juan Esteban Herrera Herrera
Este proyecto es libre para estudio y uso educativo.
